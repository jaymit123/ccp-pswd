/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.dao;

/**
 *
 * @author VJ
 */
public class DAOException extends Exception {

    private DAOExReason reason;

    public DAOException(String string) {
        super(string);
    }

    public DAOException(DAOExReason der, String string, Throwable thrwbl) {
        super(string, thrwbl);
        reason = der;
    }

    public DAOExReason getErrorReason() {
        return reason;
    }

}
