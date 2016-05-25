/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author andro
 */
public class XML_PropertiesMap extends XML_Properties implements Map {

    private final Map map;

    public XML_PropertiesMap(String name, Map map) {
        super(name);
        this.map = map;
    }

    @Override
    public XML_Property setProperty(String name, Object value) {
        XML_Property prop = super.setProperty(name, value);

        Object obj = map.get(name);
        if (obj == null || !obj.equals(value)) {
            map.put(name, value);
        }
        return prop;
    }

    @Override
    public Object getValue() {
        return map;
    }

    @Override
    public XML_Property setValue(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return map.get(key);
    }

    @Override
    public Object put(Object key, Object value) {
        setProperty(String.valueOf(key), value);
        return map.put(String.valueOf(key), value);
    }

    @Override
    public Object remove(Object key) {
        XML_MAP.remove(key);
        return map.remove(key);
    }

    @Override
    public void putAll(Map m) {
        map.putAll(m);
        Set<Map.Entry> entrySet = m.entrySet();
        Iterator<Map.Entry> iter = entrySet.iterator();
        while (iter.hasNext()) {
            Map.Entry next = iter.next();
            setProperty(next.getKey().toString(), next.getValue());
        }
    }

    @Override
    public void clear() {
        map.clear();
        XML_MAP.clear();
    }

    @Override
    public Set keySet() {
        return map.keySet();
    }

    @Override
    public Collection values() {
        return map.values();
    }

    @Override
    public Set entrySet() {
        return map.entrySet();
    }

    @Override
    public Object getObject() {
        return map;
    }

}
