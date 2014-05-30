package org.sparcs.gnu.converter;

import java.util.HashMap;
import java.util.Map;

public class HangulExcelConfig extends ExcelConfig{
	public HangulExcelConfig()
	{
		column_map = new HashMap<String, String>();
		column_map.put("No", "index");
		column_map.put("년도", "year");
		column_map.put("학기", "semester");
		column_map.put("학과", "dept");
		column_map.put("교과목", "number");
		column_map.put("과목번호", "code");
		column_map.put("분반", "section");
		column_map.put("구분", "type");
		column_map.put("학", "credit");
		column_map.put("AU", "au");
		column_map.put("성적", "grade");
		
		value_map = new HashMap<String, Map<String, String> >();
		Map<String, String> semester_map = new HashMap<>();
		semester_map.put("기이수", "transfer");
		semester_map.put("봄학기", "spring");
		semester_map.put("가을학기", "autumn");
		semester_map.put("여름학기", "summer");
		semester_map.put("겨울학기", "winter");
		value_map.put("학기", semester_map);
		
		
	}
}