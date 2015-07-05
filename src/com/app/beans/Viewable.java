/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.beans;

import java.beans.PropertyChangeEvent;

/**
 *
 * @author VJ
 */
public interface Viewable {
    public void modelPropertyChange(final PropertyChangeEvent pce);
    
}
