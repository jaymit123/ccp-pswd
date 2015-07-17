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

    ValidationStatus("ValidationStatus"), LoginStatus("LoginStatus"), RegisterStatus("RegisterStatus"), ExceptionStatus("Exception"), NoProperty("NoProperty"), DisplayImage("Display Image"),
    GoToMainMenu("Go To MainMenu");
    private final String Property;

    private ProcessStatus(String property) {
        Property = property;
    }

    public String getProperty() {
        return Property;
    }

}
