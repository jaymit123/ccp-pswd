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

    public void goBack() {
        RegModel.reset("MAINMENU");

    }

    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt.getPropertyName() + " : " + (evt.getNewValue()));
        switch (evt.getPropertyName()) {
            case "DisplayImage":
            case "RegisterStatus":
            case "ValidationStatus":
            case "ExceptionStatus":
                RegView.modelPropertyChange(evt);
                break;

            case "GoToMainMenu":
                MainView.modelPropertyChange(evt);
                break;
        }

    }
}
