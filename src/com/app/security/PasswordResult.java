/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.security;

import java.awt.image.BufferedImage;

/**
 *
 * @author VJ
 */
public enum PasswordResult {

    CONTINUE("Continue with Next Image!"), SUCCESS("Login Sucessfull!"), FAILURE("Failed to Login!");
    private final String Message;
    private BufferedImage Image = null;

    private PasswordResult(String msg) {
        Message = msg;
    }

    public void setImage(BufferedImage img) {
        Image = img;
    }

    public BufferedImage getContinueImage() {
        return Image;
    }

}
