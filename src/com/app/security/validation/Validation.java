/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.security.validation;

/**
 *
 * @author VJ
 */
public class Validation {

    private final String Username_Regex  = "[\\S]{3,10}";
    private final String Password_Regex = "(?=\\S*[a-z])(?=\\S*[A-Z])(?=\\S*[0-9])(?=\\S*[+-.,!@#$%^&*(){}\\[\\];\\\\\\/|<>\"'])(\\S{6,16})";

    public ValidationStatus validateUsername(String username) {
        if (username.matches(Username_Regex)) {
            return ValidationStatus.USERNAME_OK;
        } else {
            return ValidationStatus.USERNAME_FMT_ERROR;
        }
    }

    public ValidationStatus validatePassword(String pswd) {
        if (pswd.matches(Password_Regex)) {
            return ValidationStatus.PASSWORD_OK;
        } else {
            return ValidationStatus.PASSWORD_FMT_ERROR;
        }
    }

}
