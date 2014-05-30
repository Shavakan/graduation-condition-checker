package org.sparcs.gnu.catalog;
/**
 * Having rule information
 * @author Alphamin
 *
 */
public class Rule {
	private String query;
	private String min;
	private String name;

	/**
	 * Constructor.
	 */
	public Rule(String name, String query, String min){
		this.name = name;
		this.query = query;
		this.min = min;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getQuery()
	{
		return query;
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
}
