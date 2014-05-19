package org.sparcs.gnu.converter;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.io.IOUtils;

public class SQLiteManager {
	private SQLiteManager()
	{
		
	}
	
	public static Connection createDatabase(String filename, boolean create)
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + filename);
			if(create)
			{
				String sql = IOUtils.toString(new FileInputStream("database" + File.separator + "database.sql"));
				for(String line : sql.split(";"))
				{
					line = line.trim();
					if (line.length()== 0)
						continue;
					conn.createStatement().execute(line);
				}
			}
			conn.setCatalog("grade");

			return conn;
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			return null;
		}
	}
}
