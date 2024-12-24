package com.example.hotelmanagementsystem.receptionist;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class ResidentController {

    @FXML
    private TableView<Resident> residentTableView;
    @FXML
    private TableColumn<Resident, Integer> idColumn;
    @FXML
    private TableColumn<Resident, String> nameColumn, roomIDColumn, checkInDateColumn, checkOutDateColumn;
    @FXML
    private TableColumn<Resident, Boolean> hasSaunaColumn, hasSpaHammamColumn, hasMassageColumn, hasPrivateViewColumn;

    private static final String dbUrl = "jdbc:sqlite:db/data.db";
    private ObservableList<Resident> residentList = FXCollections.observableArrayList();

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
                        resultSet.getBoolean("hasPrivateView")
                );
                residentList.add(resident);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
        residentTableView.setItems(residentList);
    }

    @FXML
    private void handleAdd() {
        ResidentDialog dialog = new ResidentDialog();
        Optional<Resident> result = dialog.showAndWait();
        result.ifPresent(newResident -> {
            addOrUpdateResidentInDatabase(newResident);
            loadResidents();
        });
    }

    @FXML
    private void handleEdit() {
        Resident selectedResident = residentTableView.getSelectionModel().getSelectedItem();
        if (selectedResident == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a resident to edit.");
            return;
        }
        ResidentDialog dialog = new ResidentDialog(selectedResident);
        Optional<Resident> result = dialog.showAndWait();
        result.ifPresent(updatedResident -> {
            updatedResident.setId(selectedResident.getId());
            addOrUpdateResidentInDatabase(updatedResident);
            loadResidents();
        });
    }

    @FXML
    private void handleDelete() {
        Resident selectedResident = residentTableView.getSelectionModel().getSelectedItem();
        if (selectedResident == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a resident to delete.");
            return;
        }
        deleteResidentFromDatabase(selectedResident.getId());
        loadResidents();
    }

    private void addOrUpdateResidentInDatabase(Resident resident) {
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            String checkQuery = "SELECT residentID FROM Residents WHERE residentID = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setInt(1, resident.getId());
                ResultSet resultSet = checkStatement.executeQuery();

                if (resultSet.next()) {
                    // Update record
                    String updateQuery = "UPDATE Residents SET name = ?, roomID = ?, checkInDate = ?, checkOutDate = ?, hasSauna = ?, hasSpaHammam = ?, hasMassage = ?, hasPrivateView = ?, totalCost = ? WHERE residentID = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setString(1, resident.getName());
                        updateStatement.setString(2, resident.getRoomID());
                        updateStatement.setString(3, resident.getCheckInDate());
                        updateStatement.setString(4, resident.getCheckOutDate());
                        updateStatement.setBoolean(5, resident.isHasSauna());
                        updateStatement.setBoolean(6, resident.isHasSpaHammam());
                        updateStatement.setBoolean(7, resident.isHasMassage());
                        updateStatement.setBoolean(8, resident.isHasPrivateView());
                        updateStatement.setDouble(9, resident.getTotalCost());
                        updateStatement.setInt(10, resident.getId());
                        updateStatement.executeUpdate();
                    }
                } else {
                    // Insert new record
                    String insertQuery = "INSERT INTO Residents (name, roomID, checkInDate, checkOutDate, hasSauna, hasSpaHammam, hasMassage, hasPrivateView, totalCost) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setString(1, resident.getName());
                        insertStatement.setString(2, resident.getRoomID());
                        insertStatement.setString(3, resident.getCheckInDate());
                        insertStatement.setString(4, resident.getCheckOutDate());
                        insertStatement.setBoolean(5, resident.isHasSauna());
                        insertStatement.setBoolean(6, resident.isHasSpaHammam());
                        insertStatement.setBoolean(7, resident.isHasMassage());
                        insertStatement.setBoolean(8, resident.isHasPrivateView());
                        insertStatement.setDouble(9, resident.getTotalCost());
                        insertStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    private void deleteResidentFromDatabase(int residentID) {
        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Residents WHERE residentID = ?")) {
            statement.setInt(1, residentID);
            statement.executeUpdate();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
