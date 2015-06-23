/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.security;

/**
 *
 * @author VJ
 */
public enum EntryStatus {

    ADDED("Sucessfully updated password."), ALERT("Contine Registration ?\n Yes : Continue registration \n No : Complete Registration\n Note : You can select 2 more images. "), COMPLETED("Registeration Completed Sucessfully!"), RESET_P1("Phase2 Reset Successfull!");
    
    private String message;
    private EntryStatus(String msg){
      message = msg;  
    }
    
    public String getMessage(){
        return message;
    }
}
