/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import edu.emory.mathcs.backport.java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author andro
 */
class XML_TableCell implements XML_Properties, Comparable<XML_TableCell> {

    private final XML_PropertyInteger row = new XML_PropertyInteger("x");
    private final XML_PropertyInteger column = new XML_PropertyInteger("y");
    private final XML_PropertySimple value = new XML_PropertySimple("cellValue");

    private final ArrayList<XML_Property> list = new ArrayList<>(Arrays.asList(new XML_Property[]{row, column, value}));

    public XML_TableCell(Object value) {
        this.value.setValue(value);
    }

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
                return column.setValue(value);
            case "x":
                return row.setValue(value);
            case "cellValue":
                return this.value.setValue(value);
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
