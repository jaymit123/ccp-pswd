/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.login;

/**
 *
 * @author VJ
 */
public enum LoginStatus {

    CONTINUE("Continue with Next Image!"), SUCCESS("Login Sucessfull!"), FAILURE("Failed to Login!"),ERROR("Sorry an error has occured!\n Please mail me at jaymit_123@gmail.com");
    private String Message;

    private LoginStatus(String msg) {
        Message = msg;
    }

    public void setMessage(String msg) {
        Message = msg;
    }

    public String getMessage() {
        return Message;
    }

}
