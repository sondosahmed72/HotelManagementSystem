package com.example.hotelmanagementsystem;
//import com.example.hotelmanagementsystem.manager.Classes.Manager.ManagerService;
import com.example.hotelmanagementsystem.manager.Classes.Manager.manager;

import java.sql.*;

public class DataBaseConnection {

    // Use the relative path to the db folder
    private static final String URL = "jdbc:sqlite:E:/TST2/HotelManagementSystem/db/data.db";
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

