/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

/**
 *
 * @author andro
 */
public class XML_PropertyInteger implements XML_Property {

    private final String name;
    private Integer value;

    public XML_PropertyInteger(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public XML_Property setValue(Object value) {
        this.value = new Integer(String.valueOf(value));
        return this;
    }

    @Override
    public String toString() {
        return getName() + ":" + getValue();
    }

}
