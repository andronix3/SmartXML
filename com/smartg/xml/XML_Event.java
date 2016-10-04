/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import java.util.Collections;
import java.util.EventObject;
import java.util.Map;

/**
 *
 * @author andro
 */
public class XML_Event extends EventObject {
    
    private final Map<Object, Object> map;
    
    public XML_Event(Object source, Map map) {
        super(source);
        this.map = map;
    }

    public Map<Object, Object> getMap() {
        return Collections.unmodifiableMap(map);
    }
}
