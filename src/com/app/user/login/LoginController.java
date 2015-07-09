/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.login;

import com.app.user.register.*;
import com.app.beans.AbstractModel;
import com.app.beans.AbstractController;
import com.app.beans.Viewable;
import com.app.user.status.ProcessStatus;
import java.beans.PropertyChangeEvent;

/**
 *
 * @author VJ
 */
public class LoginController extends AbstractController {

    private LoginModel LoginModel;
    private LoginView LoginView;
    protected Viewable MainView;

    public void setLoginModel(LoginModel logmodel) {
        LoginModel = logmodel;
        LoginModel.addPropertyChangeListener(this);
    }

    public void setMainView(Viewable mv) {
        MainView = mv;
    }

    public void setLoginView(LoginView logview) {
        LoginView = logview;
    }

    public void loginUser(String Username, String P1Password) {
        LoginModel.loginUser(Username, P1Password);
    }

    public void authenticateUser(int Grid) {
        LoginModel.authenticate(Grid);
    }

    public void logOut() {
        System.out.println("reset");
        LoginModel.resetLogin();
    }




    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt.getPropertyName() + " : " + (evt.getNewValue()));
        switch (evt.getPropertyName()) {
            case "LoginStatus":
            case "ValidationStatus":
            case "ExceptionStatus":
                LoginView.modelPropertyChange(evt);
                break;

            case "GoToMainMenu":
                MainView.modelPropertyChange(evt);
                break;
        }

    }
}
