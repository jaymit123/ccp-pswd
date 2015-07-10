/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.register;

import com.app.user.security.AuthenticationModel;
import com.app.user.security.ValidationModel;
import com.app.user.security.AppSecurityException;
import com.app.user.security.ValidationStatus;
import com.app.user.status.ProcessStatus;
import com.app.beans.AbstractModel;
import com.app.io.ImageAccessException;
import com.app.io.ImageModel;
import com.app.user.status.ExceptionStatus;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 *
 * @author VJ
 */
public class RegisterModel extends AbstractModel {

    private RegisterUser RegUser = null;
    private final AuthenticationModel AuthenticateUser;
    private final ImageModel ImageModel;

    public RegisterModel(AuthenticationModel aum, ImageModel im) {
        AuthenticateUser = aum;
        ImageModel = im;
    }

    public void createAccount(String Username, String P1Password) {
        SwingWorker<Object, Void> CreateAccountSW = new SwingWorker<Object, Void>() {
            private ProcessStatus CurrentProcess;

            @Override
            protected Object doInBackground() throws Exception {
                //Validate User Start//
                CurrentProcess = ProcessStatus.ValidationStatus;
                ValidationStatus ValidateData = ValidationModel.validateUser(Username, P1Password);
                if (!ValidateData.equals(ValidationStatus.BOTH_OK)) {
                    return ValidateData;
                }
                if (AuthenticateUser.checkUsername(Username)) {
                    return ValidationStatus.USERNAME_EXIST;
                }
                //Validate User End//

                //Registration Process Start//
                RegUser = new RegisterUser(Username, P1Password);
                return ValidationStatus.BOTH_OK;
                // Registration Process End//
            }

            @Override
            protected void done() {
                try {
                    RegisterModel.this.firePropertyChange(CurrentProcess.toString(), null, get());
                } catch (InterruptedException | ExecutionException ex) {
                    RegUser = null;
                    RegisterModel.this.firePropertyChange(ProcessStatus.ExceptionStatus.toString(), null, ExceptionStatus.FATAL_ERROR);
                }
            }

        };
        CreateAccountSW.execute();
    }

    public void addEntry(String ImageName, int GridNumber) {
        SwingWorker<Object, Void> AddEntrySW = new SwingWorker<Object, Void>() {
            private ProcessStatus CurrentProcess;

            @Override
            protected Object doInBackground() throws AppSecurityException, Exception {

                if (RegUser == null) { //Check if RegisterUser is created!
                    CurrentProcess = ProcessStatus.ValidationStatus;
                    return ValidationStatus.NO_ACCOUNT;
                }

                //Start Adding Entry.//
                CurrentProcess = ProcessStatus.RegisterStatus;
                RegisterStatus regstatus = RegUser.addEntry(ImageName, GridNumber);
                switch (regstatus) {
                    case REGISTER_SUCCESS:
                        return completeRegistration();
                    case REGISTER_FAILED:
                        RegUser = null;
                        return regstatus;

                    case ADDED:
                    case ALERT:
                        return regstatus;

                    default: //Will Never be reached but added for completeness
                        CurrentProcess = ProcessStatus.NoProperty;
                        return null;

                }
            }

            @Override
            protected void done() {
                try {
                    RegisterModel.this.firePropertyChange(CurrentProcess.toString(), null, get());
                } catch (InterruptedException ex) {
                    RegisterModel.this.firePropertyChange(ProcessStatus.ExceptionStatus.toString(), null, ExceptionStatus.FATAL_ERROR);
                } catch (ExecutionException ex) {
                    handleExecutionException(ex);
                }

            }

        };
        AddEntrySW.execute();

    }

    public void finalizeRegistration() {
        SwingWorker<Object, Void> FinRegSW = new SwingWorker<Object, Void>() {
            private ProcessStatus CurrentProcess;

            @Override
            protected Object doInBackground() throws AppSecurityException, Exception {

                if (RegUser == null) { //Check if RegisterUser is created!
                    CurrentProcess = ProcessStatus.ValidationStatus;
                    return ValidationStatus.NO_ACCOUNT;
                }
                CurrentProcess = ProcessStatus.RegisterStatus;
                return completeRegistration();
            }

            @Override
            protected void done() {
                try {
                    RegisterModel.this.firePropertyChange(CurrentProcess.toString(), null, get());
                } catch (InterruptedException ex) {
                    RegisterModel.this.firePropertyChange(ProcessStatus.ExceptionStatus.toString(), null, ExceptionStatus.FATAL_ERROR);
                } catch (ExecutionException ex) {
                    handleExecutionException(ex);
                }

            }

        };
        FinRegSW.execute();

    }

