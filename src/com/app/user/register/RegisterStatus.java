/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.register;

/**
 *
 * @author VJ
 */
public enum RegisterStatus {

    INIT("Registration Started"), ADDED("Sucessfully updated password."), ALERT("Contine Registration ?\n Note : You can select 2 more images. "), REGISTER_SUCCESS("Registeration Completed Sucessfully!"), REGISTER_FAILED("Sorry an error has occured!\n Please mail me at jaymit_123@gmail.com"), RESET_P2("Phase 2 has been reset successfully!"), RESET_FULL("Full Reset Success!"),CLOSE("Close ReigsterView");

    private String message;

    private RegisterStatus(String msg) {
        message = msg;
    }

    public String getMessage() {
        return message;
    }
}
