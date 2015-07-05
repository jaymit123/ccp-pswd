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
import java.util.List;

/**
 *
 * @author VJ
 */
public class AuthenticationModel {

    private UserDAO userdao;
    private List<String> ImageList, UserList;

    public AuthenticationModel(UserDAO dao, List<String> imagelist) throws SecurityException {
        try {
            userdao = dao;
            ImageList = imagelist;
            UserList = userdao.getUserList();
            userdao.stopCommunication();
        } catch (DAOException ex) {
            throw new SecurityException(SecurityExReason.INIT_TIES, "Error in finalizeRegistration method of Authentication Model", ex);
        }
    }

    public boolean checkUsername(String username) {
        return UserList.contains(username);
    }

    public boolean finalizeRegistration(RegisterUser record) throws SecurityException {
        boolean result = false;
        try {
            userdao.startCommunication();
            result = userdao.registerUser(record.getUserRecord());
            if (result) {
                UserList = userdao.getUserList();
            }
            userdao.stopCommunication();
        } catch (DAOException ex) {
            if (ex.getErrorReason().equals(DAOExReason.REG_ERROR_USER_EXIST)) {
                throw new SecurityException(SecurityExReason.FIN_REG_USER_EXIST, ex.getMessage(), ex);
            } else {
                throw new SecurityException(SecurityExReason.FIN_REG_ERROR, "Error in finalizeRegistration method of Authentication Model", ex);
            }
        }
        return result;
    }

    public LoginUser processAccount(String username, String p1password) throws SecurityException {
        LoginUser user = null;
        try {
            userdao.startCommunication();
            String p2password = userdao.loginUser(username, p1password);
            userdao.stopCommunication();
            if (p2password != null) {
                user = new LoginUser(username, p2password, ImageList);
            }
        } catch (DAOException ex) {
            throw new SecurityException(SecurityExReason.LOGIN_ERROR, "Error in processAccount method of Authentication Model", ex);
        }
        return user;
    }

}