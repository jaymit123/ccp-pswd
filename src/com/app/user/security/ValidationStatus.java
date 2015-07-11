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

    USERNAME_OK("Username OK"), USERNAME_FMT_ERROR("Username must have 3 to 10 characters."), PASSWORD_OK("Password OK"), PASSWORD_FMT_ERROR("Password must have 5 to 10 characters & atleast 1 symbol or number."), BOTH_OK("Phase1 Complete. Phase2 will begin now."), USERNAME_EXIST("Username provided already exist, please choose another username"), NO_ACCOUNT("Sorry, Account doesn't exist!\nRegistration will restart now."), BOTH_ERROR("Login Failed, Please check username and password."),ACC_DOESNT_EXIST("This account doesn't exist. Please Register First.");

    private String message;

    private ValidationStatus(String msg) {
        message = msg;
    }

    public String getValidationMsg() {
        return message;
    }
}
