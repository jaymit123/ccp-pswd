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
public enum AuthenticationExReason {

    FIN_REG_ERROR("Sorry an error occured while registering.Please try again."), FIN_REG_USER_EXIST("Sorry, username has been taken.Please use different name and try again"), INIT_DBCON_ERROR("Sorry an error occured while registering.Please try again."), LOGIN_ERROR("Sorry an error occured while registering.Please try again."), PASS_REGEX_CHECK_ERROR("Sorry, This account cannot be accessed.contact me at jaymitd123@gmail.com"), ACC_IMG_NOT_FOUND("Sorry,This account cannot be accessed."),ENCRYPT_ERROR(""),DECRYPT_ERROR("");
    private String reason;

    private AuthenticationExReason(String str) {
        reason = str;
    }

    public String getMessage() {
        return reason;
    }

}
