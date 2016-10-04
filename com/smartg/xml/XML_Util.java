/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
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

    public static Map xmlToMap(String input, String name) {
        XML_PropertiesMap xmlProperties = new XML_PropertiesMap(name, "key", "value");
        XML_SupportMap support = new XML_SupportMap(xmlProperties);
        parseXml(input, name, support);
        return support.getValue();
    }

    public static String mapToXml(String name, Map<String, String> map) {
        XML_PropertiesMap xmlProperties = new XML_PropertiesMap(name, map, "key", "value");
        String toXml = toXml(xmlProperties);
        return toXml;
    }

    public static TableModel xmlToTable(String input, String name) {
        XML_PropertiesTable properties = new XML_PropertiesTable(name);
        XML_SupportTable support = new XML_SupportTable(properties);
        parseXml(input, name, support);
        return support.getValue();
    }

    public static String tableToXml(String name, TableModel table) {
        XML_PropertiesTable properties = new XML_PropertiesTable(table, name);
        String toXml = toXml(properties);
        return toXml;
    }

    private static class XML_SupportMap implements XML_Support<Map<String, String>> {

        private final XML_PropertiesMap xmlMap;
        private final Map<String, String> map = new HashMap<>();

        public XML_SupportMap(XML_PropertiesMap xmlMap) {
            this.xmlMap = xmlMap;
            xmlMap.getEventManager().addXmlEventListener(this);
        }

        @Override
        public XML_Properties create() {
            return xmlMap;
        }

        @Override
        public XML_Properties create(String name) {
            return xmlMap;
        }

        @Override
        public void propertySet(XML_Event e) {
            Map<Object, Object> m = e.getMap();
            m.forEach((k, o) -> {
                map.put(k.toString(), o.toString());
            });
        }

        @Override
        public Map<String, String> getValue() {
            return map;
        }
    }

    private static class XML_SupportTable implements XML_Support<TableModel> {

        private final XML_PropertiesTable properties;
        private final DefaultTableModel model = new DefaultTableModel();
        private String [] columns;

        public XML_SupportTable(XML_PropertiesTable properties) {
            this.properties = properties;
            properties.getEventManager().addXmlEventListener(this);
        }

        @Override
        public XML_Properties create() {
            return properties;
        }

        @Override
        public XML_Properties create(String name) {
            return properties;
        }

        @Override
        public void propertySet(XML_Event e) {
            Map<Object, Object> map = e.getMap();
            System.out.println("com.smartg.xml.XML_Util.XML_SupportTable.propertySet() " + map);
            String type = (String) map.get("TYPE");
            switch (type) {
                case "WIDTH":
                    Integer w = Integer.valueOf(map.get("width").toString());
                    model.setColumnCount(w);
                    columns = new String[w];
                    break;
                case "HEIGHT":
                    Integer h = Integer.valueOf(map.get("height").toString());
                    model.setRowCount(h);
                    break;
                case "COLUMN":
                    Integer columnIndex = Integer.valueOf(map.get("columnIndex").toString());
                    String columnName = (String) map.get("columnName");
                    columns[columnIndex] = columnName;
                    break;
                case "CELL":
                    Integer x = Integer.valueOf(map.get("x").toString());
                    Integer y = Integer.valueOf(map.get("y").toString());
                    Object cellValue = map.get("cellValue");
                    model.setValueAt(cellValue, y, x);
                    break;
            }
        }

        @Override
        public TableModel getValue() {
            model.setColumnIdentifiers(columns);
            return model;
        }

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
