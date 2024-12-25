package com.example.hotelmanagementsystem.manager.Services;
import com.example.hotelmanagementsystem.DataBaseConnection;
import com.example.hotelmanagementsystem.manager.Classes.Rooms.RoomFactory;
import com.example.hotelmanagementsystem.manager.Classes.Rooms.Rooms;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomService {
    public static List<Rooms> getAllRooms(){
        List<Rooms> rooms = new ArrayList<>();
        try {
            Connection connection = DataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Rooms";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int roomID = Integer.parseInt(resultSet.getString("roomID"));
                String type = resultSet.getString("type");
                double price = resultSet.getDouble("price");
                String status = resultSet.getString("status");

                // Create a room using the factory, passing the roomId and other values
                Rooms room = RoomFactory.createRoom(roomID, type, price, status);
                rooms.add(room);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return rooms;

    }
    // Add new room to the database
    public static void addRoom(Rooms newRoom) throws SQLException {
        String query = "INSERT INTO Rooms (type, price, status) VALUES (?, ?, ?)";

        try (
                Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set the parameters for the prepared statement
            statement.setInt(1, newRoom.getRoomID());
            statement.setString(2, newRoom.getType());
            statement.setDouble(3, newRoom.getPrice());
            statement.setString(4, newRoom.getStatus());

            // Execute the update to insert the new room into the database
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error adding new room to the database", e);
        }
    }
}
