package org.sparcs.gnu;

import java.io.File;
import java.sql.Connection;

import org.sparcs.gnu.catalog.Catalog;
import org.sparcs.gnu.checker.GraduationChecker;
import org.sparcs.gnu.converter.Converter;
import org.sparcs.gnu.converter.SQLiteManager;
import org.sparcs.gnu.course.GradeInfo;
import org.sparcs.gnu.parser.Parse;

public class GCCMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Converter conv = Converter.converterObject("tmp" + File.separator + "정민.xls");
		conv.convert("tmp" + File.separator + "output.db");
		Connection conn = SQLiteManager.createDatabase("tmp" + File.separator + "output.db", false);
		Class.forName("org.sparcs.gnu.course.GradeInfo");
		
		GradeInfo info = new GradeInfo(conn);
		Parse.parseRawInput("conf" + File.separator + "cs.conf", "tmp" + File.separator + "cs.xml");
		Catalog catalog = Catalog.loadCatalog("tmp" + File.separator + "cs.xml");

		GraduationChecker checker = new GraduationChecker(catalog);
		checker.process(info);
	}
}
