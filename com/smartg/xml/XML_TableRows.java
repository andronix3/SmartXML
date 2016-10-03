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
class XML_TableRows implements XML_Properties {

    private final ArrayList<XML_Property> rows = new ArrayList<>();
    private final XML_PropertyInteger width = new XML_PropertyInteger("width");
    private final XML_PropertyInteger height = new XML_PropertyInteger("height");

    private XML_PropertyInteger row = new XML_PropertyInteger("y");
    private XML_PropertyInteger column = new XML_PropertyInteger("x");

    @Override
    public boolean isEmpty() {
        return rows.isEmpty();
    }

    @Override
    public boolean isSaveEmptyObjects() {
        return true;
    }

    @Override
    public XML_Property setProperty(String name, Object value) {
        System.out.println("com.smartg.xml.XML_TableRows.setProperty() " + name + " " + value);
        switch (name) {
            case "height":
                height.setValue(value);
                rows.ensureCapacity(height.getValue());
                for (int i = rows.size(); i < height.getValue(); i++) {
                    rows.add(new XML_TableRow());
                }
                break;
            case "width":
                for (int i = 0; i < rows.size(); i++) {
                    width.setValue(value);
                    XML_TableRow t = (XML_TableRow) rows.get(i);
                    if (t == null) {
                        t = new XML_TableRow();
                        rows.set(i, t);
                    }
                    t.setProperty(name, value);
                }
                break;
            case "x":
                return column.setValue(value);
            case "y":
                return row.setValue(value);
            case "cellValue":
                XML_TableRow r = (XML_TableRow) rows.get(row.getValue());
                r.setProperty("x", column.getValue());
                r.setProperty("y", row.getValue());
                r.setProperty("cellValue", value);
                break;
            default:
                throw new AssertionError();
        }
        return null;
    }

    @Override
    public String getName() {
        return "rows";
    }

    @Override
    public Object getValue() {
        return Collections.unmodifiableList(rows);
    }

    @Override
    public Iterator<XML_Property> iterator() {
        return rows.iterator();
    }

    public XML_PropertyInteger getWidth() {
        return width;
    }

    public XML_PropertyInteger getHeight() {
        return height;
    }

}
