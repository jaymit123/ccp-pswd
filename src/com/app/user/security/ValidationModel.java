/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.security;

/**
 *
 * @author VJ
 */
public class ValidationModel {

    private final static String Username_Regex = "[\\S]{3,10}";
    private final static String P1_Password_Regex = "((?=\\S*\\w+)(?=\\S*[+-.,!@#$%^&*();\\\\/|<>\"'\\d]+)(\\S{5,10}))";
    private final static String P2_Password_Regex = "(\\|[^/?<>\\\\:*|]+\\.[a-zA-Z0-9]{3,5}\\&[\\d]{1,3}){3,5}";

    public static ValidationStatus validateUsername(String username) {
        if (username.matches(Username_Regex)) {
            return ValidationStatus.USERNAME_OK;
        } else {
            return ValidationStatus.USERNAME_FMT_ERROR;
        }
    }

    public static ValidationStatus validateP1Password(String pswd) {
        if (pswd.matches(P1_Password_Regex)) {
            return ValidationStatus.PASSWORD_OK;
        } else {
            return ValidationStatus.PASSWORD_FMT_ERROR;
        }
    }

    public static boolean validateP2Passowrd(String pswd) {
        return pswd.matches(P2_Password_Regex);
    }

    public static ValidationStatus validateUser(String Username, String Password) {

        if (Username.equals(Password)) {
            return ValidationStatus.BOTH_SAME_ERROR;
        } else if (validateUsername(Username) == ValidationStatus.USERNAME_FMT_ERROR) {
            return ValidationStatus.USERNAME_FMT_ERROR;
        } else if (validateP1Password(Password) == ValidationStatus.PASSWORD_FMT_ERROR) {
            return ValidationStatus.PASSWORD_FMT_ERROR;
        }
        return ValidationStatus.BOTH_OK;
    }

}
