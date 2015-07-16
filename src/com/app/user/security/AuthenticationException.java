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
public class AuthenticationException extends Exception {

    private AuthenticationExReason reason;

    public AuthenticationException(AuthenticationExReason rsn, String string) {
        super(string);
        reason = rsn;
    }

    public AuthenticationException(AuthenticationExReason rsn, String string, Throwable thrwbl) {
        super(string, thrwbl);
        reason = rsn;

    }

    public AuthenticationExReason getErroReason() {
        return reason;
    }

}
