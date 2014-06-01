package org.sparcs.gnu.converter;

import java.util.HashMap;

public class HangulSugangConfig extends SugangConfig {
	public HangulSugangConfig()
	{
		column_map = new HashMap<String, String>();
		column_map.put("No", "index");
		column_map.put("전산코드", "number");
		column_map.put("과목번호", "code");
		column_map.put("분반", "section");
		column_map.put("과목구분", "type");
		column_map.put("학점", "credit");
		column_map.put("AU", "au");
	}
}
