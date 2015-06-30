/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.security.validation;

/**
 *
 * @author VJ
 */
public enum ValidationStatus {
USERNAME_OK("Username OK")  ,  USERNAME_FMT_ERROR("Username must have 3 to 10 characters.") ,PASSWORD_OK("Password OK"), PASSWORD_FMT_ERROR("Password must have,\n1)6 to 16 characters\n2)atleast 1 special symbol\n3)atleast 1 uppercase & lowercase characters\n4)No Space")
;
    private String Message;
    
    private ValidationStatus(String msg){
        Message = msg;
    }
    
    public String getValidationMsg(){
        return Message;
    }
}
