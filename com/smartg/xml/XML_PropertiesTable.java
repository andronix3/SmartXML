/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author andro
 */
public class XML_PropertiesTable extends XML_PropertiesAbstract {

    private final TableModel model;

    private final String name;

    private final XML_TableColumns tableColumns = new XML_TableColumns();
    private final XML_TableRows tableRows = new XML_TableRows();

    public XML_PropertiesTable(String name) {
        this(new DefaultTableModel(), name);
    }

    public XML_PropertiesTable(TableModel model, String name) {
        this.name = name;
        this.model = model;


        //event forwarding
        tableColumns.setEventManager(getEventManager());
        tableRows.setEventManager(getEventManager());
       
        setModel(model);
    }

    private void setModel(TableModel model) {
        int columnCount = model.getColumnCount();
        int rowCount = model.getRowCount();
        if(columnCount == 0) {
            return;
        }

        //important set height first
        setProperty("height", rowCount);
        setProperty("width", columnCount);

        for (int i = 0; i < columnCount; i++) {
            setProperty("columnIndex", i);
            setProperty("columnName", model.getColumnName(i));
        }
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                //set y first
                setProperty("y", r);
                setProperty("x", c);
                setProperty("cellValue", model.getValueAt(r, c));
            }
        }
    }

    @Override
    public XML_Property setProperty(String name, Object value) {
        switch (name) {
            case "columns":
                return tableColumns;
            case "rows":
                return tableRows;
            case "height":
                return tableRows.setProperty(name, value);
            case "width":
                tableColumns.setProperty(name, value);
                return tableRows.setProperty(name, value);
            case "columnName":
            case "columnIndex":
                return tableColumns.setProperty(name, value);
            case "x":
            case "y":
            case "cellValue":
                return tableRows.setProperty(name, value);
            default:
                throw new AssertionError(name);
        }
    }

    @Override
    public Iterator<XML_Property> iterator() {
        ArrayList<XML_Property> list = new ArrayList<>();

        list.add(tableRows.getHeight());
        list.add(tableRows.getWidth());
        list.add(tableColumns);
        list.add(tableRows);

        return list.iterator();
    }

    @Override
    public boolean isEmpty() {
        return tableColumns.isEmpty();
    }

    @Override
    public Object getValue() {
        return model;
    }

    @Override
    public boolean isSaveEmptyObjects() {
        return true;
    }

    @Override
    public String getName() {
        return name;
    }
}
