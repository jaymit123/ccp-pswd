/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.database;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.LinkedList;

/**
 *
 * @author VJ
 */
public class DatabaseModel {

    private DatabaseType selected;                            //Select type of database h2 / mysql / any future db.
    private Connection databaseConnection;
    private final String dbPath, dbUsername, dbPassword, dbTableName;
    private final String UserList_SQL_QUERY;

    public DatabaseModel(DatabaseType dt, String pth, String uname, String pwd, String tablename) throws DatabaseException {
        try {
            selected = dt;
            dbPath = pth;
            dbUsername = uname;
            dbPassword = pwd;
            dbTableName = tablename;
            Class.forName(selected.getDriver()).newInstance();
            UserList_SQL_QUERY = "Select Username from " + dbTableName + ";";
            createConnection();
        } catch (ClassNotFoundException ex) {
            throw new DatabaseException("ClassNotFoundException : Could not find Instance of Driver " + selected.name() + " interface implementation.", ex);
        } catch (InstantiationException ex) {
            throw new DatabaseException("InstantiationException : Could not create Instance of specified Driver " + selected.name() + " Interface implementation. : ", ex);
        } catch (IllegalAccessException ex) {
            throw new DatabaseException("IllegalAccessException : Could Not Access the method/constructor of a " + selected.name() + " class", ex);
        }
    }

    private void createConnection() throws DatabaseException {
        try {
            databaseConnection = DriverManager.getConnection(selected.getAddress() + dbPath, dbUsername, dbPassword);
        } catch (SQLException ex) {
            throw new DatabaseException("SQLException occured while creating Connection Object in createConnection Method", ex);
        }
    }

    public List<String> getUserList() throws DatabaseException {
        try (Statement UserListStmt = databaseConnection.createStatement()) {
            List<String> usernames = new LinkedList<>();
            try (ResultSet QueryResult = UserListStmt.executeQuery(UserList_SQL_QUERY)) {
                if (QueryResult.isBeforeFirst()) {
                    while (QueryResult.next()) {
                        usernames.add(QueryResult.getString(1));
                    }
                }
                return usernames;
            } catch (SQLException ex) {
                throw new DatabaseException("SQLException occured while using ResultSet in getUserList Method.", ex);
            }
        } catch (SQLException ex) {
            throw new DatabaseException("SQLException occured while fetching UserList in getUserList Method.", ex);
        }
    }

    public boolean registerUser(String username, String password) throws DatabaseException {

        try (Statement RegisterUserStmt = databaseConnection.createStatement()) {
            boolean isRegistered = false;
            String Register_SQL_QUERY = "Insert into " + dbTableName + " (Username,Password) values('" + username + "','" + password + "');";
            if (RegisterUserStmt.executeUpdate(Register_SQL_QUERY) == 1) { //executeUpdate() returns 1 if a row is added/updated.
                isRegistered = true;
            }
            return isRegistered;
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062 || ex.getErrorCode() == 23505) {
                throw new DatabaseException("Sorry, The Username " + username + " already exists! ", ex);
            } else {
                throw new DatabaseException("SQLException occured while inserting user record in registerUser Method", ex);
            }
        }
    }

    public String loginUser(String username) throws DatabaseException {
        try (Statement LoginUserStmt = databaseConnection.createStatement()) {
            String Login_SQL_QUERY = "Select Password from " + dbTableName + " where Username = '" + username + "';";
            try (ResultSet QueryResult = LoginUserStmt.executeQuery(Login_SQL_QUERY)) {
                String password = null;
                if (QueryResult.isBeforeFirst() && QueryResult.next()) {
                    password = QueryResult.getString(1);
                }
                return password;
            } catch (SQLException ex) {
                throw new DatabaseException("SQLException occured in ResultSet of LoginUser method", ex);
            }
        } catch (SQLException ex) {
            throw new DatabaseException("SQLException in LoginUser Method", ex);
        }
    }

    public void initConnection() throws DatabaseException {
        if (databaseConnection == null) {
            createConnection();
        }
    }

    public void closeConnection() throws DatabaseException {
        try {
            if (databaseConnection != null) {
                databaseConnection.close();
                databaseConnection = null;
            }
        } catch (SQLException ex) {
            throw new DatabaseException("SQLException occured while closing Connection Object in closeConnection Method", ex);
        }
    }
}
