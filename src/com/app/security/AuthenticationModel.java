/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.security;

import com.app.security.login.ExistingUser;
import com.app.security.register.RegisterUser;
import com.app.database.UserDAO;
import com.app.exceptions.DAOException;
import com.app.exceptions.DAOExReason;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author VJ
 */
public class AuthenticationModel {

    private UserDAO userdao;
    private List<String> ImageList, UserList;

    public AuthenticationModel(UserDAO dao, List<String> imagelist) {
        try {
            userdao = dao;
            ImageList = imagelist;
            UserList = userdao.getUserList();
            userdao.stopCommunication();
        } catch (DAOException ex) {

        }
    }

    public boolean checkUsername(String username) {
        return UserList.contains(username);
    }

    public boolean finalizeRegistration(RegisterUser record) {
        boolean result = false;
        try {
            userdao.startCommunication();
            result = userdao.registerUser(record.getUserRecord());
            if (result) {
                UserList = userdao.getUserList();
            }
            userdao.stopCommunication();
        } catch (DAOException ex) {

        }
        return result;
    }

    public ExistingUser processAccount(String username, String p1password) {
        ExistingUser user = null;
        try {
            userdao.startCommunication();
            String p2password = userdao.loginUser(username, p1password);
            userdao.stopCommunication();
            user = new ExistingUser(username, p2password, ImageList);
        } catch (DAOException ex) {

        }
        return user;
    }

}
