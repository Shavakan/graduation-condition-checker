package org.sparcs.gnu.catalog;

import org.jdom2.Element;

/**
 * Having information for exception handling.
 * @author Alphamin
 *
 */
public class Exception {
	private double minRequirement;
	private String target;
	/**
	 * Constructor.
	 */
	public Exception(Element exception){
		this.minRequirement = Double.parseDouble(exception.getChildText("최소"));
		this.target = exception.getAttributeValue("name");
	}
	public double getMinRequirement() {
		return minRequirement;
	}
	public String getTarget() {
		return target;
	}

}
