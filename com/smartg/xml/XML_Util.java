/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;
import java.util.Map;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author andro
 */
public class XML_Util {

    public static String getIndentString(int n) {
        StringBuilder sb = new StringBuilder(n + 1);
        sb.append("\n");
        for (int i = 0; i < n; i++) {
            sb.append("\t");
        }
        return sb.toString();
    }

    public static void writeEndElement(XMLStreamWriter xmlStreamWriter, int indent) throws XMLStreamException {
        //write end tag of Graphing element
        xmlStreamWriter.writeCharacters(getIndentString(indent));
        xmlStreamWriter.writeEndElement();
    }

    public static void writeElement(XMLStreamWriter xmlStreamWriter, String name, String elem, int indent) throws XMLStreamException {
        writeStartElement(xmlStreamWriter, indent, name);
        xmlStreamWriter.writeCharacters(getIndentString(indent + 1) + elem);
        writeEndElement(xmlStreamWriter, indent);
    }

    public static void writeStartElement(XMLStreamWriter xmlStreamWriter, int indent, String rootElement) throws XMLStreamException {
        xmlStreamWriter.writeCharacters(getIndentString(indent));
        xmlStreamWriter.writeStartElement(rootElement);
    }

    public static void writeXml(Writer output, XML_Properties t) throws FactoryConfigurationError, XMLStreamException {
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(output);
        //start writing xml file
        xmlStreamWriter.writeStartDocument("UTF-8", "1.0");
        t.writeXml(xmlStreamWriter, 0);
        xmlStreamWriter.writeEndDocument();
        //flush data to file and close writer
        xmlStreamWriter.flush();
        xmlStreamWriter.close();
    }

    public static String toXml(XML_Properties t) {
        StringWriter output = new StringWriter();
        try {
            XML_Util.writeXml(output, t);
        } catch (FactoryConfigurationError | XMLStreamException ex) {
            Logger.getLogger(XML_Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output.getBuffer().toString();
    }

    public static XML_Properties parseXml(String input, String elementName, Class<? extends XML_Properties> elementClass) {
        Objects.requireNonNull(elementClass);
        XML_Stack stack = new XML_Stack();
        stack.register(elementName, elementClass);

        return parseXml(input, elementName, stack);
    }

    public static XML_Properties parseXml(String input, String elementName, XML_Support xmlSupport) {
        Objects.requireNonNull(xmlSupport);
        XML_Stack stack = new XML_Stack();
        stack.register(elementName, xmlSupport);

        return parseXml(input, elementName, stack);
    }

    private static XML_Properties parseXml(String input, String elementName, XML_Stack stack) {
        Objects.requireNonNull(input);
        Objects.requireNonNull(elementName);
        if (input.isEmpty()) {
            return null;
        }
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        xmlInputFactory.setProperty(XMLInputFactory.IS_COALESCING, true);
        StringReader reader = new StringReader(input);
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(reader);
            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    String name = startElement.getName().getLocalPart();
                    xmlEvent = xmlEventReader.nextEvent();
                    String value = xmlEvent.asCharacters().getData().trim();
                    stack.start(name, value);
                } else if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    String name = endElement.getName().getLocalPart();
                    stack.end(name);
                }
            }
        } catch (XMLStreamException e) {
            Logger.getGlobal().log(Level.SEVERE, null, e);
        }
        XML_Properties lastObject = (XML_Properties) stack.getLastObject();
        return lastObject;
    }

}
