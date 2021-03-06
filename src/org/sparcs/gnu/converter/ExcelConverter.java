package org.sparcs.gnu.converter;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
class ExcelConverter extends Converter {
	
	private String xlsFilePath;

	public ExcelConverter(String filepath) {
		xlsFilePath = filepath;
	}
	
	/**
	 * Converts Excel(.csv) file to sqlite file.
	 * @param outputFilename Filename of sqlite containing a user's file history
	 */
	@Override
	public boolean convert(String outputFilename) {
		
		ExcelAltConverter alt = new ExcelAltConverter(xlsFilePath);
		if(alt.convert(outputFilename))
			return true;
		
		List<ExcelConfig> allConfig = new LinkedList<>();
		
		allConfig.add(new HangulExcelConfig());
		allConfig.add(new EnglishExcelConfig());
		allConfig.add(new HangulSugangConfig());
		
		ExcelConfig finalConfig = null;
		Set<String> finalFields = null;
		
		try
		{
			Map<String, Integer> fieldToIndex = new HashMap<>();
			Workbook xls = Workbook.getWorkbook(new File(xlsFilePath));
			Sheet sheet = xls.getSheet(0);
			int startRow = 0;
			boolean found = false;
			for(; startRow < sheet.getRows(); startRow++)
			{
				Cell[] cells = sheet.getRow(startRow);
				Set<String> allCell = new HashSet<>();
				for(Cell cell : cells)
					allCell.add(cell.getContents().trim());
				
				if(finalConfig == null)
				{
					for(ExcelConfig current : allConfig)
					{
						Set<String> currentColumns = current.getAllColumn();
						if(allCell.containsAll(currentColumns))
						{
							finalConfig = current;
							finalFields = currentColumns;
						}
							
					}
				}
				
				if(finalConfig != null)
				{
					for(String field : finalFields)
					{
						for(int k=0; k<cells.length; k++)
						{
							if(cells[k].getContents().trim().equals(field))
							{
								fieldToIndex.put(field, k);
								break;
							}
						}
					}
					found = true;
					break;
				}
			}
			if(!found)
			{
				System.err.println("적절한 excel 포맷이 아닙니다.");
				return false;
			}
			
			Connection conn = SQLiteManager.createDatabase(outputFilename, false);
			
			List<String> finalFieldList = new LinkedList<>();
			for(String field : finalFields)
				finalFieldList.add(finalConfig.transformColumn(field));
			
			String format = "";
			String sqlFields = "";
			for(int k=0; k< finalFieldList.size(); k++)
			{
				if(k+1 == finalFields.size())
				{
					sqlFields += "`" + finalFieldList.get(k) + "`";
					format += "?";
				}
				else
				{
					sqlFields += "`" + finalFieldList.get(k) + "`,";
					format += "?,";
				}
			}
			
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO `grade`(" + sqlFields + ") VALUES(" + format + ")");
			skip_empty_row : for(startRow = startRow + 1; startRow < sheet.getRows(); startRow++)
			{
				Cell[] cells = sheet.getRow(startRow);
				stmt.clearParameters();
				for(String key : fieldToIndex.keySet())
				{
					int index = fieldToIndex.get(key);
					String finalKey = finalConfig.transformColumn(key);
					String finalValue = finalConfig.transformValue(key, cells[index].getContents().trim());
					if(finalValue.length() == 0)
						continue skip_empty_row;
					
					int sqlIndex = finalFieldList.indexOf(finalKey);
					stmt.setString(sqlIndex + 1, finalValue);
				}
				stmt.executeUpdate();
			}
			stmt.close();		
			
			conn.close();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			return false;
		}
	}
}
