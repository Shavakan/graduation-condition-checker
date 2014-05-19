package org.sparcs.gnu.converter;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
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
		// TODO parse .csv file and write sqlite file of name outputFilename
		List<String> indexField = new LinkedList<>();
		indexField.add("No");
		indexField.add("년도");
		indexField.add("학기");
		indexField.add("학과");
		indexField.add("교과목");
		indexField.add("과목번호");
		indexField.add("분반");
		indexField.add("구분");
		
		Set<String> allField = new HashSet<>();
		for(String field : indexField)
			allField.add(field.trim());
		
		try
		{
			Map<String, Integer> fieldToIndex = new HashMap<>();
			Workbook xls = Workbook.getWorkbook(new File(xlsFilePath));
			Sheet sheet = xls.getSheet("grade");
			int startRow = 0;
			boolean found = false;
			for(; startRow < sheet.getRows(); startRow++)
			{
				Cell[] cells = sheet.getRow(startRow);
				Set<String> allCell = new HashSet<>();
				for(Cell cell : cells)
					allCell.add(cell.getContents().trim());
				
				if(allCell.containsAll(allField))
				{
					for(String field : allField)
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
			
			Connection conn = SQLiteManager.createDatabase(outputFilename, true);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	//TODO this is temp, remove
	public static void main(String[] args)
	{
		ExcelConverter conv = new ExcelConverter("grade.xls");
		conv.convert("output.db");
	}
}
