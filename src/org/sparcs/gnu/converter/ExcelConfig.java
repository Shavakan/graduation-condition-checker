package org.sparcs.gnu.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class ExcelConfig {
	protected Map<String, String> column_map;
	protected Map<String, Map<String,String>> value_map;
	
	protected ExcelConfig()
	{
		column_map = new HashMap<String, String>();
		value_map = new HashMap<String, Map<String,String>>();
	}
	
	public Set<String> getAllColumn(){
		return column_map.keySet();
	}
	
	public String transformColumn(String column){
		if(column_map.containsKey(column))
			return column_map.get(column);
		else
			return column;
	}
	
	public String transformValue(String column, String value){
		if(value_map.containsKey(column))
		{
			Map<String, String> submap = value_map.get(column);
			if(submap.containsKey(value))
				return submap.get(value);
			else
				return value;
		}
		else
			return value;
	}
}
