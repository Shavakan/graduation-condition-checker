package org.sparcs.gnu;

import java.sql.Connection;

import org.sparcs.gnu.converter.Converter;
import org.sparcs.gnu.converter.SQLiteManager;
import org.sparcs.gnu.course.GradeInfo;

public class GCCMain {

	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		Converter conv = Converter.converterObject("grade.xls");
		conv.convert("output.db");
		Connection conn = SQLiteManager.createDatabase("output.db", false);
		Class.forName("org.sparcs.gnu.course.GradeInfo");
		GradeInfo info = new GradeInfo(conn);
		info.preprocess();
	}

}
