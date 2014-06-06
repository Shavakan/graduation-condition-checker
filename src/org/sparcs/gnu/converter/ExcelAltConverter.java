package org.sparcs.gnu.converter;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
class ExcelAltConverter extends Converter {
	
	private String xlsFilePath;

	public ExcelAltConverter(String filepath) {
		xlsFilePath = filepath;
	}
	
	/**
	 * Converts Excel(.csv) file to sqlite file.
	 * @param outputFilename Filename of sqlite containing a user's file history
	 */
	@Override
	public boolean convert(String outputFilename) {
		try
		{
			Workbook xls = Workbook.getWorkbook(new File(xlsFilePath));
			if(xls.getSheet(0).getRow(0)[0].getContents().trim().contains("졸업사정"))
				for(Sheet sheet : xls.getSheets())
					doSheet(sheet, outputFilename);
			else
				return false;
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			return false;
		}
	}
	private static void doSheet(Sheet sheet, String outputFilename) throws SQLException
	{
		int startRow = 0;
		for(; startRow < sheet.getRows(); startRow++)
		{
			Cell[] row = sheet.getRow(startRow);
			if(row == null || row.length == 0)
				continue;
			if(row[0].getContents().trim().equals("이수구분"))
				break;
		}
		startRow++;
		
		List<List<String>> compressed = new LinkedList<>();
		
		for(; startRow < sheet.getRows(); startRow++)
		{
			Cell[] row = sheet.getRow(startRow);
			if(row == null || row.length == 0 || row[0].getContents().trim().length() == 0)
				continue;
			List<String> listRow = new LinkedList<>();
			for(Cell c : row)
			{
				String s = c.getContents().trim();
				if(s.length() == 0)
					continue;
				if(s.equals("Y"))
					continue;
				if(s.equals("수강중"))
					s = "S";
				listRow.add(s);
			}
			if(listRow.size() != 9)
				continue;
			{
				int k=0;

				String type = listRow.get(k);
				if(type.contains("("))
					type = type.substring(0, type.indexOf("("));
				if(type.contains("연구"))
					type = "연구";
				else if(type.length() > 3)
					type = "" + type.charAt(0) + type.charAt(type.length()-2);
				listRow.set(k, type);

			}
			{
				int k=3;

				String code = listRow.get(k);
				String number = listRow.get(k+1);
				code = code + number.substring(3);
				listRow.set(k, code);
			}
			compressed.add(listRow);
		}
						
		Connection conn = SQLiteManager.createDatabase(outputFilename, false);
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO `grade`(`type`, `year`, `semester`, `code`, `number`, `common_name`, `au`, `credit`, `grade`) VALUES(?,?,?,?,?,?,?,?,?)");
		for(List<String> iter : compressed)
		{
			stmt.clearParameters();
			for(int k=0; k<iter.size(); k++)
				stmt.setString(k+1, iter.get(k));
			stmt.executeUpdate();
		}
		stmt.close();
	}
}
