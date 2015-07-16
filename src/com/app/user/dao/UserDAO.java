/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.dao;

import com.app.user.database.DatabaseExReason;
import com.app.user.database.DatabaseException;
import com.app.user.database.DatabaseModel;
import java.util.List;

/**
 *
 * @author VJ
 */
public class UserDAO{

    private DatabaseModel dbModel = null;


    public UserDAO(DatabaseModel dbm){
            dbModel = dbm; 
    }


    public List<String> getUserList() throws DAOException {
         List<String> UserList = null;
        try {
         UserList  = dbModel.getUserList();
        } catch (DatabaseException ex) {
            throw new DAOException(DAOExReason.GET_USER_LIST_ERROR,"Error while synchronizing User Information", ex);
        }
        return UserList;
    }

    public boolean registerUser(String[] UserRecord) throws DAOException {
        boolean isRegistered = false;
        if (UserRecord.length == 2) {
            try {
                isRegistered = (dbModel.registerUser(UserRecord[0], UserRecord[1]));
            } catch (DatabaseException ex) {
                 if (ex.getErrorReason() == DatabaseExReason.DB_USER_EXIST) {
             throw new DAOException(DAOExReason.REG_ERROR_USER_EXIST,ex.getMessage(), ex);
        } else {
           throw new DAOException(DAOExReason.REG_ERROR_OTHER,"Error while registering UserRecord", ex);
        }

             
            }
        }
        return isRegistered;
    }

    public String loginUser(String Username) throws DAOException {
        try {
            return dbModel.loginUser(Username);
        } catch (DatabaseException ex) {
            throw new DAOException(DAOExReason.LOGIN_ERROR,"Error while performing User Login", ex);
        }
    }

    public void startCommunication() throws DAOException {
        try {
            dbModel.createConnection();
        } catch (DatabaseException ex) {
            throw new DAOException(DAOExReason.START_COM_ERROR,"Error while starting Communication", ex);
        }
    }

    public void stopCommunication() throws DAOException {
        try {
            dbModel.closeConnection();
        } catch (DatabaseException ex) {
            throw new DAOException(DAOExReason.STOP_COM_ERROR,"Error while stopping Communication", ex);
        }
    }

}
