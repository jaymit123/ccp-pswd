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
import java.sql.Statement;
import java.util.List;
import java.util.LinkedList;

/**
 *
 * @author VJ
 */
public class DatabaseModel {

    private DatabaseType selected;
    private Connection database_con;
    private final String Path, Username, Password, TableName;
    private final String UserList_SQL_QUERY;

    public  DatabaseModel(DatabaseType dt, String path, String username, String password, String tablename) throws DatabaseException {
        try {
            selected = dt;
            Path = path;
            Username = username;
            Password = password;
            TableName = tablename;
            Class.forName(selected.getDriver()).newInstance();
            UserList_SQL_QUERY = "Select Uscername from " + TableName+";";
            createConnection();
        } catch (ClassNotFoundException ex) {
            throw new DatabaseException("ClassNotFoundException : Could not find Instance of Driver " + selected.name() + " interface implementation.", ex);
        } catch (InstantiationException ex) {
            throw new DatabaseException("InstantiationException : Could not create Instance of specified Driver " + selected.name() + " Interface implementation. : ", ex);
        } catch (IllegalAccessException ex) {
            throw new DatabaseException("IllegalAccessException : Could Not Access the method/constructor of a " + selected.name() + " class", ex);
        }
    }

    public static void execute() throws DatabaseException {
        DatabaseModel db = new DatabaseModel(DatabaseType.H2, "", "sa", "","");
        db.initConnection();
        
        System.out.print(db.registerUser("d", "dd", "fff"));
    }

    public void createConnection() throws DatabaseException {
        try {
            database_con = DriverManager.getConnection(selected.getAddress() + Path, Username, Password);
        } catch (SQLException ex) { 
            throw new DatabaseException("SQLException occured while creating Connection Object in createConnection Method", ex);
        }
    }

    public List<String> getUserList() throws DatabaseException {
        List<String> usernames = new LinkedList<>();
        try (Statement UserListStmt = database_con.createStatement()) {
            try (ResultSet QueryResult = UserListStmt.executeQuery(UserList_SQL_QUERY)) {
                if (QueryResult.isBeforeFirst()) {
                    while (QueryResult.next()) {
                        usernames.add(QueryResult.getString(1));
                    }
                }
            } catch (SQLException ex) { 
                throw new DatabaseException("SQLException occured while using ResultSet in getUserList Method.", ex);
            }
        } catch (SQLException ex) {
            throw new DatabaseException("SQLException occured while fetching UserList in getUserList Method.", ex);
        }
        return usernames;
    }

    public boolean registerUser(String Username, String P1Password, String P2Password) throws DatabaseException {

        boolean isRegistered = false;
        try (Statement RegisterUserStmt = database_con.createStatement()) {
            String Register_SQL_QUERY = "Insert into " + TableName + " (Username,P1Password,P2Password) values('"+Username+"','"+P1Password+"','"+P2Password+"');";
            if (RegisterUserStmt.executeUpdate(Register_SQL_QUERY) == 1) { //executeUpdate() returns 1 if a row is added/updated.
                isRegistered = true;
            }
        } catch (SQLException ex) { System.out.println(ex.getErrorCode());
            if (ex.getErrorCode() == 1062 || ex.getErrorCode() == 23505) {
                throw new DatabaseException("Sorry, The Username " + Username + " already exists! ", ex);
            } else {
                throw new DatabaseException("SQLException occured while inserting user record in registerUser Method", ex);
            }
        }
        return isRegistered;
    }

    public String loginUser(String Username, String P1Password) throws DatabaseException {
        String P2Password = null;
        try (Statement LoginUserStmt = database_con.createStatement()) {
            String Login_SQL_QUERY = "Select P2Password from " + TableName + " where Username = '" + Username + "' and P1Password = '" + P1Password + "';";
            try (ResultSet QueryResult = LoginUserStmt.executeQuery(Login_SQL_QUERY)) {
                if (QueryResult.isBeforeFirst() && QueryResult.next()) {
                    P2Password = QueryResult.getString(1);
                }
            } catch (SQLException ex) {
                throw new DatabaseException("SQLException occured in ResultSet of LoginUser method", ex);
            }
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
            if (database_con != null) {
                database_con.close();
                database_con = null;
            }
        } catch (SQLException ex) {
            throw new DatabaseException("SQLException occured while closing Connection Object in closeConnection Method", ex);
        }
    }
}
