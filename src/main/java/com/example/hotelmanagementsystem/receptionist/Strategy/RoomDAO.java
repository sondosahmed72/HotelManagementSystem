package com.example.hotelmanagementsystem.receptionist.Strategy;

import com.example.hotelmanagementsystem.DataBaseConnection;
import com.example.hotelmanagementsystem.manager.Classes.Rooms.Rooms;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Room Data Access Object (DAO)
public class RoomDAO {
    private static final String DB_URL = DataBaseConnection.getDatabaseUrl();

    // Fetch all available rooms by type and boarding option
    public static List<Rooms> getAvailableRooms(String type, String boardingOption) throws SQLException {
        List<Rooms> rooms = new ArrayList<>();
        String query = "SELECT * FROM Rooms WHERE status = 'Available'";

        if (!type.equals("All")) {
            query += " AND type = ?";
        }
        if (!boardingOption.equals("All")) {
            query += " AND boardingOption = ?";
        }

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            int index = 1;
            if (!type.equals("All")) {
                pstmt.setString(index++, type);
            }
            if (!boardingOption.equals("All")) {
                pstmt.setString(index, boardingOption);
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rooms.add(new Rooms(
                        rs.getInt("roomID"),
                        rs.getString("type"),
                        rs.getDouble("price"),
                        rs.getString("status")
                ) {
                });
            }
        }
        return rooms;
    }

    // Update room status
    public static void updateRoomStatus(int roomID, String status) throws SQLException {
        String query = "UPDATE Rooms SET status = ? WHERE roomID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, roomID);
            pstmt.executeUpdate();
        }
    }
}
