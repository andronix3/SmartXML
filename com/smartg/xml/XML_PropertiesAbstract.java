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
public abstract class XML_PropertiesAbstract implements XML_Properties {

    private XML_EventManager eventManager = new XML_EventManager();

    public XML_PropertiesAbstract() {
    }

    protected void fireXmlEvent(XML_Event e) {
        eventManager.fireXmlEvent(e);
    }

    @Override
    public final XML_EventManager getEventManager() {
        return eventManager;
    }

    @Override
    public final void setEventManager(XML_EventManager m) {
        this.eventManager = m;
    }
}
