package org.sparcs.gnu.catalog;
/**
 * Having rule information
 * @author Alphamin
 *
 */
public class Rule {
	private String query;
	private String min;

	/**
	 * Constructor.
	 */
	public Rule(String name, String query, String min){
		//TODO fill in.
		this.query = query;
		this.min = min;
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
