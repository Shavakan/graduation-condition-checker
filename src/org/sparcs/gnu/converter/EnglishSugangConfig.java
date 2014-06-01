package org.sparcs.gnu.converter;

import java.util.HashMap;

public class EnglishSugangConfig extends ExcelConfig {
	public EnglishSugangConfig(){
		column_map.put("No.", "index");
		column_map.put("System Nbr.", "number");
		column_map.put("Course Nbr.", "code");
		column_map.put("Section", "section");
		column_map.put("Course Type", "type");
		column_map.put("Credit", "credit");
		column_map.put("AU", "au");
	}
}
