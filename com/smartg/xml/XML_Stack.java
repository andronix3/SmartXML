/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import java.util.HashMap;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andro
 */
public class XML_Stack {

    private final HashMap<String, Object> map = new HashMap<>();

    private final Stack<XML_Property> stack = new Stack<>();
    private XML_Property lastObject;

    public void register(String name, Class cls) {
        map.put(name, cls);
    }

    public void register(String name, XML_Support t) {
        map.put(name, t);
    }

    public XML_Property getLastObject() {
        return lastObject;
    }

    public void start(String name, String value) {
        //System.out.println("com.smartg.xml.XML_Stack.start() " + name);
        if (stack.isEmpty()) {
            Object t = map.get(name);
            if (t instanceof Class) {
                Class<?> cls = (Class<?>) t;
                try {
                    Object props = cls.newInstance();
                    XML_Property p = (XML_Property) props;
                    stack.add(p);
                } catch (InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(XML_Stack.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(t instanceof XML_Support) {
                XML_Support xmls = (XML_Support) t;
                stack.add(xmls.create(name));
            }
        } else {
            XML_Property peek = stack.peek();
            //System.out.println(peek.getName() + " " + peek.getClass().getName());
            if (peek instanceof XML_Properties) {
                XML_Properties props = (XML_Properties) peek;
                XML_Property property = props.setProperty(name, value);
//                try {
//                    property = Objects.requireNonNull(property);
//                }
//                catch(NullPointerException ex) {
//                    props.setProperty(name, value);
//                    ex.printStackTrace();
//                }
                stack.add(property);
            } else {
                System.err.println("Using XML_PropertySimple");
                XML_PropertySimple prop = new XML_PropertySimple(name);
                prop.setValue(value);
                stack.add(prop);
            }
        }
        //System.out.println("start::" + name);
        //System.out.println("value::" + value);
    }

    public void end(String name) {
        //System.out.println("com.smartg.xml.XML_Stack.end() " + name);
        if (!name.equals(stack.peek().getName())) {
            throw new RuntimeException(name + " != " + stack.peek().getName());
        }
        lastObject = stack.pop();
        //System.out.println("end::" + name);
    }

}
