/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 *
 * @author andro
 */
class XML_TableColumns extends XML_PropertiesAbstract {

    private final ArrayList<XML_Property> columns = new ArrayList<>();

    private final XML_PropertyInteger width = new XML_PropertyInteger("width");
    private final XML_PropertyInteger columnIndex = new XML_PropertyInteger("columnIndex");

    @Override
    public boolean isEmpty() {
        return columns.isEmpty();
    }

    @Override
    public boolean isSaveEmptyObjects() {
        return true;
    }

    private XML_TableColumn currentColumn;

    @Override
    public XML_Property setProperty(String name, Object value) {
        //System.out.println("com.smartg.xml.XML_TableColumns.setProperty() {" + name + ", " + value + "} ");
        switch (name) {
            case "column":
                return currentColumn;
            case "width":
                width.setValue(value);
                final Integer w = width.getValue();
                columns.ensureCapacity(w);
                for (int i = 0; i < w; i++) {
                    XML_TableColumn t = new XML_TableColumn();
                    t.setEventManager(getEventManager());
                    t.setProperty("columnIndex", i);
                    columns.add(t);
                }
                currentColumn = (XML_TableColumn) columns.get(0);
                return width;
            case "columnIndex":
                return this.columnIndex.setValue(value);
            case "columnName":
                XML_TableColumn t = (XML_TableColumn) columns.get(columnIndex.getValue());
                currentColumn = t;
                return t.setProperty("columnName", value);
            default:
                throw new AssertionError(name);
        }
    }

    @Override
    public String getName() {
        return "columns";
    }

    @Override
    public Object getValue() {
        return Collections.unmodifiableList(columns);
    }

    @Override
    public Iterator<XML_Property> iterator() {
        return columns.iterator();
    }
}
