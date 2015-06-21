/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.exceptions;

/**
 *MySQL Error Codes:
 * 1049 : Unknown Database name, couldn't find db
 * 1044 : Could not access database, username provided doesn't have permission to access db
 * 1045: Could not access database , wrong password entered.
 * 1054 : Could not find column in table
 * 1062 : cant register user, username already exist
 * 1146 : Could not find table in database
 * 1064  :Parse Error : Tablename might be null
 *  0 : possible  path provided null
 * 
 * 
 * H2 Error Codes: 
 * 23505 : Username already exist.
 * 28000 : DB access username / password is wrong
 * 42122 : Column not found
 * 42102 : SQL Table not found
 * 42001 : Null table name /  1 or more Column name not matching
 * @author VJ
 */
public class DatabaseException extends Exception {

    public DatabaseException(String string) {
        super(string);

    }

    public DatabaseException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }
    
  
}
