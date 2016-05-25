/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListModel;

/**
 *
 * @author andro
 */
public class XML_PropertiesJList extends XML_Properties {

    private final List<XML_Property> values = new ArrayList<>();

    private final JList list;
    private final DefaultListModel model;

    public XML_PropertiesJList(String name) {
        super(name);
        this.model = new DefaultListModel<>();
        this.list = new JList(this.model);
    }

    public XML_PropertiesJList(String name, String propertyName, JList list) {
        super(name);
        this.list = list;
        ListModel m = list.getModel();

        this.model = new DefaultListModel();
        int size = m.getSize();
        for (int i = 0; i < size; i++) {
            setProperty(propertyName, m.getElementAt(i));
        }
        this.list.setModel(this.model);
    }

    @Override
    public final XML_Property setProperty(String name, Object value) {
        XML_Property prop = create(name);
        prop.setValue(value);
        values.add(prop);
        model.addElement(prop.getValue());
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
    public Object getObject() {
        return list;
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
