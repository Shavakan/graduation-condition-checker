package org.sparcs.gnu.parser;

import java.io.FileReader;
import java.io.FileWriter;

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
	public static boolean parseRawInput(String input, String output) {
		try
		{
			String filename = input;

			InputGrammar parse = new InputGrammar(new FileReader(filename), filename);
			Object o = parse.pSpec(0);
			if(o instanceof SemanticValue) {
				SemanticValue res = (SemanticValue) o;
				Document d = res.semanticValue();
				XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
				FileWriter outfile = new FileWriter(output);
				out.output(d, outfile);
			}
			else if(o instanceof ParseError) {
				System.err.println("?");
			}
			else {
				System.out.println("?");
			}

			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			return false;
		}
	}
	
	/** this main method is for testing above functions.
	 * free to remove this function
	 * @author coffee
	 * @throws Exception 
	 *//*
	public static void main(String[] args) throws Exception {
		parseRawInput("conf" + File.separator + "cs.conf", "tmp" + File.separator + "cs.xml");
	}*/
}
