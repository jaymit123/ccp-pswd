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
public class SecurityException extends Exception {

    private SecurityExReason Reason;

    public SecurityException(SecurityExReason reason, String string) {
        super(string);
        Reason = reason;
    }

    public SecurityException(SecurityExReason reason, String string, Throwable thrwbl) {
        super(string, thrwbl);
        Reason = reason;

    }

    public SecurityExReason getErroReason() {
        return Reason;
    }

}
