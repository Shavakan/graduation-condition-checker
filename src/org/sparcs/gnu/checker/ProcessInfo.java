package org.sparcs.gnu.checker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	private Map<String, List<String>> takenMap;
	private Map<String, List<String>> exceptionMessageMap;
	private Map<String, List<String>> failMessageMap;
	private Set<String> allKeys;
	private double mutualRecogTotal;
	private Map<String, Double> failMap;
	
	ProcessInfo()
	{
		allKeys = new HashSet<>();
		totalMap = new HashMap<>();
		exceptionMap = new HashMap<>();
		completeMap = new HashMap<>();
		takenMap = new HashMap<>();
		exceptionMessageMap = new HashMap<>();
		failMessageMap = new HashMap<>();
		failMap = new HashMap<>();
		mutualRecogTotal = 0;
	}
	
	void setFail(String key, double value)
	{
		allKeys.add(key);
		failMap.put(key, value);
	}

	void addMutualRecog(double value)
	{
		mutualRecogTotal += value;
	}
	void setTotal(String key, double value)
	{
		allKeys.add(key);
		totalMap.put(key, value);
	}
	
	void setException(String key, double value)
	{
		allKeys.add(key);
		exceptionMap.put(key, value);
	}
	
	void setComplete(String key, double value)
	{
		allKeys.add(key);
		completeMap.put(key, value);
	}
	
	void addTaken(String key, String value)
	{
		allKeys.add(key);
		List<String> list = null;
		if(takenMap.containsKey(key))
			list = takenMap.get(key);
		else
		{
			list = new LinkedList<>();
			takenMap.put(key, list);
		}
		list.add(value);
	}
	
	void addFailList(String key, String value)
	{
		allKeys.add(key);
		List<String> list = null;
		if(failMessageMap.containsKey(key))
			list = failMessageMap.get(key);
		else
		{
			list = new LinkedList<>();
			failMessageMap.put(key, list);
		}
		list.add(value);
	}
	
	void addExceptionMessage(String key, String value)
	{
		allKeys.add(key);
		List<String> list = null;
		if(exceptionMessageMap.containsKey(key))
			list = exceptionMessageMap.get(key);
		else
		{
			list = new LinkedList<>();
			exceptionMessageMap.put(key, list);
		}
		list.add(value);
	}
	
	public double getTotal(String key)
	{
		Double ret = totalMap.get(key);
		if(ret == null)
			return -1;
		return ret.doubleValue();
	}
	
	public double getFail(String key)
	{
		Double ret = failMap.get(key);
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
	public double getMutualRecog()
	{
		return mutualRecogTotal;
	}
	
	public boolean checkKey(String key)
	{
		return allKeys.contains(key);
	}
	
	public Set<String> getKeys()
	{
		return allKeys;
	}
	public List<String> getTakenList(String key)
	{
		return takenMap.get(key);
	}
	public List<String> getExceptionList(String key)
	{
		return exceptionMessageMap.get(key);
	}
	public List<String> getFailList(String key)
	{
		return failMessageMap.get(key);
	}
}
