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
public enum SecurityExReason {

    FIN_REG_ERROR("Sorry an error occured while registering.Please try again."), FIN_REG_USER_EXIST("Sorry, username has been taken.Please use different name and try again"), INIT_TIES("Sorry an error occured while registering.Please try again."), LOGIN_ERROR("Sorry an error occured while registering.Please try again."), PASS_REGEX_CHECK_ERROR("Sorry, This account cannot be accessed.contact me at jaymitd123@gmail.com"), ACC_IMG_NOT_FOUND("Sorry,This account cannot be accessed.");
    private String Reason;

    private SecurityExReason(String str) {
        Reason = str;
    }

    public String getMessage() {
        return Reason;
    }

}
