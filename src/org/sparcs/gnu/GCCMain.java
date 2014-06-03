package org.sparcs.gnu;

import java.io.File;
import java.sql.Connection;

import org.sparcs.gnu.catalog.Catalog;
import org.sparcs.gnu.checker.GraduationChecker;
import org.sparcs.gnu.checker.ProcessInfo;
import org.sparcs.gnu.converter.Converter;
import org.sparcs.gnu.converter.SQLiteManager;
import org.sparcs.gnu.course.GradeInfo;
import org.sparcs.gnu.parser.Parse;

public class GCCMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Connection conn = SQLiteManager.createDatabase("tmp" + File.separator + "output.db", true);
		Converter conv = Converter.converterObject("tmp" + File.separator + "근홍.xls");
		conv.convert("tmp" + File.separator + "output.db");
		
		conv = Converter.converterObject("tmp" + File.separator + "근홍수강.xls");
		conv.convert("tmp" + File.separator + "output.db");
		
		Class.forName("org.sparcs.gnu.course.GradeInfo");
		
		GradeInfo info = new GradeInfo(conn);
		Parse.parseRawInput("conf" + File.separator + "cs.conf", "tmp" + File.separator + "cs.xml");
		Parse.parseException("conf" + File.separator + "cs_except_double.conf", "tmp" + File.separator + "cs.xml");
		Catalog catalog = Catalog.loadCatalog("tmp" + File.separator + "cs.xml");

		GraduationChecker checker = new GraduationChecker(catalog);
		ProcessInfo result = checker.process(info);
	}
}
