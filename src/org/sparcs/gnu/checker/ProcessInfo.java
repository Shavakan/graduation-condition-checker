package org.sparcs.gnu.checker;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains information of a single graduation assessment.
 * It contains whether he/she can graduate, why he/she can graduate, or how he/she can graduate, etc.
 * @author leeopop
 *
 */
public class ProcessInfo {
	
	private Map<String, Double> totalMap;
	private Map<String, Double> exceptionMap;
	private Map<String, Double> completeMap;
	
	ProcessInfo()
	{
		totalMap = new HashMap<>();
		exceptionMap = new HashMap<>();
		completeMap = new HashMap<>();
	}
	
	void addTotal(String key, double value)
	{
		totalMap.put(key, value);
	}
	
	void addException(String key, double value)
	{
		exceptionMap.put(key, value);
	}
	
	void addComplete(String key, double value)
	{
		completeMap.put(key, value);
	}
	
	public double getTotal(String key)
	{
		Double ret = totalMap.get(key);
		if(ret == null)
			return -1;
		return ret.doubleValue();
	}
	
	public double getException(String key)
	{
		Double ret = exceptionMap.get(key);
		if(ret == null)
			return -1;
		return ret.doubleValue();
	}
	
	public double getComplete(String key)
	{
		Double ret = completeMap.get(key);
		if(ret == null)
			return -1;
		return ret.doubleValue();
	}
}
