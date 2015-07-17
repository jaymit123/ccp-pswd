/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.register;
import com.app.beans.AbstractController;
import com.app.beans.Viewable;
import java.beans.PropertyChangeEvent;

/**
 *
 * @author VJ
 */
public class RegisterController extends AbstractController {

    private RegisterModel regModel;
    private RegisterView regView;
    protected Viewable mainView;

    public void setRegisterModel(RegisterModel regmodel) {
        regModel = regmodel;
        regModel.addPropertyChangeListener(this);
    }

    public void setMainView(Viewable mv) {
        mainView = mv;
    }

    public void setRegisterView(RegisterView regview) {
        regView = regview;
    }

    public void registerUser(String Username, String P1Password) {
        regModel.createAccount(Username, P1Password);
    }

    public void addUserEntry(String Image, int Grid) {
        regModel.addEntry(Image, Grid);
    }

    public void completeRegisteration() {
        regModel.finalizeRegistration();
    }

    public void requestImage(String ImgName) {
        regModel.getImage(ImgName);
    }

    public void restart() {
        regModel.reset("FULL_RESET");
    }

    public void resetPhase2() {
        regModel.reset("P2_RESET");
    }

    public void goBack() {
        regModel.reset("MAINMENU");

    }

    public void propertyChange(PropertyChangeEvent evt) {
        //System.out.println(evt.getPropertyName() + " : " + (evt.getNewValue()));
        switch (evt.getPropertyName()) {
            case "DisplayImage":
            case "RegisterStatus":
            case "ValidationStatus":
            case "ExceptionStatus":
                regView.modelPropertyChange(evt);
                break;

            case "GoToMainMenu":
                mainView.modelPropertyChange(evt);
                break;
        }

    }
}
