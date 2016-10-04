/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author andro
 */
class XML_TableRows extends XML_PropertiesAbstract {

    private final ArrayList<XML_Property> rows = new ArrayList<>();
    private final XML_PropertyInteger width = new XML_PropertyInteger("width");
    private final XML_PropertyInteger height = new XML_PropertyInteger("height");

    private final XML_PropertyInteger row = new XML_PropertyInteger("y");
    private final XML_PropertyInteger column = new XML_PropertyInteger("x");

    @Override
    public boolean isEmpty() {
        return rows.isEmpty();
    }

    @Override
    public boolean isSaveEmptyObjects() {
        return true;
    }

    private XML_TableRow tableRow;

    @Override
    public XML_Property setProperty(String name, Object value) {
        switch (name) {
            case "row":
                return tableRow;
            case "height":
                height.setValue(value);
                rows.ensureCapacity(height.getValue());
                for (int i = rows.size(); i < height.getValue(); i++) {
                    final XML_TableRow tblRow = new XML_TableRow();
                    tblRow.setEventManager(getEventManager());
                    rows.add(tblRow);
                }
                this.tableRow = (XML_TableRow) rows.get(0);
                fireEvent(name, value);
                return height;
            case "width":
                width.setValue(value);
                for (int i = 0; i < rows.size(); i++) {
                    XML_TableRow t = (XML_TableRow) rows.get(i);
//                    if (t == null) {
//                        t = new XML_TableRow();
//                        rows.set(i, t);
//                    }
                    t.setProperty(name, value);
                }
                fireEvent(name, value);
                return width;
            case "x":
                return column.setValue(value);
            case "y":
                return row.setValue(value);
            case "cellValue":
                XML_TableRow r = (XML_TableRow) rows.get(row.getValue());
                r.setProperty("x", column.getValue());
                r.setProperty("y", row.getValue());
                r.setProperty("cellValue", value);
                return r;
            default:
                throw new AssertionError();
        }
    }

    private void fireEvent(String name, Object value) {
        HashMap m = new HashMap();
        m.put(name, value);
        m.put("TYPE", name.toUpperCase());
        fireXmlEvent(new XML_Event(this, m));
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
