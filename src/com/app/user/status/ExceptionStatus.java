/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.status;

/**
 *
 * @author VJ
 */
public enum ExceptionStatus {

    USER_EXIST(""), FATAL_ERROR("Oh no! Something went wrong!\nPlease restart the software or email me at jaymit_123@hotmail.com!");
    private String Message;

    private ExceptionStatus(String msg) {
        Message = msg;
    }

    public void setMessage(String msg) {
        Message = msg;
    }

    public String getMessage() {
        return Message;
    }

}
