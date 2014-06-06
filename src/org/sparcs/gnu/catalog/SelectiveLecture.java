package org.sparcs.gnu.catalog;

public class SelectiveLecture {
	private String selectionCode;
	private String selectionTypeFrom;
	private String selectionTypeTo;
	private String selectionNumber;
	private String selectionCredit;
	public String getSelectionCode() {
		return selectionCode;
	}
	public String getSelectionTypeFrom() {
		return selectionTypeFrom;
	}
	public String getSelectionTypeTo() {
		return selectionTypeTo;
	}
	public SelectiveLecture(String selectionNumber, String selectionCode, String selectionTypeFrom,
			String selectionTypeTo, String selectionCredit) {
		super();
		this.selectionCode = selectionCode;
		this.selectionTypeFrom = selectionTypeFrom;
		this.selectionTypeTo = selectionTypeTo;
		this.selectionNumber = selectionNumber;
		this.selectionCredit = selectionCredit;
	}
	
	public Replace getReplace()
	{
		return new Replace(selectionCode, selectionCode, selectionNumber, "전선", selectionCredit);
	}
}
