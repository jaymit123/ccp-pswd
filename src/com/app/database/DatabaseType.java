/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.database;

/**
 *
 * @author VJ
 */
public enum DatabaseType {

    MYSQL("com.mysql.jdbc.Driver","jdbc:mysql://"), H2("org.h2.Driver","jdbc:h2:~/test");
    private final String Driver;
    private final String Address;
    private DatabaseType(String driver,String address) {
        Driver = driver;
        Address = address;
    }

    public String getDriver() {
        return Driver;
    }
    
    public String getAddress(){
        return Address;
    }
}
