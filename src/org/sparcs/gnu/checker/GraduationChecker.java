package org.sparcs.gnu.checker;

import org.sparcs.gnu.catalog.Catalog;
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
		//TODO fill in
		return null;
	}
}
