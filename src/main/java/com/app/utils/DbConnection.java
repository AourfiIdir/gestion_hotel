package com.app.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String URL = "jdbc:h2:~/hotel_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        //code to establish a connection to the database
            return DriverManager.getConnection(URL, USER, PASSWORD);      
    }
}
