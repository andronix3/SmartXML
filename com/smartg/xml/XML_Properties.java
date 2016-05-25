/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author andro
 */
public abstract class XML_Properties implements XML_Property, Iterable<XML_Property> {

    final Map<String, XML_Property> XML_MAP = new HashMap<>();
    private final String name;
    private boolean saveEmptyObjects;

    public XML_Properties(String name) {
        this.name = name;
    }

    public abstract Object getObject();

    public XML_Property setProperty(String name, Object value) {
        if (!isRegistered(name)) {
            throw new RuntimeException("Unknown XML name: " + name);
        }
        XML_Property prop = getProperty(name);
        prop.setValue(value);
        return prop;
    }

    public boolean isRegistered(String name) {
        return XML_MAP.containsKey(name);
    }

    public boolean isEmpty() {
        return XML_MAP.isEmpty();
    }

    public final void registerProperty(XML_Property p) {
        XML_MAP.put(p.getName(), p);
    }

    public XML_Property getProperty(String name) {
        return XML_MAP.get(name);
    }

    public final boolean isSaveEmptyObjects() {
        return saveEmptyObjects;
    }

    public final void setSaveEmptyObjects(boolean seo) {
        this.saveEmptyObjects = seo;
    }

    @Override
    public Iterator<XML_Property> iterator() {
        return XML_MAP.values().iterator();
    }

    @Override
    public XML_Property setValue(Object value) {
        return this;
    }

    @Override
    public void writeXml(XMLStreamWriter xmlStreamWriter, int indent) {
        if (isEmpty() && !saveEmptyObjects) {
            return;
        }
        try {
            XML_Util.writeStartElement(xmlStreamWriter, indent, getName());
            forEach((XML_Property t) -> {
                t.writeXml(xmlStreamWriter, indent + 1);
            });
            XML_Util.writeEndElement(xmlStreamWriter, indent);
        } catch (XMLStreamException ex) {
            Logger.getLogger(XML_Properties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public final String getName() {
        return name;
    }

}
