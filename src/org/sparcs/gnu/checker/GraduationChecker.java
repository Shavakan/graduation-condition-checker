package org.sparcs.gnu.checker;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.sparcs.gnu.catalog.Catalog;
import org.sparcs.gnu.catalog.Essential;
import org.sparcs.gnu.catalog.Exception;
import org.sparcs.gnu.catalog.MutualRecog;
import org.sparcs.gnu.catalog.Rule;
import org.sparcs.gnu.course.GradeInfo;

/**
 * This class loads a catalog and process each student's grade information to determine whether he/she can graduate.
 * @author leeopop
 *
 */
public class GraduationChecker {
	private Catalog catalog;

	/**
	 * Create a GCC with a given catalog.
	 * @param catalog Catalog information to be used.
	 */
	public GraduationChecker(Catalog catalog)
	{
		this.catalog = catalog;
	}
	
	/**
	 * Process an individual's graduation assessment using his/her grade information. 
	 * @param info An individual's past grade information.
	 * @return ProcessInfo that contains information of current assessment.
	 */
	public ProcessInfo process(GradeInfo info)
	{
		return process(info, new LinkedList<MutualRecog>());
	}
	public ProcessInfo process(GradeInfo info, List<MutualRecog> mutualRecogs)
	{
		info.preprocess();
		
		ProcessInfo result = new ProcessInfo();
		
		info.doReplace(catalog.getReplaces());
		
		Map<String, Double> mutualMap = new HashMap<>();
		for(Rule rule : catalog.getRules())
		{
			double resultMutual = 0.0;
			String resultKey = rule.getName();
			for(MutualRecog mutual : mutualRecogs)
			{
				if(mutual.getOrigin() == rule)
				{
					if(info.applyMutualRecog(mutual.getExceptionCode(), mutual.getExceptionOrigin(), mutual.getExceptionNew()))
					{
						result.setException(resultKey, mutual.getExceptionCredit());
						result.addTaken(resultKey, "[상호인정] " + mutual.getExceptionOrigin() + " -> " + mutual.getExceptionNew() + " (" + Math.round(mutual.getExceptionCredit()) + ")");
						resultMutual += mutual.getExceptionCredit();
					}
				}
			}
			mutualMap.put(resultKey, resultMutual);
		}

		for(Rule rule : catalog.getRules())
		{
			String msg = "";
			String value = rule.getMinRequirement();
			boolean ret = false;
			
			String resultKey = rule.getName();
			double resultTotal = Double.parseDouble(value);
			double resultComplete = 0.0;
			double resultException = 0.0;
			double resultMutual = mutualMap.get(resultKey);
			double resultFail = 0.0;

			Exception except = catalog.getException(resultKey);
			if(except != null)
			{
				resultException += resultTotal - except.getMinRequirement();
				result.addExceptionMessage(resultKey, resultKey + ": " + resultTotal + " -> " + except.getMinRequirement());
			}

			if(resultTotal > 0.0001)
				for(Essential essence : rule.getEssentialList())
				{
					if(!info.checkEssential(essence.getEssentialQuery()))
					{
						resultFail += essence.getEssentialCredit();
						result.addFailList(resultKey, "[필수과목] " + essence.getEssentialCode() + "(" + Math.round(essence.getEssentialCredit()) + ")");
					}
				}

			if(value.contains("."))
			{
				double total = Double.parseDouble(value);
				double comp = total - resultException;
				String checkData = info.check(rule.getQuery());
				if(checkData == null)
					checkData = "0.0";
				double myComp = Double.parseDouble(checkData);
				myComp += resultMutual;
				if(myComp >= comp)
					ret = true;
				if(resultException > 0.00001)
				{
					msg = "" + myComp + "/(" + total + " - " + resultException + ")";
				}
				else
				{
					msg = "" + myComp + "/" + total;
				}
				
				resultComplete = myComp;
			}
			else
			{
				long total = Long.parseLong(value);
				double comp = total - Math.round(resultException);
				String checkData = info.check(rule.getQuery());
				if(checkData == null)
					checkData = "0";
				long myComp = Long.parseLong(checkData);
				myComp += Math.round(resultMutual);
				if(myComp >= comp)
					ret = true;
				if(resultException > 0.00001)
				{
					msg = "" + myComp + "/(" + total + " - " + resultException + ")";
				}
				else
				{
					msg = "" + myComp + "/" + total;
				}
				
				resultComplete = myComp;
			}
			
			if(rule.getSelectQuery() != null && rule.getSelectQuery().trim().length()>0)
			{
				for(String taken : info.checkList(rule.getSelectQuery()))
					result.addTaken(resultKey, taken);
			}
			if(resultFail > 0.0001)
			{
				ret = false;
			}
			result.setTotal(resultKey, resultTotal);
			result.setException(resultKey, resultException);
			result.setComplete(resultKey, resultComplete);
			result.setFail(resultKey, resultFail);
			System.out.println("" + ret + " " + msg);
		}
		return result;
	}
}
