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
public enum ValidationStatus {

    USERNAME_OK("Username OK"), USERNAME_FMT_ERROR("Username must have 3 to 10 characters."), PASSWORD_OK("Password OK"), PASSWORD_FMT_ERROR("Passowrd must have 5 to 10 characters & atleast 1 symbol or number."), BOTH_OK("Username & Password OK"), USERNAME_EXIST("Username provided already exist, please choose another username"), NO_ACCOUNT("Sorry, Account doesn't exist!\nPlease reset the registration process.");

    private String Message;

    private ValidationStatus(String msg) {
        Message = msg;
    }

    public String getValidationMsg() {
        return Message;
    }
}
