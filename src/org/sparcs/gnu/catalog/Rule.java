package org.sparcs.gnu.catalog;

import java.util.LinkedList;
import java.util.List;

import org.jdom2.Element;

/**
 * Having rule information
 * @author Alphamin
 *
 */
public class Rule {
	private String query;
	private String min;
	private String name;
	private String selectQuery;
	private String originalText;
	private List<MutualRecog> mutualRecogs;
	private List<Exception> exceptionList;
	private List<Essential> essentialList;

	/**
	 * Constructor.
	 */
	public Rule(Element cond){
		this.name = cond.getAttributeValue("name");
		this.query = cond.getChildText("쿼리");
		this.min = cond.getChildText("최소");
		this.selectQuery = cond.getChildText("목록");
		this.originalText = cond.getChildText("원문");
		this.exceptionList = new LinkedList<>();
		this.essentialList = new LinkedList<>();
		
		mutualRecogs = new LinkedList<>();
		for(Element exception : cond.getChildren("예외"))
			mutualRecogs.add(new MutualRecog(exception, this));
		for(Element essence : cond.getChildren("필수"))
			essentialList.add(new Essential(essence));
	}
	
	public List<MutualRecog> getMutualRecogs() {
		return mutualRecogs;
	}
	
	public List<Essential> getEssentialList() {
		return essentialList;
	}

	public String getOriginalText()
	{
		return originalText;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getQuery()
	{
		return query;
	}
	
	public String getSelectQuery()
	{
		return selectQuery;
	}
	
	public String getMinRequirement()
	{
		return min;
	}

	@Override
	public int hashCode()
	{
		return query.hashCode();
	}
	
	public boolean equals(Object o)
	{
		if(o instanceof Rule)
		{
			return this.query.equals(((Rule)o).query);
		}
		else
			return false;
	}
	
	public void addException(Exception e)
	{
		exceptionList.add(e);
	}
	
	public List<Exception> getExceptionList()
	{
		return exceptionList;
	}
}
