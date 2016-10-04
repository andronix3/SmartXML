package com.smartg.xml.sample;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.smartg.xml.XML_Event;
import com.smartg.xml.XML_Properties;
import com.smartg.xml.XML_PropertiesObject;
import com.smartg.xml.XML_Support;
import com.smartg.xml.XML_Util;

public class ExampleOne {

    private int a;
    private int b;
    // this field is ignored
    private int c;

    public static class ExampleOneXML extends XML_PropertiesObject {

	public ExampleOneXML() {
	    this("ExampleOne", new ExampleOne());
	}

	public ExampleOneXML(String name) {
	    this(name, new ExampleOne());
	}

	public ExampleOneXML(ExampleOne obj) {
	    this("ExampleOne", obj);
	}

	public ExampleOneXML(String name, ExampleOne obj) {
	    super("ExampleOne", obj);
	    try {
		addFieldProperty("a");
		addFieldProperty("b");
		// addFieldProperty("c");
	    } catch (NoSuchFieldException ex) {
		Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
	    }
	}

    }

    public static class ExampleOneXmlSupport implements XML_Support<ExampleOne> {
	private ExampleOne exampleOne;

	public ExampleOneXmlSupport(ExampleOne exampleOne) {
	    this.exampleOne = exampleOne;
	}

	@Override
	public XML_Properties create() {
	    if (exampleOne != null) {
		return new ExampleOneXML(exampleOne);
	    }
	    return new ExampleOneXML();
	}

	@Override
	public XML_Properties create(String name) {
	    if (exampleOne != null) {
		return new ExampleOneXML(name, exampleOne);
	    }
	    return new ExampleOneXML(name, new ExampleOne());
	}

	@Override
	public void propertySet(XML_Event e) {

	}

	@Override
	public ExampleOne getValue() {
	    return exampleOne;
	}
    }

    public static void main(String[] args) {
	ExampleOne ex = new ExampleOne();

	ex.a = 7;
	ex.b = 8;
	ex.c = 3;

	ExampleOneXML eoXml = new ExampleOneXML(ex);
	ExampleOneXmlSupport xmlSupport = new ExampleOneXmlSupport(null);

	String xml = XML_Util.toXml(eoXml);
	System.out.println(xml);

	XML_Properties parseXml = XML_Util.parseXml(xml, "ExampleOne", xmlSupport);
	ExampleOne newOne = xmlSupport.getValue();
	System.out.println(newOne.a + " " + newOne.b + " " + newOne.c);
    }

}
