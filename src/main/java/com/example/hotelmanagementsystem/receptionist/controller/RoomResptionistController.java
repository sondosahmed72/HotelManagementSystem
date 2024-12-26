package com.example.hotelmanagementsystem.receptionist.controller;

import com.example.hotelmanagementsystem.DataBaseConnection;
import com.example.hotelmanagementsystem.manager.Classes.Rooms.Rooms;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class RoomResptionistController {

    @FXML
    private TableView<Rooms> roomTableView;

    @FXML
    private TableColumn<Rooms, Integer> roomIDColumn;

    @FXML
    private TableColumn<Rooms, String> typeColumn;

    @FXML
    private TableColumn<Rooms, Double> priceColumn;

    @FXML
    private TableColumn<Rooms, String> statusColumn;

    private ObservableList<Rooms> roomList = FXCollections.observableArrayList();

    private static final String dbUrl = DataBaseConnection.getDatabaseUrl();

    @FXML
    public void initialize() {
        // Setup columns
        roomIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        roomTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Load rooms from database
        loadRooms();
    }

    private void loadRooms() {
        roomList.clear();
        try (Connection connection = DriverManager.getConnection(dbUrl);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Rooms")) {

            while (resultSet.next()) {
                Rooms room = new Rooms(
                        resultSet.getInt("roomID"),
                        resultSet.getString("type"),
                        resultSet.getDouble("price"),
                        resultSet.getString("status")
                ) {
                    @Override
                    public int getRoomID() {
                        return super.getRoomID();
                    }
                };
                roomList.add(room);
            }

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }

        roomTableView.setItems(roomList);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

