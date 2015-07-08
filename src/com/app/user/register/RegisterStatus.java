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

  ADDED("Sucessfully updated password."), ALERT("Complete Registration ?\n Note : You can select 2 more images. "), REGISTER_SUCCESS("Registeration Completed Sucessfully!"), REGISTER_FAILED("Sorry an error has occured!\n Please mail me at jaymit_123@gmail.com"), P2_RESET("Phase 2 has been reset successfully!"), FULL_RESET("Registration has been sucessfully reset");

    private String message;

    private RegisterStatus(String msg) {
        message = msg;
    }

    public String getMessage() {
        return message;
    }
}
