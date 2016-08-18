/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author andro
 */
public class XML_PropertiesMap extends XML_Properties {

    private Map<String, String> map;
    private final ArrayList<XML_Property> properties = new ArrayList<>();
    private Object key;
    protected String keyName;
    protected String valueName;

    protected XML_PropertiesMap(String name) {
        this(name, null);
    }

    protected XML_PropertiesMap(String name, Map<String, String> map) {
        super(name);
        setMap(map);
    }

    private void setMap(Map<String, String> m) {
        if (m != null) {
            this.map = m;
            m.entrySet().stream().forEach(this::addToList);
        } else {
            this.map = new LinkedHashMap<>();
        }
    }

    public XML_PropertiesMap(String name, String keyName, String valueName) {
        this(name, null, keyName, valueName);
    }
    
    public XML_PropertiesMap(String name, Map<String, String> map, String keyName, String valueName) {
        super(name);
        this.keyName = keyName;
        this.valueName = valueName;
        setMap(map);
    }

    private void addToList(Map.Entry<String, String> t) {
        properties.add(new XML_PropertySimple(getKeyName()).setValue(t.getKey()));
        properties.add(new XML_PropertySimple(getValueName()).setValue(t.getValue()));
    }

    protected String getKeyName() {
        return keyName;
    }

    protected String getValueName() {
        return valueName;
    }

    @Override
    public XML_Property setProperty(String name, Object value) {
        if (getKeyName().equals(name)) {
            XML_Property prop = new XML_PropertySimple(name);
            prop.setValue(value);
            properties.add(prop);
            key = value;
            return prop;
        } else if (getValueName().equals(name)) {
            XML_Property prop = new XML_PropertySimple(name);
            prop.setValue(value);
            properties.add(prop);
            map.put(String.valueOf(key), String.valueOf(value));
            return prop;
        }
        return null;
    }

    @Override
    public Iterator<XML_Property> iterator() {
        return properties.iterator();
    }

    @Override
    public Object getValue() {
        return map;
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Object getObject() {
        return map;
    }
}
