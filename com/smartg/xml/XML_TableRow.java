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
class XML_TableRow extends XML_PropertiesAbstract {

    private final ArrayList<XML_Property> cells = new ArrayList<>();

    private final XML_PropertyInteger row = new XML_PropertyInteger("y");
    private final XML_PropertyInteger column = new XML_PropertyInteger("x");

    private final XML_PropertyInteger width = new XML_PropertyInteger("width");

    private XML_TableCell tableCell;

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
            case "cell":
                return tableCell;
            case "width":
                width.setValue(value);
                cells.ensureCapacity(width.getValue());
                for (int i = cells.size(); i < width.getValue(); i++) {
                    XML_TableCell cell = new XML_TableCell();
                    cell.setEventManager(getEventManager());
                    cells.add(cell);
                }
                this.tableCell = (XML_TableCell) cells.get(0);
                return width;
            case "x":
                return column.setValue(value);
            case "y":
                return row.setValue(value);
            case "cellValue":
                XML_TableCell cell = (XML_TableCell) cells.get(column.getValue());
                cell.setProperty("x", column.getValue());
                cell.setProperty("y", row.getValue());
                cell.setValue(value);
                this.tableCell = cell;
                return cell;
            default:
                throw new AssertionError(name);
        }
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
