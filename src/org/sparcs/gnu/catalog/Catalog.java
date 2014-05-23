package org.sparcs.gnu.catalog;

import java.util.HashSet;
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
	private Replace replace;
	private Set<Rule> rules;
	private Exception exception;
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
			//TODO fill in.
			catalog.replace = new Replace();
			catalog.rules = new HashSet<>();


			for(Element cond : rootElement.getChildren("조건"))
			{
				catalog.rules.add(new Rule(cond.getName(), cond.getChildText("쿼리"), cond.getChildText("최소")));
			}
			catalog.exception = new Exception();
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
}
