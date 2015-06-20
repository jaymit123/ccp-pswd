/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.exceptions;

/**
 *Error Codes:
 * 1049 : Unknown Database name, couldn't find db
 * 1044 : Could not access database, username provided doesn't have permission to access db
 * 1045: Could not access database , wrong password entered.
 * 1054 : Could not find column in table
 * 1062 : cant register user, username already exist
 * 1146 : Could not find table in database
 * 1064  :Parse Error : Tablename might be null
 *  0 : possible  path provided null
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
