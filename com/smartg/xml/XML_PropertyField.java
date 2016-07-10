/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author andro
 */
public class XML_PropertyField implements XML_Property {

    private final Object obj;
    private final Field field;
    private final String name;

    public XML_PropertyField(Object obj, String name) throws NoSuchFieldException {
        Objects.requireNonNull(obj);
        this.obj = obj;
        this.name = name;
        field = obj.getClass().getDeclaredField(name);
        field.setAccessible(true);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getValue() {
        try {
            return field.get(obj).toString();
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public XML_Property setValue(Object value) {
        try {
            Class<?> type = field.getType();
            if(type == int.class || type == Integer.class) {
                Integer valueOf = Integer.valueOf(value.toString());
                field.set(obj, valueOf);
            }
            else if(type == double.class || type == Double.class) {
                field.set(obj, Double.valueOf(value.toString()));
            }
            else if(type == boolean.class || type == Boolean.class) {
                field.set(obj, Boolean.valueOf(value.toString()));
            }
            else {
                field.set(obj, value);
            }
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

}
