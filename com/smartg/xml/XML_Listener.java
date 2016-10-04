/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import java.util.EventListener;

/**
 *
 * @author andro
 */
public interface XML_Listener extends EventListener {
    
    void propertySet(XML_Event e);
    
}
