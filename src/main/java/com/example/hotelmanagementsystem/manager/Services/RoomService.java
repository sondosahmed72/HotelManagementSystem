package com.example.hotelmanagementsystem.manager.Services;
import com.example.hotelmanagementsystem.DataBaseConnection;
import com.example.hotelmanagementsystem.manager.Classes.Rooms.RoomFactory;
import com.example.hotelmanagementsystem.manager.Classes.Rooms.Rooms;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomService {
    public static List<Rooms> getAllRooms() {
        List<Rooms> rooms = new ArrayList<>();
        String query = "SELECT * FROM Rooms";
        try (Connection connection = DataBaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int roomID = resultSet.getInt("roomID");
                String type = resultSet.getString("type");
                double price = resultSet.getDouble("price");
                String status = resultSet.getString("status");

                Rooms room = RoomFactory.createRoom(roomID, type, price, status);
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    // Add new room to the database
    public static void addRoom(Rooms newRoom) throws SQLException {
        String query = "INSERT INTO Rooms (type, price, status) VALUES (?, ?, ?)";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, newRoom.getType());
            statement.setDouble(2, newRoom.getPrice());
            statement.setString(3, newRoom.getStatus());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to insert room, no rows affected.");
            }
        } catch (SQLException e) {
            throw new SQLException("Error adding new room to the database: " + e.getMessage(), e);
        }
    }

}