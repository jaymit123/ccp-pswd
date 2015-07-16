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

    private LoginUser currentUser = null;
    private final AuthenticationModel authenticateUser;
    private final ImageModel imageModel;

    public LoginModel(AuthenticationModel aum, ImageModel im) {
        authenticateUser = aum;
        imageModel = im;
    }

    public void loginUser(String Username, String P1Password) {
        SwingWorker<Object, Void> LoginSW = new SwingWorker<Object, Void>() {
            private ProcessStatus property = ProcessStatus.NoProperty;

            @Override
            protected Object doInBackground() throws Exception {
                property = ProcessStatus.ValidationStatus;
                if (!(ValidationModel.validateUser(Username, P1Password) == ValidationStatus.BOTH_OK)) {
                    return ValidationStatus.BOTH_ERROR;
                }
                if (!authenticateUser.checkUsername(Username)) {
                    return ValidationStatus.ACC_DOESNT_EXIST;
                }
                if ((currentUser = authenticateUser.processAccount(Username, P1Password)) == null) {
                    return ValidationStatus.BOTH_ERROR;
                } else {
                    LoginStatus loginstatus = currentUser.authenticateGrid(-1);
                    if (loginstatus == LoginStatus.INIT) {
                        property = ProcessStatus.LoginStatus;
                        loginstatus.setImage(imageModel.getImage(loginstatus.getMessage()));
                        return loginstatus;
                    } else {
                        currentUser = null;
                        return ValidationStatus.BOTH_ERROR;
                    }
                }

            }

            @Override
            protected void done() {
                try {
                    LoginModel.this.firePropertyChange(property.toString(), null, get());
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
            private ProcessStatus property = ProcessStatus.NoProperty;

            @Override
            protected Object doInBackground() throws Exception {
                property = ProcessStatus.LoginStatus;
                if (currentUser == null) {
                    return LoginStatus.ERROR;
                } else {
                    LoginStatus loginstatus = currentUser.authenticateGrid(GridNo);
                    switch (loginstatus) {
                        case CONTINUE:
                            loginstatus.setImage(imageModel.getImage(loginstatus.getMessage()));
                            return loginstatus;

                        case SUCCESS:
                            return loginstatus;
                        case FAILURE:
                        case ERROR:
                        default:
                            currentUser = null;
                            return loginstatus;

                    }
                }

            }

            @Override
            protected void done() {
                try {
                    LoginModel.this.firePropertyChange(property.toString(), null, get());
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
            private ProcessStatus property = ProcessStatus.NoProperty;

            @Override
            protected Object doInBackground() throws Exception {
                property = ProcessStatus.GoToMainMenu;
                currentUser = null;
                return null;
            }

            @Override
            protected void done() {
                try {
                    LoginModel.this.firePropertyChange(property.toString(), null, get());
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
        if (ex.getCause() instanceof com.app.user.security.AuthenticationException) {
            com.app.user.security.AuthenticationException se = ((com.app.user.security.AuthenticationException) ex.getCause());
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
            ExceptionStatus es = ExceptionStatus.OTHER_ERROR;
            int index = ex.getMessage().indexOf(":");
            es.setMessage(ex.getMessage().substring(index + 1));
            LoginModel.this.firePropertyChange(ProcessStatus.ExceptionStatus.toString(), null, es);

        } else {
            LoginModel.this.firePropertyChange(ProcessStatus.ExceptionStatus.toString(), null, ExceptionStatus.FATAL_ERROR);
        }
    }

}
