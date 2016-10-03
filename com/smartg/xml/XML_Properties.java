/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author andro
 */
public interface XML_Properties extends XML_Property, Iterable<XML_Property> {

    boolean isEmpty();

    boolean isSaveEmptyObjects();

    XML_Property setProperty(String name, Object value);

    @Override
    public default XML_Property setValue(Object value) {
        return this;
    }

    @Override
    default void writeXml(XMLStreamWriter xmlStreamWriter, int indent) {
        if (isEmpty() && !isSaveEmptyObjects()) {
            return;
        }
        try {
            XML_Util.writeStartElement(xmlStreamWriter, indent, getName());
            Iterator<XML_Property> iterator = iterator();
            while(iterator.hasNext()) {
                XML_Property next = iterator.next();
                next.writeXml(xmlStreamWriter, indent + 1);
            }
//            forEach((XML_Property t) -> {
//                t.writeXml(xmlStreamWriter, indent + 1);
//            });
            XML_Util.writeEndElement(xmlStreamWriter, indent);
        } catch (XMLStreamException ex) {
            Logger.getLogger(XML_Properties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
