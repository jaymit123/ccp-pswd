/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.login;

import java.awt.image.BufferedImage;

/**
 *
 * @author VJ
 */
public enum LoginStatus {

    INIT("First BufferedImage"), CONTINUE("Continue with Next BufferedImage!"), SUCCESS("Login Sucessfull!"), FAILURE("Failed to Login!"), ERROR("Sorry an error has occured!\n Please mail me at jaymit_123@gmail.com"), LOGOUT("Logout Successful");
    private String message;
    private BufferedImage image = null;

    private LoginStatus(String msg) {
        message = msg;
    }

    public void setMessage(String msg) {
        message = msg;
    }

    public void setImage(BufferedImage im) {
        image = im;
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getMessage() {
        return message;
    }

}
