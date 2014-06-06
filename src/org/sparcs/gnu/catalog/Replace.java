package org.sparcs.gnu.catalog;

import org.jdom2.Element;

/**
 * Having information for substitution.
 * @author Alphamin
 *
 */
public class Replace {
	private String replaceOriginal;
	private String replaceNew;
	private String replaceNumber;
	private String replaceType;
	private String replaceCredit;
	
	Replace(String replaceOriginal, String replaceNew, String replaceNumber,
			String replaceType, String replaceCredit) {
		super();
		this.replaceOriginal = replaceOriginal;
		this.replaceNew = replaceNew;
		this.replaceNumber = replaceNumber;
		this.replaceType = replaceType;
		this.replaceCredit = replaceCredit;
	}

	/**
	 * Constructor.
	 */
	public Replace(Element replace){
		
		replaceOriginal = replace.getChildText("좌변");
		replaceNew = replace.getChildText("우변");
		replaceNumber = replace.getChildText("코드");
		replaceType = replace.getChildText("타입");
		replaceCredit = replace.getChildText("학점");
	}

	public String getReplaceOriginal() {
		return replaceOriginal;
	}

	public String getReplaceNew() {
		return replaceNew;
	}

	public String getReplaceNumber() {
		return replaceNumber;
	}

	public String getReplaceType() {
		return replaceType;
	}

	public String getReplaceCredit() {
		return replaceCredit;
	}
	
	@Override
	public int hashCode()
	{
		return replaceOriginal.hashCode();
	}
	
	public boolean equals(Object other)
	{
		return replaceOriginal.equals(other);
	}
}