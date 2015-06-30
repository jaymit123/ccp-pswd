/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.exceptions;

/**
 *
 * @author VJ
 */
public class ImageAccessException extends Exception{

    public ImageAccessException(String string) {
        super(string);
    }

    public ImageAccessException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }
    
    
}
