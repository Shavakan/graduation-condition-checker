package org.sparcs.gnu.converter;

import java.util.HashMap;
import java.util.Set;

public class HangulExcelConfig extends ExcelConfig{
	HashMap<String, String> column_map;
	HashMap<String, HashMap<String,String>> value_map;
	public HangulExcelConfig(){
		column_map = new HashMap<String, String>() {{
			put("No", "index");
			put("년도", "year");
			put("학기", "semester");
			put("교과목", "number");
			put("과목번호", "code");
			put("분반", "section");
			put("구분", "type");
			put("학", "credit");
			put("AU", "au");
			put("성적", "grade");
		}};
		value_map = new HashMap<String, HashMap<String, String>>(){{
			put("학기", new HashMap<String, String>(){{
				put("기이수", "transfer");
				put("봄학기", "spring");
				put("가을학기", "autumn");
				put("여름학기", "summer");
				put("겨울학기", "winter");
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
