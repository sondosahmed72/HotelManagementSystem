package com.example.hotelmanagementsystem.manager.Controllers;

import com.example.hotelmanagementsystem.DataBaseConnection;
import com.example.hotelmanagementsystem.receptionist.Models.Resident;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class ResidentViewController {
    @FXML
    private TableView<Resident> residentTableView;
    @FXML
    private TableColumn<Resident, Integer> idColumn;
    @FXML
    private TableColumn<Resident, String> nameColumn, roomIDColumn, checkInDateColumn, checkOutDateColumn;
    @FXML
    private TableColumn<Resident, Boolean> hasSaunaColumn, hasSpaHammamColumn, hasMassageColumn, hasPrivateViewColumn;

    private static final String dbUrl = DataBaseConnection.getDatabaseUrl();
    private ObservableList<Resident> residentList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Resident, String> boardingOptionColumn;
    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        roomIDColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        checkInDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        checkOutDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        hasSaunaColumn.setCellValueFactory(new PropertyValueFactory<>("hasSauna"));
        hasSpaHammamColumn.setCellValueFactory(new PropertyValueFactory<>("hasSpaHammam"));
        hasMassageColumn.setCellValueFactory(new PropertyValueFactory<>("hasMassage"));
        hasPrivateViewColumn.setCellValueFactory(new PropertyValueFactory<>("hasPrivateView"));

        boardingOptionColumn.setCellValueFactory(new PropertyValueFactory<>("boardingOption"));
        residentTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        loadResidents();
    }

    private void loadResidents() {
        residentList.clear();
        try (Connection connection = DriverManager.getConnection(dbUrl);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Residents")) {
            while (resultSet.next()) {
                Resident resident = new Resident(
                        resultSet.getInt("residentID"),
                        resultSet.getString("name"),
                        resultSet.getString("roomID"),
                        resultSet.getString("checkInDate"),
                        resultSet.getString("checkOutDate"),
                        resultSet.getBoolean("hasSauna"),
                        resultSet.getBoolean("hasSpaHammam"),
                        resultSet.getBoolean("hasMassage"),
                        resultSet.getBoolean("hasPrivateView"),
                        resultSet.getString("boardingOption") // استرجاع البوردينج
                );
                residentList.add(resident);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
        residentTableView.setItems(residentList);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}