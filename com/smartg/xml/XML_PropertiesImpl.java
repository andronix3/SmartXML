/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author andro
 */
public abstract class XML_PropertiesImpl extends XML_PropertiesAbstract {

    private final Map<String, XML_Property> XML_MAP = new HashMap<>();
    private final String name;
    private boolean saveEmptyObjects;
    

    public XML_PropertiesImpl(String name) {
        this.name = name;
    }

    @Override
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

    @Override
    public boolean isEmpty() {
        return XML_MAP.isEmpty();
    }

    public final void registerProperty(XML_Property p) {
        XML_MAP.put(p.getName(), p);
    }

    public XML_Property getProperty(String name) {
        return XML_MAP.get(name);
    }

    @Override
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
    public final String getName() {
        return name;
    }
}
