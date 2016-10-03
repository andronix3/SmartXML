/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author andro
 * @param <T>
 */
public class XML_PropertiesCollection<T> extends XML_PropertiesImpl {

    private final List<XML_Property> values = new ArrayList<>();
    private final Collection<T> list;

    public XML_PropertiesCollection(String name) {
        this(name, "", new ArrayList<>());
    }

    public XML_PropertiesCollection(String name, String propertyName, Collection<T> list) {
        super(name);
        this.list = list;
        list.stream().forEach((T t) -> {
            setProperty(propertyName, t);
        });
    }

    @Override
    public XML_Property setProperty(String name, Object value) {
        XML_Property prop = create(name);
        prop.setValue(value);
        values.add(prop);
        /*
         * important don't add value, add prop.getValue()!!!
         */
        list.remove((T)value);
        list.add((T) prop.getValue());
        return prop;
    }

    protected XML_Property create(String name) {
        XML_Property prop = new XML_PropertySimple(name);
        return prop;
    }


    @Override
    public Object getValue() {
        return Collections.unmodifiableList(values);
    }

    @Override
    public XML_Property setValue(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<XML_Property> iterator() {
        return values.iterator();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }
}
