package org.sparcs.gnu.parser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
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
	 * @param conf
	 * @param xml
	 * @return intermediate representation
	 * @throws Exception
	 */
	public static boolean parseRawInput(String conf, String xml) {
		try
		{
			String filename = conf;

			InputGrammar parse = new InputGrammar(new FileReader(filename), filename);
			Object o = parse.pSpec(0);
			if(o instanceof SemanticValue) {
				SemanticValue res = (SemanticValue) o;
				Document d = res.semanticValue();
				XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
				FileWriter outfile = new FileWriter(xml);
				out.output(d, outfile);
			}
			else if(o instanceof ParseError) {
				System.err.println("ParseError");
			}
			else {
				System.err.println("Unknown state after parsing");
			}

			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			return false;
		}
	}

	/**
	 * Parse exception input
	 * @param conf
	 * @param xml
	 * @return
	 */
	public static boolean parseException(String conf, String xml) {
		try
		{
			String filename = conf;
			
			ExceptionGrammar parse = new ExceptionGrammar(new FileReader(filename), filename);
			Object o = parse.pSpec(0);
			if(o instanceof SemanticValue) {
				SemanticValue res = (SemanticValue) o;
				List<Element> l = res.semanticValue();

				SAXBuilder builder = new SAXBuilder();
				Document prev = builder.build(new File(xml));

				for(Element e : l){
					prev.getRootElement().addContent(e);
				}
				
				XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
				FileWriter outfile = new FileWriter(xml);
				out.output(prev, outfile);
			}
			else if(o instanceof ParseError) {
				System.err.println("ParseError");
			}
			else {
				System.err.println("Unknown state after parsing");
			}

			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			return false;
		}
	}

	/**
	 * Parse replace input
	 * @param conf
	 * @param xml
	 * @return
	 */
	public static boolean parseReplace(String conf, String xml) {
		try
		{
			String filename = conf;
			
			ReplaceGrammar parse = new ReplaceGrammar(new FileReader(filename), filename);
			Object o = parse.pSpec(0);
			if(o instanceof SemanticValue) {
				SemanticValue res = (SemanticValue) o;
				List<Element> l = res.semanticValue();

				SAXBuilder builder = new SAXBuilder();
				Document prev = builder.build(new File(xml));

				for(Element e : l){
					prev.getRootElement().addContent(e);
				}
				
				XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
				FileWriter outfile = new FileWriter(xml);
				out.output(prev, outfile);
			}
			else if(o instanceof ParseError) {
				System.err.println("ParseError");
			}
			else {
				System.err.println("Unknown state after parsing");
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
		parseException("conf" + File.separator + "ee_minor.conf", "tmp" + File.separator + "cs.xml");
		parseReplace("conf" + File.separator + "replace.conf", "tmp" + File.separator + "cs.xml");
		SAXBuilder s = new SAXBuilder();
		XMLOutputter o = new XMLOutputter(Format.getPrettyFormat());
		o.output(s.build("tmp" + File.separator + "cs.xml"), System.out);
	}*/
}
