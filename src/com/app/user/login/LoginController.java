/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.login;


import com.app.beans.AbstractController;
import com.app.beans.Viewable;
import java.beans.PropertyChangeEvent;

/**
 *
 * @author VJ
 */
public class LoginController extends AbstractController {

    private LoginModel loginModel;
    private LoginView loginView;
    protected Viewable mainView;

    public void setLoginModel(LoginModel logmodel) {
        loginModel = logmodel;
        loginModel.addPropertyChangeListener(this);
    }

    public void setMainView(Viewable mv) {
        mainView = mv;
    }

    public void setLoginView(LoginView logview) {
        loginView = logview;
    }

    public void loginUser(String Username, String P1Password) {
        loginModel.loginUser(Username, P1Password);
    }

    public void authenticateUser(int Grid) {
        loginModel.authenticate(Grid);
    }

    public void logOut() {
        loginModel.resetLogin();
    }




    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt.getPropertyName() + " : " + (evt.getNewValue()));
        switch (evt.getPropertyName()) {
            case "LoginStatus":
            case "ValidationStatus":
            case "ExceptionStatus":
                loginView.modelPropertyChange(evt);
                break;

            case "GoToMainMenu":
                mainView.modelPropertyChange(evt);
                break;
        }

    }
}
