package com.example.hotelmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseConnection {

    // Use the relative path to the db folder
    private static final String URL = "jdbc:sqlite:E:/tst/HotelManagementSystem/db/data.db";

    private static Connection connection;

    // Method to get the URL of the database
    public static String getDatabaseUrl() {
        return URL;
    }

    // Method to get a connection to the database
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}

