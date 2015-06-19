/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.database;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

/**
 *
 * @author VJ
 */
public class DatabaseModel {

    private final Connection database_con ;
    private final String path,username,password,Table_Name;
    private final String UserList_SQL_QUERY, RegisterUser_SQL_QUERY, LoginUser_SQL_QUERY;
    private final PreparedStatement UserListPS, RegisterUserPS, LoginUserPS;

    public DatabaseModel(String path, String username, String password, String tablename) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Table_Name = tablename;
        this.username = username;
        this.password = password;
        this.path = path;
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        database_con = DriverManager.getConnection(path, username, password);
        UserList_SQL_QUERY = "Select Username from " + Table_Name;
        RegisterUser_SQL_QUERY = "Insert into " + Table_Name +  "(Username,P1Password,P2Password) values(?,?,?)";
        LoginUser_SQL_QUERY = "Select * from " + Table_Name + " where Username = ? and P1Password = ?";
        UserListPS = database_con.prepareStatement(UserList_SQL_QUERY);
        RegisterUserPS = database_con.prepareStatement(RegisterUser_SQL_QUERY);
        LoginUserPS = database_con.prepareStatement(LoginUser_SQL_QUERY);
    }

    public static void execute() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
            DatabaseModel db = new DatabaseModel("jdbc:mysql://localhost/db", "root", "", "CCP_User_Table");
        

    }
    
    
    public List<String> getUserList() throws SQLException{
        List<String> usernames = new LinkedList<>();
        ResultSet QueryResult = UserListPS.executeQuery();
        if(QueryResult.isBeforeFirst()){
            while(QueryResult.next()) usernames.add(QueryResult.getString(1));
        }
        return usernames;
    }
    
    public boolean registerUser(String Username , String P1Password , String P2Password)throws SQLException{
        boolean isRegistered = false;
        LoginUserPS.setString(1, Username);
        LoginUserPS.setString(2, P1Password);
        LoginUserPS.setString(3, P2Password);
        if(LoginUserPS.execute()) isRegistered = true;
        return isRegistered;
    }
    

}
