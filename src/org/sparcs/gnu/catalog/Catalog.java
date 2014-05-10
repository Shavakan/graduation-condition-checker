package org.sparcs.gnu.catalog;
/**
 * Having graduation conditions.
 * @author Alphamin
 *
 */
public class Catalog {
	private Replace replace;
	private Rule rule;
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
		Catalog catalog = new Catalog();
		//TODO fill in.
		catalog.replace = new Replace();
		catalog.rule = new Rule();
		catalog.exception = new Exception();
		return catalog;
		
	}
}
