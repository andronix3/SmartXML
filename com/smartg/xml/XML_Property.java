/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author andro
 */
public interface XML_Property extends Cloneable {
    String getName();
    Object getValue();
    
    /**
     * Set value of XML_Property
     * @param value
     * @return this
     */
    XML_Property setValue(Object value);
    
    default void writeXml(XMLStreamWriter xmlStreamWriter, int indent) {
        try {
            String name = getName();
            String value = getValue().toString();
            XML_Util.writeElement(xmlStreamWriter, name, value, indent);
        } catch (XMLStreamException ex) {
            Logger.getLogger(XML_Property.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
