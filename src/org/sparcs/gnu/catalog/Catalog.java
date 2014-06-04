package org.sparcs.gnu.catalog;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 * Having graduation conditions.
 * @author Alphamin
 *
 */
public class Catalog {
	private Set<Replace> replaces;
	private Set<Rule> rules;
	private Map<String, Exception> exceptionMap;
	private String departmentCode;
	/**
	 * Constructor.
	 */
	private Catalog(){
	}
	/**
	 * Create a catalog object and three stage objects. 
	 * @param path Converted grammar path
	 * @return Created catalog
	 */
	public static Catalog loadCatalog(String path){
		try
		{
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(path);

			Element rootElement = document.getRootElement();
			

			Catalog catalog = new Catalog();
			catalog.departmentCode = rootElement.getAttributeValue("code");
			catalog.replaces = new HashSet<>();
			catalog.rules = new HashSet<>();
			catalog.exceptionMap = new HashMap<>();


			for(Element cond : rootElement.getChildren("조건"))
			{
				catalog.rules.add(new Rule(cond)); 
			}
			for(Element exception : rootElement.getChildren("예외"))
			{
				Exception e = new Exception(exception);
				catalog.exceptionMap.put(e.getTarget(), e); 
			}
			for(Element replace : rootElement.getChildren("대체"))
			{
				catalog.replaces.add(new Replace(replace));
			}
			return catalog;
		}
		catch(java.lang.Exception e)
		{
			e.printStackTrace(System.err);
			return null;
		}
		
	}
	
	public Set<Rule> getRules()
	{
		return rules;
	}
	
	public Set<Replace> getReplaces()
	{
		return replaces;
	}
	
	public String getDepartmentCode()
	{
		return departmentCode;
	}
	
	public Exception getException(String key)
	{
		return exceptionMap.get(key);
	}
}
