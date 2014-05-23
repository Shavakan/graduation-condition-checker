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

		for(Rule rule : catalog.getRules())
		{
			String msg = "";
			String value = rule.getMinRequirement();
			boolean ret = false;
			if(value.contains("."))
			{
				double comp = Double.parseDouble(value);

				double myComp = Double.parseDouble(info.check(rule.getQuery()));
				if(myComp >= comp)
					ret = true;
				msg = "" + myComp + "/" + comp;
			}
			else
			{
				long comp = Long.parseLong(value);
				long myComp = Long.parseLong(info.check(rule.getQuery()));
				if(myComp >= comp)
					ret = true;
				msg = "" + myComp + "/" + comp;
			}
			System.out.println("" + ret + " " + msg);
		}
		return null;
	}
}
