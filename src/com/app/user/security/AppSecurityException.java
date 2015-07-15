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
public class AppSecurityException extends Exception {

    private AppSecurityExReason reason;

    public AppSecurityException(AppSecurityExReason rsn, String string) {
        super(string);
        reason = rsn;
    }

    public AppSecurityException(AppSecurityExReason rsn, String string, Throwable thrwbl) {
        super(string, thrwbl);
        reason = rsn;

    }

    public AppSecurityExReason getErroReason() {
        return reason;
    }

}