    public void reset(String type) {
        SwingWorker<Object, Void> ResetSW = new SwingWorker<Object, Void>() {
            private ProcessStatus CurrentProcess;

            @Override
            protected Object doInBackground() throws Exception {
                switch (type) {
                    case "FULL_RESET":
                        CurrentProcess = ProcessStatus.RegisterStatus;
                        RegUser = null;
                        return RegisterStatus.FULL_RESET;

                    case "P2_RESET":
                        CurrentProcess = ProcessStatus.RegisterStatus;
                        RegUser.resetPhase2();
                        return RegisterStatus.P2_RESET;

                    case "MAINMENU":
                        CurrentProcess = ProcessStatus.GoToMainMenu;
                        RegUser = null;
                        return null;
                    default:
                        CurrentProcess = ProcessStatus.NoProperty;
                        return null;
                }

            }

            protected void done() {
                try {
                    RegisterModel.this.firePropertyChange(CurrentProcess.toString(), null, get());
                } catch (InterruptedException | ExecutionException ex) {
                    RegisterModel.this.firePropertyChange(ProcessStatus.ExceptionStatus.toString(), null, ExceptionStatus.FATAL_ERROR);

                }

            }

        };
        ResetSW.execute();
    }

    public void getImage(String ImageName) {
        SwingWorker<Object, Void> GetImageSW = new SwingWorker<Object, Void>() {
            private ProcessStatus CurrentProcess;

            @Override
            protected Object doInBackground() throws Exception {
                CurrentProcess = ProcessStatus.DisplayImage;
                return ImageModel.getImage(ImageName);
            }

            @Override
            protected void done() {
                try {
                    RegisterModel.this.firePropertyChange(CurrentProcess.toString(), null, get());
                } catch (InterruptedException ex) {
                    RegisterModel.this.firePropertyChange(ProcessStatus.ExceptionStatus.toString(), null, ExceptionStatus.FATAL_ERROR);
                } catch (ExecutionException ex) { 
                    handleExecutionException(ex);
                }
            }
        };
        GetImageSW.execute();
    }

    private RegisterStatus completeRegistration() throws AppSecurityException {
        if (AuthenticateUser.finalizeRegistration(RegUser)) {
            RegUser = null;
            return RegisterStatus.REGISTER_SUCCESS;
        } else {
            RegUser = null;
            return RegisterStatus.REGISTER_FAILED;
        }
    }

    private void handleExecutionException(ExecutionException ex) {
        if (ex.getCause() instanceof AppSecurityException) {
            AppSecurityException se = ((AppSecurityException) ex.getCause());
            switch (se.getErroReason()) {
                case FIN_REG_USER_EXIST:
                    ExceptionStatus es = ExceptionStatus.USER_EXIST;
                    es.setMessage(se.getMessage());
                    RegisterModel.this.firePropertyChange(ProcessStatus.ExceptionStatus.toString(), null, ExceptionStatus.USER_EXIST);
                    break;

                default:
                    RegisterModel.this.firePropertyChange(ProcessStatus.ExceptionStatus.toString(), null, ExceptionStatus.FATAL_ERROR);
                    break;
            }
        } else if (ex.getCause() instanceof ImageAccessException) {

            ExceptionStatus es = ExceptionStatus.FATAL_ERROR;
            int index = ex.getMessage().indexOf(":");
            es.setMessage(ex.getMessage().substring(index + 1));
            RegisterModel.this.firePropertyChange(ProcessStatus.ExceptionStatus.toString(), null, es);

        } else {
            RegisterModel.this.firePropertyChange(ProcessStatus.ExceptionStatus.toString(), null, ExceptionStatus.FATAL_ERROR);
        }
    }

}
