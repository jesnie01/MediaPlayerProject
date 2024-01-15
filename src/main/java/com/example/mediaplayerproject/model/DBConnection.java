package com.example.mediaplayerproject.model;

import java.sql.*;
import java.util.Properties;

public class DBConnection {

    private static final String dbName = "dbMediaPlayer";
    private static final String userName = "sa";
    private static final String password = "1234";
    private static final String port = "1433";
    private static final String url = "jdbc:sqlserver://localhost:"+port+";databaseName="+dbName;
    private static Properties properties = new Properties();

    private static DBConnection dbConnection = null;
    private DBConnection() {
        properties.setProperty("user", userName);
        properties.setProperty("password", password);
        properties.setProperty("encrypt", "false");
    }

    public static DBConnection getDbConnection() {
        if (dbConnection == null)
            dbConnection = new DBConnection();
        return dbConnection;
    }

    public static Connection makeConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url,properties);
        return conn;
    }
}
