/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author andro
 */
public class XML_PropertyMethod implements XML_Property {

    private final String name;
    private final Object obj;
    private final Method getter;
    private Method setter;

    public XML_PropertyMethod(String name, Object obj, String getter, String setter) throws NoSuchMethodException {
        this.name = name;
        this.obj = obj;
        this.getter = obj.getClass().getMethod(getter, new Class[0]);
        if(setter != null) {
            this.setter = obj.getClass().getMethod(setter, String.class);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getValue() {
        try {
            return (String) getter.invoke(obj, (Object[]) null);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(XML_PropertyMethod.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public XML_Property setValue(Object value) {
        if(setter == null) {
            throw new UnsupportedOperationException();
        }
        try {
            setter.invoke(obj, value);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(XML_PropertyMethod.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

}
