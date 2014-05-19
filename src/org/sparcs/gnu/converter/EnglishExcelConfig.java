package org.sparcs.gnu.converter;

import java.util.HashMap;
import java.util.Set;

public class EnglishExcelConfig {
	HashMap<String, String> column_map;
	HashMap<String, HashMap<String,String>> value_map;
	public EnglishExcelConfig(){
		column_map = new HashMap<String, String>() {{
			put("No", "index");
			put("Year", "year");
			put("Term", "semester");
			put("System No.", "number");
			put("Course No.", "code");
			put("Section", "section");
			put("Classification", "type");
			put("Credit", "credit");
			put("AU", "au");
			put("Grade", "grade");
		}};
		value_map = new HashMap<String, HashMap<String, String>>(){{
			put("Term", new HashMap<String, String>(){{
				put("Transfer", "transfer");
				put("Spring", "spring");
				put("Autumn", "autumn");
				put("Summer", "summer");
				put("Winter", "winter");
			}});
		}};
	}
	public Set<String> getAllColumn(){
		return column_map.keySet();
	}
	public String transformColumn(String column){
		return column_map.get(column);
	}
	public String transformValue(String column, String value){
		return (value_map.get(column)).get(value);
	}
}
