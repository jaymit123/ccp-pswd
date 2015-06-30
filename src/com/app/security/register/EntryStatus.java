/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.security.register;

/**
 *
 * @author VJ
 */
public enum EntryStatus {

    ADDED("Sucessfully updated password."), ALERT("Contine Registration ?\n Note : You can select 2 more images. "), COMPLETED("Registeration Completed Sucessfully!"),ERROR("Sorry an error has occured!\n Please mail me at jaymit_123@gmail.com") ,RESET_P1("Phase2 Reset Successfull!");
    
    private String message;
    private EntryStatus(String msg){
      message = msg;  
    }
    
    public String getMessage(){
        return message;
    }
}
