package com.example.mediaplayerproject;

import java.sql.*;
import java.util.Properties;

public class dbConnection {

    private static final String dbName = "dbMediaPlayer";
    private static final String userName = "sa";
    private static final String password = "1234";
    private static final String port = "1433";
    private static final String url = "jdbc:sqlserver://localhost:"+port+";databaseName="+dbName;
    private static Properties properties = new Properties();

    private dbConnection() {
        properties.setProperty("user", userName);
        properties.setProperty("password", password);
    }

    public Connection makeConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url,properties);
        return conn;
    }




}
