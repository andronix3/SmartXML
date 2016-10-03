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
public class XML_PropertyString implements XML_Property {
    
    private final String name;
    private String value;
    
    public XML_PropertyString(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public XML_Property setValue(Object value) {
        if(value != null) {
            this.value = value.toString();
        }
        else {
            this.value = "";
        }
        return this;
    }
    
    @Override
    public String toString() {
        return getName() + ":" + getValue();
    }
}
