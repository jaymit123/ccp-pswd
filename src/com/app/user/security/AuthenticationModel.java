/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.security;

import com.app.user.login.LoginUser;
import com.app.user.register.RegisterUser;
import com.app.user.dao.UserDAO;
import com.app.user.dao.DAOException;
import com.app.user.dao.DAOExReason;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author VJ
 */
public class AuthenticationModel {

    private UserDAO userdao;
    private List<String> ImageList, UserList;
    private AESCipher aesCipher;

    public AuthenticationModel(UserDAO dao, List<String> imagelist) throws AuthenticationException {
        try {
            userdao = dao;
            ImageList = imagelist;
            UserList = new LinkedList<String>(userdao.getUserList()) {
                /*Ensures that username is checked irrespective of case*/
                @Override
                public boolean contains(Object o) {
                    String paramStr = (String) o;
                    for (String s : this) {
                        if (paramStr.equalsIgnoreCase(s)) {
                            return true;
                        }
                    }
                    return false;
                }

            };
            userdao.stopCommunication();
            aesCipher = new AESCipher();
        } catch (DAOException ex) {
            throw new AuthenticationException(AuthenticationExReason.INIT_DBCON_ERROR, "Error in finalizeRegistration method of Authentication Model", ex);
        }
    }

    public boolean checkUsername(String username) {
        return UserList.contains(username);
    }

    public boolean finalizeRegistration(RegisterUser record) throws AuthenticationException {

        try {
            boolean isRegistered = false;
            userdao.startCommunication();
            isRegistered = userdao.registerUser(record.getUserRecord());
            if (isRegistered) {
                UserList = userdao.getUserList();
            }
            userdao.stopCommunication();
            return isRegistered;
        } catch (DAOException ex) {
            if (ex.getErrorReason() == DAOExReason.REG_ERROR_USER_EXIST) {
                throw new AuthenticationException(AuthenticationExReason.FIN_REG_USER_EXIST, ex.getMessage(), ex);
            } else {
                throw new AuthenticationException(AuthenticationExReason.FIN_REG_ERROR, "Error in finalizeRegistration method of Authentication Model", ex);
            }

        } catch (AESCipherException ex) {
            throw new AuthenticationException(AuthenticationExReason.ENCRYPT_ERROR, "Error in finalizeRegistration method of Authentication Model", ex);
        }
    }

    public LoginUser processAccount(String username, String p1password) throws AuthenticationException {
        LoginUser user = null;
        try {
            userdao.startCommunication();
            String encryptedPswd = userdao.loginUser(username);
            userdao.stopCommunication();
            if (encryptedPswd != null) {
                String decryptedPswd = aesCipher.decryptPassword(username, p1password, encryptedPswd);

                if (ValidationModel.validateP2Passowrd(decryptedPswd)) {
                    user = new LoginUser(username, decryptedPswd, ImageList);
                }
            }
            return user;
        } catch (DAOException ex) {
            throw new AuthenticationException(AuthenticationExReason.LOGIN_ERROR, "Error in processAccount method of Authentication Model", ex);
        } catch (AESCipherException ex) {
            throw new AuthenticationException(AuthenticationExReason.DECRYPT_ERROR, "Error in processAccount method of Authentication Model", ex);
        }

    }

}
