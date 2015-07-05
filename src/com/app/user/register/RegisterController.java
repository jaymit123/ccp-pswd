/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.register;

import com.app.beans.AbstractModel;
import com.app.beans.AbstractController;
import com.app.beans.Viewable;
import java.beans.PropertyChangeEvent;

/**
 *
 * @author VJ
 */
public class RegisterController extends AbstractController {
  private RegisterModel RegModel;
  
  
    public RegisterController(Viewable mainMenu) {
        super(mainMenu); 
    }
    
    public void setRegisterModel(RegisterModel regmodel){
        RegModel = regmodel;
        RegModel.addPropertyChangeListener(this);
    }

    public void registerUser(String Username, String P1Password) {
    RegModel.createAccount(Username, P1Password);
    }


    public void addUserEntry(String Image, int Grid) {
      RegModel.addEntry(Image, Grid);
    }
    
    
    public void completeRegisteration(){
        RegModel.finalizeRegistration();
    }
    
    public void requestImage(String ImgName){
        RegModel.getImage(ImgName);
    }
    
    
    public void resetProcess(String Type){
        RegModel.reset(Type);
    }
    
    public void Close(){
 
    }

    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt.getPropertyName() + " : " + (evt.getNewValue()));

    }
}
