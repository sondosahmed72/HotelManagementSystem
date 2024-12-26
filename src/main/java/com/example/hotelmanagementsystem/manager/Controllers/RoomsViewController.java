package com.example.hotelmanagementsystem.manager.Controllers;
import com.example.hotelmanagementsystem.manager.Classes.Rooms.RoomManagerDialog;
import com.example.hotelmanagementsystem.manager.Services.RoomService;
import com.example.hotelmanagementsystem.manager.Classes.Rooms.Rooms;
//import com.example.hotelmanagementsystem.receptionist..Resident;
//import com.example.hotelmanagementsystem.receptionist.ResidentDialog;
//import com.example.hotelmanagementsystem.receptionist.RoomDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.Optional;

public class RoomsViewController {

    @FXML
    private TableView<Rooms> roomsTable;

    @FXML
    private TableColumn<Rooms, String> roomIdColumn;

    @FXML
    private TableColumn<Rooms, String> typeColumn;

    @FXML
    private TableColumn<Rooms, Double> priceColumn;

    @FXML
    private TableColumn<Rooms, String> statusColumn;

    public void initialize() {
        loadRooms();
        roomIdColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        ObservableList<Rooms> rooms = FXCollections.observableArrayList(RoomService.getAllRooms());
        if (rooms != null && !rooms.isEmpty()) {
            roomsTable.setItems(rooms);
        } else {
            System.out.println("No rooms available to display.");
        }
           System.out.println("Fetched rooms: " + rooms.size());
        // Print each room's details
        for (Rooms room : rooms) {
            System.out.println("Room ID: " + room.getRoomID() + ", Type: " + room.getType() + ", Price: " + room.getPrice() + ", Status: " + room.getStatus());
        }
        roomsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    @FXML
    private void handleAddRoom() {
        RoomManagerDialog dialog = new RoomManagerDialog();
        Optional<Rooms> result = dialog.showAndWait();
        result.ifPresent(newRoom -> {
            // Add the new room to the database
            addRoomToDatabase(newRoom);
            // After adding the room, update the available rooms display
            loadRooms(); // This method would refresh the displayed list of rooms in the UI
        });
    }
    private void addRoomToDatabase(Rooms newRoom) {
        try {
            // Add the new room to the database
            RoomService.addRoom(newRoom);
        } catch (SQLException e) {
            showError("Database Error", e.getMessage());
        }
    }
    private void loadRooms() {
        ObservableList<Rooms> rooms = FXCollections.observableArrayList(RoomService.getAllRooms());
        if (rooms != null && !rooms.isEmpty()) {
            roomsTable.setItems(rooms);
        } else {
            System.out.println("No rooms available to display.");
        }
    }

    // Method to show error messages
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
