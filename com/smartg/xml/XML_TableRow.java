/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import edu.emory.mathcs.backport.java.util.Collections;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author andro
 */
class XML_TableRow implements XML_Properties {

    private final ArrayList<XML_Property> cells = new ArrayList<>();

    private final XML_PropertyInteger row = new XML_PropertyInteger("y");
    private final XML_PropertyInteger column = new XML_PropertyInteger("x");

    private final XML_PropertyInteger width = new XML_PropertyInteger("width");

    @Override
    public boolean isEmpty() {
        return cells.isEmpty();
    }

    @Override
    public boolean isSaveEmptyObjects() {
        return true;
    }

    @Override
    public XML_Property setProperty(String name, Object value) {
        switch (name) {
            case "width":
                width.setValue(Integer.valueOf(String.valueOf(value)));
                cells.ensureCapacity(width.getValue());
                for (int i = cells.size(); i < width.getValue(); i++) {
                    cells.add(null);
                }
                break;
            case "x":
                return column.setValue(value);
            case "y":
                return row.setValue(value);
            case "cellValue":
                XML_TableCell cell = new XML_TableCell(value);
                cell.setProperty("x", column.getValue());
                cell.setProperty("y", row.getValue());
                cells.set(column.getValue(), cell);
                System.out.println("com.smartg.xml.XML_TableRow.setProperty() " + cell);
                break;
            default:
                throw new AssertionError();
        }
        return this;
    }

    @Override
    public String getName() {
        return "row";
    }

    @Override
    public Object getValue() {
        return Collections.unmodifiableList(cells);
    }

    @Override
    public Iterator<XML_Property> iterator() {
        return cells.iterator();
    }
}
