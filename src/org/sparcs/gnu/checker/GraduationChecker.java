package org.sparcs.gnu.checker;

import org.sparcs.gnu.catalog.Catalog;
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
		//TODO fill in
		this.catalog = catalog;
	}
	
	/**
	 * Process an individual's graduation assessment using his/her grade information. 
	 * @param info An individual's past grade information.
	 * @return ProcessInfo that contains information of current assessment.
	 */
	public ProcessInfo process(GradeInfo info)
	{
		info.preprocess();
		//TODO fill in
		
		ProcessInfo result = new ProcessInfo();

		for(Rule rule : catalog.getRules())
		{
			String msg = "";
			String value = rule.getMinRequirement();
			boolean ret = false;
			
			String resultKey = rule.getName();
			double resultTotal = 0.0;
			double resultComplete = 0.0;
			double resultException = 0.0;
			
			if(value.contains("."))
			{
				double comp = Double.parseDouble(value);
				String checkData = info.check(rule.getQuery());
				if(checkData == null)
					checkData = "0.0";
				double myComp = Double.parseDouble(checkData);
				if(myComp >= comp)
					ret = true;
				msg = "" + myComp + "/" + comp;
				
				resultTotal = comp;
				resultComplete = myComp;
				//TODO exception 처리
			}
			else
			{
				long comp = Long.parseLong(value);
				String checkData = info.check(rule.getQuery());
				if(checkData == null)
					checkData = "0";
				long myComp = Long.parseLong(checkData);
				if(myComp >= comp)
					ret = true;
				msg = "" + myComp + "/" + comp;
				
				resultTotal = comp;
				resultComplete = myComp;
				//TODO exception 처리
			}
			
			result.addTotal(resultKey, resultTotal);
			result.addException(resultKey, resultException);
			result.addComplete(resultKey, resultComplete);
			System.out.println("" + ret + " " + msg);
		}
		return result;
	}
}
