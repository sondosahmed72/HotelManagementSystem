package com.example.hotelmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DataBaseConnection {
    // Use the relative path to the db folder
    private static final String URL = "jdbc:sqlite:data.db";


    public static void connectAndUpdate() {
        try (Connection conn = DriverManager.getConnection(URL)) {
            String sql = "INSERT INTO Residents (name, roomID, checkInDate, checkOutDate, boardingOption, totalCost) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, "John Doe");
                pstmt.setInt(2, 101); // Example roomID
                pstmt.setString(3, "2024-12-20");
                pstmt.setString(4, "2024-12-25");
                pstmt.setString(5, "Full Board");
                pstmt.setDouble(6, 500.0);

                pstmt.executeUpdate(); // This will update the database file
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
