/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.register;

import com.app.beans.AbstractModel;
import com.app.beans.AbstractController;
import com.app.beans.Viewable;
import com.app.user.register.view.RegisterView;
import com.app.user.status.ProcessStatus;
import java.beans.PropertyChangeEvent;

/**
 *
 * @author VJ
 */
public class RegisterController extends AbstractController {

    private RegisterModel RegModel;
    private RegisterView RegView;
    protected Viewable MainView;

    public void setRegisterModel(RegisterModel regmodel) {
        RegModel = regmodel;
        RegModel.addPropertyChangeListener(this);
    }

    public void setMainView(Viewable mv) {
        MainView = mv;
    }

    public void setRegisterView(RegisterView regview) {
        RegView = regview;
    }

    public void registerUser(String Username, String P1Password) {
        RegModel.createAccount(Username, P1Password);
    }

    public void addUserEntry(String Image, int Grid) {
        RegModel.addEntry(Image, Grid);
    }

    public void completeRegisteration() {
        RegModel.finalizeRegistration();
    }

    public void requestImage(String ImgName) {
        RegModel.getImage(ImgName);
    }

    public void restart() {
        RegModel.reset("FULL_RESET");
    }

    public void resetPhase2() {
        RegModel.reset("P2_RESET");
    }

    public void close() {
       RegModel.reset("CLOSE");

    }

    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt.getPropertyName() + " : " + (evt.getNewValue()));
        switch (evt.getPropertyName()) {
            case "DisplayImage":
                RegView.modelPropertyChange(evt);
                break;
                
            case "RegisterStatus":
                if (evt.getNewValue().equals(RegisterStatus.CLOSE)) {
                    MainView.modelPropertyChange(evt);
                } else {
                    RegView.modelPropertyChange(evt);
                }
                break;

        }

    }
}
