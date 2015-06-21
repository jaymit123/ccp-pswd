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

    MYSQL("com.mysql.jdbc.Driver", "jdbc:mysql:"), H2("org.h2.Driver", "jdbc:h2:~/h2_ccp_db;INIT=runscript from '" + DatabaseType.class.getResource("/init.sql").getPath().substring(1) + "';"); // the init property executes the init.sql file to create the table
    private final String Driver;
    private String Address;
    private final String h2_init_file = "INIT=runscript from '" + DatabaseType.class.getResource("/init.sql").getPath().substring(1) + "';";

    private DatabaseType(String driver, String address) {
        Driver = driver;
        Address = address;
    }

    public String getDriver() {
        return Driver;
    }

    public void setAddress(String address, boolean isAutomated) { //boolean param checks if user want to create own table or want the system to provide a table
        if (this == DatabaseType.H2) {
            if (isAutomated) {
                Address = "jdbc:h2:" + address + ";" + h2_init_file;
            } else {
                Address += "jdbc:h2:" + address + ";";
            }
        } else {
            Address += address;
        }
    }
    
    public String getAddress() {
        return Address;
    }
}
