/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.status;

/**
 *
 * @author VJ
 */
public enum ProcessStatus {

    ValidationStatus("ValidationStatus"), RegisterStatus("RegisterStatus"), ExceptionStatus("Exception"), NoProperty("NoProperty"), DisplayImage("Display Image");

    private String Property;

    private ProcessStatus(String property) {
        Property = property;
    }

    public String getProperty() {
        return Property;
    }



}
