/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartg.xml;

import com.smartg.java.util.AddToList;
import com.smartg.java.util.EventListenerListIterator;
import javax.swing.event.EventListenerList;

/**
 *
 * @author andro
 */
public class XML_EventManager {

    private final EventListenerList listenerList = new EventListenerList();

    public XML_EventManager() {
    }

    public void addXmlEventListener(XML_Listener l) {
        new AddToList(listenerList).add(XML_Listener.class, l);
    }

    public void removeXmlEventListenr(XML_Listener l) {
        listenerList.remove(XML_Listener.class, l);
    }

    public void fireXmlEvent(XML_Event e) {
        EventListenerListIterator<XML_Listener> iterator = new EventListenerListIterator<>(XML_Listener.class, listenerList);
        while (iterator.hasNext()) {
            iterator.next().propertySet(e);
        }
    }

}
