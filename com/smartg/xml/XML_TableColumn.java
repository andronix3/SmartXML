/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import edu.emory.mathcs.backport.java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author andro
 */
public class XML_TableColumn implements XML_Properties {

    private final XML_PropertyInteger columnIndex = new XML_PropertyInteger("columnIndex");
    private final XML_PropertyString columnName = new XML_PropertyString("columnName");

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isSaveEmptyObjects() {
        return true;
    }

    @Override
    public XML_Property setProperty(String name, Object value) {
        switch (name) {
            case "columnIndex":
                return columnIndex.setValue(value);
            case "columnName":
                return columnName.setValue(value);
            default:
                throw new AssertionError();
        }
    }

    @Override
    public String getName() {
        return "column";
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public Iterator<XML_Property> iterator() {
        return Arrays.asList(new XML_Property[]{columnIndex, columnName}).iterator();
    }
}
