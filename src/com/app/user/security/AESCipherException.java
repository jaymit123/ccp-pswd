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
public class AESCipherException extends Exception {

    AESCipherException( String string,Throwable ex) {
        super(string,ex);
    }

}
