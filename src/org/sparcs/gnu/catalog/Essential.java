package org.sparcs.gnu.catalog;

import org.jdom2.Element;

public class Essential {
	private String essentialQuery;
	private String essentialCode;
	private double essentialCredit;
	
	public Essential(Element essence)
	{
		essentialQuery = essence.getChildText("쿼리");
		essentialCode = essence.getChildText("코드");
		essentialCredit = Double.parseDouble(essence.getChildText("학점"));
	}

	public String getEssentialQuery() {
		return essentialQuery;
	}

	public String getEssentialCode() {
		return essentialCode;
	}

	public double getEssentialCredit() {
		return essentialCredit;
	}
}
