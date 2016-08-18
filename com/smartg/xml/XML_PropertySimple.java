/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import java.util.Objects;

/**
 *
 * @author andro
 */
public class XML_PropertySimple implements XML_Property {
    
    private final String name;
    private Object value;

    public XML_PropertySimple(String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public XML_Property setValue(Object value) {
        this.value = value;
        return this;
    }
    
}
