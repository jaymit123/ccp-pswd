/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.database;

import com.app.exceptions.DatabaseException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.LinkedList;
/**
 *
 * @author VJ
 */
public class DatabaseModel {
    private Connection database_con;
    private String Path, Username, Password, TableName;
    private final String UserList_SQL_QUERY, RegisterUser_SQL_QUERY, LoginUser_SQL_QUERY;
    private PreparedStatement UserListPS, RegisterUserPS, LoginUserPS;

    public DatabaseModel(String path, String username, String password, String tablename) throws DatabaseException {
        Path = path;
        Username = username;
        Password = password;
        TableName = tablename;
        UserList_SQL_QUERY = "Select Username from " + TableName;
        RegisterUser_SQL_QUERY = "Insert into " + TableName + "(Username,P1Password,P2Password) values(?,?,?)";
        LoginUser_SQL_QUERY = "Select P2Password from " + TableName + " where Username = ? and P1Password = ?";
        createConnection();
    }

    public static void execute() throws DatabaseException {
        DatabaseModel db = new DatabaseModel("jdbc:mysql://localhost/db" ,"root", "", "CCP_User_Table");
        db.getUserList();
        db.closeConnection();
        db.initConnection();
        System.out.println(db.registerUser("ddjd", "dd", ""));
    }

    private void createConnection() throws DatabaseException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            database_con = DriverManager.getConnection(Path, Username, Password);
            UserListPS = database_con.prepareStatement(UserList_SQL_QUERY);
            RegisterUserPS = database_con.prepareStatement(RegisterUser_SQL_QUERY);
            LoginUserPS = database_con.prepareStatement(LoginUser_SQL_QUERY);
        } catch (SQLException ex) {
            throw new DatabaseException("SQLException", ex);
        } catch (ClassNotFoundException ex) {
            throw new DatabaseException("ClassNotFoundException : Could not find Instance of Driver interface implementation.", ex);
        } catch (InstantiationException ex) {
            throw new DatabaseException("InstantiationException : Could not create Instance of specified Driver Interface implementation. : ", ex);
        } catch (IllegalAccessException ex) {
            throw new DatabaseException("IllegalAccessException : Could Not Access the method/constructor of a class", ex);
        }
    }

    public List<String> getUserList() throws DatabaseException {
        List<String> usernames = new LinkedList<>();
        try {
            ResultSet QueryResult = UserListPS.executeQuery();
            if (QueryResult.isBeforeFirst()) {
                while (QueryResult.next()) {
                    usernames.add(QueryResult.getString(1));
                }
            }
        } catch (SQLException ex) { 
            throw new DatabaseException("SQLException", ex);
        }
        return usernames;
    }

    public boolean registerUser(String Username, String P1Password, String P2Password) throws DatabaseException {

        boolean isRegistered = false;
        try {
            RegisterUserPS.setString(1, Username);
            RegisterUserPS.setString(2, P1Password);
            RegisterUserPS.setString(3, P2Password);
            if (RegisterUserPS.executeUpdate() == 1) { //executeUpdate() returns 1 if a row is added/updated.
                isRegistered = true;  
            }
            RegisterUserPS.clearParameters();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                throw new DatabaseException("Sorry, The Username " + Username + " already exists! ", ex);
            } else {
                throw new DatabaseException("SQLException", ex);
            }
        }
        return isRegistered;
    }

    public String loginUser(String Username, String P1Password) throws DatabaseException {
        String P2Password = null;
        try {
            LoginUserPS.setString(1, Username);
            LoginUserPS.setString(2, P1Password);
            ResultSet QueryResult = LoginUserPS.executeQuery();
            if (QueryResult.isBeforeFirst() && QueryResult.next()) {
                P2Password = QueryResult.getString(1);
            }
            LoginUserPS.clearParameters();
        } catch (SQLException ex) {
            throw new DatabaseException("SQLException", ex);
        }
        return P2Password;

    }

    public void initConnection() throws DatabaseException {

        if (database_con == null) {
            createConnection();
        }

    }

    public void closeConnection() throws DatabaseException {

        try {
            if (!database_con.isClosed()) {
                RegisterUserPS.close();
                UserListPS.close();
                database_con.close();
            }
            LoginUserPS = null;
            RegisterUserPS = null;
            UserListPS = null;
            database_con = null;
        } catch (SQLException ex) {
            throw new DatabaseException("SQLException", ex);
        }
    }
}
