/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.beans;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author VJ
 */
public abstract class AbstractModel {
    protected PropertyChangeSupport checkProperty;
    
    
public AbstractModel(){
    checkProperty = new PropertyChangeSupport(this);       
}

//adds Controllers that will communicate with Model
public void addPropertyChangeListener(PropertyChangeListener listener){      
    checkProperty.addPropertyChangeListener(listener);
} 

public void removePropertyChangeListener(PropertyChangeListener listener){
    checkProperty.removePropertyChangeListener(listener);
}

 //send a PropertyChangeEvent to the Controller , To inform the  View about changes.
 protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) { 
        checkProperty.firePropertyChange(propertyName, oldValue, newValue);
    }
    
}
