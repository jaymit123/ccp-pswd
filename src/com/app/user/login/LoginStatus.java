/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.login;

import java.awt.Image;

/**
 *
 * @author VJ
 */
public enum LoginStatus {

    INIT("First Image"), CONTINUE("Continue with Next Image!"), SUCCESS("Login Sucessfull!"), FAILURE("Failed to Login!"), ERROR("Sorry an error has occured!\n Please mail me at jaymit_123@gmail.com"), LOGOUT("Logout Successful");
    private String Message;
    private Image img = null;

    private LoginStatus(String msg) {
        Message = msg;
    }

    public void setMessage(String msg) {
        Message = msg;
    }

    public void setImage(Image im) {
        img = im;
    }

    public Image getImage() {
        return img;
    }

    public String getMessage() {
        return Message;
    }

}
