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

    private SecurityExReason Reason;

    public AppSecurityException(SecurityExReason reason, String string) {
        super(string);
        Reason = reason;
    }

    public AppSecurityException(SecurityExReason reason, String string, Throwable thrwbl) {
        super(string, thrwbl);
        Reason = reason;

    }

    public SecurityExReason getErroReason() {
        return Reason;
    }

}
