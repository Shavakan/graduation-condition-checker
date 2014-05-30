package org.sparcs.gnu.converter;

import java.util.HashMap;
import java.util.Map;

public class EnglishExcelConfig extends ExcelConfig{
	public EnglishExcelConfig(){
		column_map = new HashMap<String, String>();
		column_map.put("No", "index");
		column_map.put("Year", "year");
		column_map.put("Term", "semester");
		column_map.put("System No.", "number");
		column_map.put("Course No.", "code");
		column_map.put("Section", "section");
		column_map.put("Classification", "type");
		column_map.put("Credit", "credit");
		column_map.put("AU", "au");
		column_map.put("Grade", "grade");

		Map<String, String> semester_map = new HashMap<>();
		semester_map.put("Transfer", "transfer");
		semester_map.put("Spring", "spring");
		semester_map.put("Autumn", "autumn");
		semester_map.put("Summer", "summer");
		semester_map.put("Winter", "winter");
		value_map = new HashMap<String, Map<String, String>>();
		value_map.put("Term", semester_map);
		
		
			
	}
}
