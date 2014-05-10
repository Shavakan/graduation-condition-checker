package org.sparcs.gnu.converter;

public abstract class Converter {
	
	/*
	 * TODO import sqlite module
	 * TODO create sqlite data
	 */
	
	protected Converter() {}
	
	/**
	 * Creates the factory object of Converter class.
	 * @param arg
	 * @return Converter factory object
	 */
	public static Converter converterObject(String arg) {
		Converter converter = null;
		
		if (arg.endsWith(".csv")) {
			converter = new ExcelConverter(arg);
		}
		else if (arg.startsWith("http")) {
			converter = new WebConverter(arg); 
		}
		else if (arg.toLowerCase().startsWith("mysql")) {
			converter = new PortalConverter(arg);
		}
		// TODO more types of file inputs
		
		return converter;
	}
	
	/**
	 * Wrapper method for converting user's course history.
	 * @param outputFilename Filename of sqlite containing a user's course history.
	 */
	public abstract void convert(String outputFilename);
}
