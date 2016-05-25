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
public class XML_PropertiesObject extends XML_Properties {

    private final Object object;

    public XML_PropertiesObject(String name, Object obj) {
        super(name);
        this.object = obj;
    }

    public XML_PropertiesObject(String name, Object obj, String... fieldList) throws NoSuchFieldException {
        super(name);
        this.object = obj;
        for (String s : fieldList) {
            addFieldProperty(s);
        }
    }

    public final void addFieldProperty(String name) throws NoSuchFieldException {
        XML_PropertyField prop = new XML_PropertyField(object, name);
        registerProperty(prop);
    }

    /**
     * add method property with default getter and setter names (constructed by
     * preceding name with 'get' and 'set')
     *
     * @param name property name
     * @throws NoSuchMethodException
     */
    public final void addMethodProperty(String name) throws NoSuchMethodException {
        char firstChar = name.charAt(0);
        String uName = name.replace(firstChar, Character.toUpperCase(firstChar));
        addMethodProperty(name, "get" + uName, "set" + uName);
    }

    public final void addMethodProperty(String name, String getter, String setter) throws NoSuchMethodException {
        XML_PropertyMethod prop = new XML_PropertyMethod(name, object, getter, setter);
        registerProperty(prop);
    }

    @Override
    public Object getValue() {
        return object;
    }

    @Override
    public Object getObject() {
        return object;
    }

}
