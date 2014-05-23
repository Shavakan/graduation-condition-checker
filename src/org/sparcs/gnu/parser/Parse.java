package org.sparcs.gnu.parser;

import java.io.FileReader;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.sparcs.gnu.catalog.Catalog;

import xtc.parser.ParseError;
import xtc.parser.SemanticValue;

public class Parse {

	/**
	 * Parse specification written in intermediate representation 
	 * @param input
	 * @return {@link Catalog} instance
	 */
	public static Catalog parseCatalog(String input) {
		Catalog result = null;
		//TODO implement
		
		return result;
	}

	/**
	 * Parse user input to make intermediate representation
	 * @param input
	 * @return intermediate representation
	 * @throws Exception 
	 */
	public static String parseRawInput(String input) throws Exception {
		String result = null;
		String filename = System.getProperty("user.dir") + "/conf/cs.conf";
		
		InputGrammar parse = new InputGrammar(new FileReader(filename), filename);
		Object o = parse.pSpec(0);
		System.out.println();
		if(o instanceof SemanticValue) {
			SemanticValue res = (SemanticValue) o;
			Document d = res.semanticValue();
			XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
			out.output(d, System.out);
		}
		else if(o instanceof ParseError) {
			System.err.println("?");
		}
		else {
			System.out.println("?");
		}
		
		return result;
	}
	
	/** this main method is for testing above functions.
	 * free to remove this
	 * @author coffee
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		parseRawInput("");
	}
}
