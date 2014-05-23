package org.sparcs.gnu;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.sparcs.gnu.converter.Converter;
import org.sparcs.gnu.converter.SQLiteManager;
import org.sparcs.gnu.course.GradeInfo;
import org.sparcs.gnu.parser.Parse;

public class GCCMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Converter conv = Converter.converterObject("tmp" + File.separator + "grade.xls");
		conv.convert("tmp" + File.separator + "output.db");
		Connection conn = SQLiteManager.createDatabase("tmp" + File.separator + "output.db", false);
		Class.forName("org.sparcs.gnu.course.GradeInfo");
		GradeInfo info = new GradeInfo(conn);
		info.preprocess();
		Parse.parseRawInput("conf" + File.separator + "cs.conf", "tmp" + File.separator + "cs.xml");

		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build("tmp" + File.separator + "cs.xml");

		Element rootElement = document.getRootElement();
		for(Element cond : rootElement.getChildren("조건"))
		{
			try
			{
				String sql = cond.getChildText("쿼리");
				System.out.println(sql);
				String value = cond.getChildText("최소");
				boolean ret = false;
				String msg = "";
				if(value.contains("."))
				{
					double comp = Double.parseDouble(value);
					PreparedStatement stmt = conn.prepareStatement(sql);
					ResultSet result = stmt.executeQuery();

					if(result.next())
					{
						double myComp = Double.parseDouble(result.getString(1));
						if(myComp >= comp)
							ret = true;
						msg = "" + myComp + "/" + comp;
					}
					result.close();
					stmt.close();
				}
				else
				{
					long comp = Long.parseLong(value);
					PreparedStatement stmt = conn.prepareStatement(sql);
					ResultSet result = stmt.executeQuery();
					if(result.next())
					{
						System.out.println(result.getString(1));
						long myComp = Long.parseLong(result.getString(1));
						if(myComp >= comp)
							ret = true;
						msg = "" + myComp + "/" + comp;
					}
					result.close();
					stmt.close();
				}
				System.out.println("" + ret + " " + msg);
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

}
