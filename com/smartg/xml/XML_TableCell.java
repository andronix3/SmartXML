/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author andro
 */
class XML_TableCell extends XML_PropertiesAbstract implements Comparable<XML_TableCell> {

    private final XML_PropertyInteger row = new XML_PropertyInteger("y");
    private final XML_PropertyInteger column = new XML_PropertyInteger("x");
    private final XML_PropertySimple value = new XML_PropertySimple("cellValue");

    private final ArrayList<XML_Property> list = new ArrayList<>(Arrays.asList(new XML_Property[]{row, column, value}));

    @Override
    public String toString() {
        return row + " " + column + " " + value;
    }

    @Override
    public String getName() {
        return "cell";
    }

    @Override
    public Object getValue() {
        return value.getValue();
    }

    @Override
    public XML_Property setValue(Object value) {
        this.value.setValue(value);
        return this.value;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isSaveEmptyObjects() {
        return false;
    }

    @Override
    public XML_Property setProperty(String name, Object value) {
        switch (name) {
            case "y":
                return row.setValue(value);
            case "x":
                return column.setValue(value);
            case "cellValue":
                this.value.setValue(value);

                Map m = new HashMap();
                m.put("x", column.getValue());
                m.put("y", row.getValue());
                m.put(name, value);
                m.put("TYPE", "CELL");
                fireXmlEvent(new XML_Event(this, m));

                return this.value;
            default:
                throw new AssertionError();
        }
    }

    @Override
    public Iterator<XML_Property> iterator() {
        return list.iterator();
    }

    @Override
    public int compareTo(XML_TableCell o) {
        int cmp = o.row.getValue().compareTo(row.getValue());
        if (cmp != 0) {
            return cmp;
        }
        return o.column.getValue().compareTo(column.getValue());
    }
}
