/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.database;

import com.app.exceptions.DAOException;
import com.app.exceptions.DAOExReason;
import com.app.exceptions.DatabaseException;
import com.app.exceptions.DatabaseExReason;
import java.util.List;

/**
 *
 * @author VJ
 */
public class UserDAO{

    private DatabaseModel dbmodel = null;
<<<<<<< HEAD


    public UserDAO(DatabaseModel dbm){
            dbmodel = dbm; 
    }


    public List<String> getUserList() throws DAOException {
         List<String> UserList = null;
        try {
         UserList  = dbmodel.getUserList();
        } catch (DatabaseException ex) {
            throw new DAOException(DAOExReason.GET_USER_LIST_ERROR,"Error while synchronizing User Information", ex);
        }
=======
    private List<String> UserList = null;

    public UserDAO() throws DAOException {
        try {
            dbmodel = new DatabaseModel(DatabaseType.MYSQL, "//localhost/db", "root", "", "CCP_User_Table");
        } catch (DatabaseException ex) {
            throw new DAOException(DAOExReason.INIT_COM_ERROR,"Error while establishing Connection with Database", ex);
        }
    }

    public void syncUserList() throws DAOException {
        try {
            UserList = dbmodel.getUserList();
        } catch (DatabaseException ex) {
            throw new DAOException(DAOExReason.SYNC_USER_ERROR,"Error while synchronizing User Information", ex);
        }
    }

    public List<String> getUserList() {
>>>>>>> origin/master
        return UserList;
    }

    public boolean registerUser(String[] UserRecord) throws DAOException {
        boolean isRegistered = false;
        if (UserRecord.length == 3) {
            try {
                isRegistered = (dbmodel.registerUser(UserRecord[0], UserRecord[1], UserRecord[2]));
            } catch (DatabaseException ex) {
                 if (ex.getErrorReason().equals(DatabaseExReason.DB_USER_EXIST)) {
             throw new DAOException(DAOExReason.REG_ERROR_USER_EXIST,ex.getMessage(), ex);
        } else {
           throw new DAOException(DAOExReason.REG_ERROR_OTHER,"Error while registering UserRecord", ex);
        }

             
            }
        }
        return isRegistered;
    }

    public String loginUser(String Username, String P1Password) throws DAOException {
        try {
            return dbmodel.loginUser(Username, P1Password);
        } catch (DatabaseException ex) {
            throw new DAOException(DAOExReason.LOGIN_ERROR,"Error while performing User Login", ex);
        }
    }

    public void startCommunication() throws DAOException {
        try {
            dbmodel.createConnection();
        } catch (DatabaseException ex) {
            throw new DAOException(DAOExReason.START_COM_ERROR,"Error while starting Communication", ex);
        }
    }

    public void stopCommunication() throws DAOException {
        try {
            dbmodel.closeConnection();
        } catch (DatabaseException ex) {
            throw new DAOException(DAOExReason.STOP_COM_ERROR,"Error while stopping Communication", ex);
        }
    }

}
