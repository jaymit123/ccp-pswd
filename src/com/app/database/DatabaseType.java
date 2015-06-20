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

    MYSQL("com.mysql.jdbc.Driver"), H2("org.h2.Driver");
    private final String DriverPath;

    private DatabaseType(String link) {
        DriverPath = link;
    }

    public String getPath() {
        return DriverPath;
    }
}
