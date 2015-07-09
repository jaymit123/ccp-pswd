/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.login;

import com.app.beans.AbstractModel;
import com.app.io.ImageAccessException;
import com.app.io.ImageModel;
import com.app.user.security.AuthenticationModel;
import com.app.user.security.ValidationModel;
import com.app.user.security.ValidationStatus;
import com.app.user.status.ExceptionStatus;
import com.app.user.status.ProcessStatus;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;

/**
 *
 * @author VJ
 */
public class LoginModel extends AbstractModel {

    private LoginUser CurrentUser = null;
    private final AuthenticationModel AuthenticateUser;
    private final ImageModel ImageModel;

    public LoginModel(AuthenticationModel aum, ImageModel im) {
        AuthenticateUser = aum;
        ImageModel = im;
    }

    public void loginUser(String Username, String P1Password) {
        SwingWorker<Object, Void> LoginSW = new SwingWorker<Object, Void>() {
            private ProcessStatus Property = ProcessStatus.NoProperty;

            @Override
            protected Object doInBackground() throws Exception {
                Property = ProcessStatus.ValidationStatus;
                if (!ValidationModel.validateUser(Username, P1Password).equals(ValidationStatus.BOTH_OK)) {
                    return ValidationStatus.BOTH_ERROR;
                }
                if (!AuthenticateUser.checkUsername(Username)) {
                    return ValidationStatus.ACC_DOESNT_EXIST;
                }
                if ((CurrentUser = AuthenticateUser.processAccount(Username, P1Password)) == null) {
                    return ValidationStatus.BOTH_ERROR;
                } else {
                    LoginStatus loginstatus = CurrentUser.authenticateGrid(-1);
                    if (loginstatus.equals(LoginStatus.INIT)) {
                        Property = ProcessStatus.LoginStatus;
                        loginstatus.setImage(ImageModel.getImage(loginstatus.getMessage()));
                        return loginstatus;
                    } else {
                        CurrentUser = null;
                        return ValidationStatus.BOTH_ERROR;
                    }
                }

            }

            @Override
            protected void done() {
                try {
                    LoginModel.this.firePropertyChange(Property.toString(), null, get());
                } catch (InterruptedException ex) {
                    LoginModel.this.firePropertyChange(ProcessStatus.ExceptionStatus.toString(), null, ExceptionStatus.FATAL_ERROR);
                } catch (ExecutionException ex) {
                    handleExecutionException(ex);
                }
            }

        };
        LoginSW.execute();
    }

    public void authenticate(int GridNo) {
        SwingWorker<Object, Void> AuthSW = new SwingWorker<Object, Void>() {
            private ProcessStatus Property = ProcessStatus.NoProperty;

            @Override
            protected Object doInBackground() throws Exception {
                Property = ProcessStatus.LoginStatus;
                if (CurrentUser == null) {
                    return LoginStatus.ERROR;
                } else {
                    LoginStatus loginstatus = CurrentUser.authenticateGrid(GridNo);
                    switch (loginstatus) {
                        case CONTINUE:
                            loginstatus.setImage(ImageModel.getImage(loginstatus.getMessage()));
                            return loginstatus;

                        case SUCCESS:
                            return loginstatus;
                        case FAILURE:
                        case ERROR:
                        default:
                            CurrentUser = null;
                            return loginstatus;

                    }
                }

            }

            @Override
            protected void done() {
                try {
                    LoginModel.this.firePropertyChange(Property.toString(), null, get());
                } catch (InterruptedException ex) {
                    LoginModel.this.firePropertyChange(ProcessStatus.ExceptionStatus.toString(), null, ExceptionStatus.FATAL_ERROR);
                } catch (ExecutionException ex) {
                    handleExecutionException(ex);
                }

            }
        };
        AuthSW.execute();
    }

    public void resetLogin() {
        SwingWorker<Object, Void> ResetSW = new SwingWorker<Object, Void>() {
            private ProcessStatus Property = ProcessStatus.NoProperty;

            @Override
            protected Object doInBackground() throws Exception {
                Property = ProcessStatus.GoToMainMenu;
                CurrentUser = null;
                return null;
            }

            @Override
            protected void done() {
                try {
                    LoginModel.this.firePropertyChange(Property.toString(), null, get());
                } catch (InterruptedException ex) {
                    LoginModel.this.firePropertyChange(ProcessStatus.ExceptionStatus.toString(), null, ExceptionStatus.FATAL_ERROR);
                } catch (ExecutionException ex) {
                    handleExecutionException(ex);
                }

            }
        };
        ResetSW.execute();
    }

    private void handleExecutionException(ExecutionException ex) {
        if (ex.getCause() instanceof com.app.user.security.SecurityException) {
            com.app.user.security.SecurityException se = ((com.app.user.security.SecurityException) ex.getCause());
            switch (se.getErroReason()) {
                case PASS_REGEX_CHECK_ERROR:
                case ACC_IMG_NOT_FOUND:
                    ExceptionStatus eso = ExceptionStatus.OTHER_ERROR;
                    eso.setMessage(se.getErroReason().getMessage());
                    LoginModel.this.firePropertyChange(ProcessStatus.ExceptionStatus.toString(), null, eso);
                    break;

                default:
                    LoginModel.this.firePropertyChange(ProcessStatus.ExceptionStatus.toString(), null, ExceptionStatus.FATAL_ERROR);
                    break;
            }
        } else if (ex.getCause() instanceof ImageAccessException) {

            ExceptionStatus es = ExceptionStatus.FATAL_ERROR;
            int index = ex.getMessage().indexOf(":");
            es.setMessage(ex.getMessage().substring(index+1));
            LoginModel.this.firePropertyChange(ProcessStatus.ExceptionStatus.toString(), null, es);

        } else {
            LoginModel.this.firePropertyChange(ProcessStatus.ExceptionStatus.toString(), null, ExceptionStatus.FATAL_ERROR);
        }
    }

}
