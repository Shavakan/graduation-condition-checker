package org.sparcs.gnu.converter;

import java.util.HashMap;
import java.util.Set;

public abstract class ExcelConfig {
	HashMap<String, String> comlumn_map;
	HashMap<String, HashMap<String,String>> value_map;
	public abstract Set<String> getAllColumn();
	public abstract String transformColumn(String column);
	public abstract String transformValue(String column, String value);
}
