package org.sparcs.gnu.catalog;

import org.jdom2.Element;

public class MutualRecog {
	private String exceptionQuery;
	private String exceptionOrigin;
	private String exceptionNew;
	private String exceptionCode;
	private double exceptionCredit;
	private Rule origin;
	
	public Rule getOrigin()
	{
		return origin;
	}
	
	public MutualRecog(Element mutual, Rule origin)
	{
		this.origin = origin;
		exceptionQuery = mutual.getChildText("쿼리");
		exceptionOrigin = mutual.getChildText("좌변");
		exceptionNew = mutual.getChildText("우변");
		exceptionCode = mutual.getChildText("우변코드");
		String exceptionCreditString = mutual.getChildText("학점");
		
		if(exceptionCreditString == null || exceptionCreditString.length() == 0)
			exceptionCreditString = "3";
		
		exceptionCredit = Double.parseDouble(exceptionCreditString);
	}
	
	public double getExceptionCredit()
	{
		return exceptionCredit;
	}

	public String getExceptionQuery() {
		return exceptionQuery;
	}

	public String getExceptionOrigin() {
		return exceptionOrigin;
	}

	public String getExceptionNew() {
		return exceptionNew;
	}
	
	public String getExceptionCode() {
		return exceptionCode;
	}
	
	@Override
	public String toString()
	{
		return "" + exceptionOrigin + " -> " + exceptionNew;
	}
}
