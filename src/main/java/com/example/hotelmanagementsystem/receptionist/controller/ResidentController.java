package com.example.hotelmanagementsystem.receptionist.controller;

import com.example.hotelmanagementsystem.DataBaseConnection;
import com.example.hotelmanagementsystem.manager.Classes.Rooms.Rooms;
import com.example.hotelmanagementsystem.receptionist.Facade.CheckoutCostFacade;
import com.example.hotelmanagementsystem.receptionist.Models.Resident;
import com.example.hotelmanagementsystem.receptionist.Strategy.RoomDAO;
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

    private static final String dbUrl = DataBaseConnection.getDatabaseUrl();
    private ObservableList<Resident> residentList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Resident, String> boardingOptionColumn;
    @FXML
    private TableColumn<Resident, Double> totalCostColumn;

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
//        totalCostColumn.setCellValueFactory(new PropertyValueFactory<>("totalCost")); // ربط العمود

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
                        resultSet.getString("boardingOption")
                );
                residentList.add(resident);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
        residentTableView.setItems(residentList);
    }


    private ObservableList<Rooms> roomList = FXCollections.observableArrayList();

    public ObservableList<Rooms> getRoomList() {
        return roomList;
    }


    // Load available rooms function
    public  void loadAvailableRooms() {
        roomList.clear(); // Clear previous data
        try (Connection connection = DriverManager.getConnection(dbUrl);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Rooms WHERE status = 'Available'")) {

            while (resultSet.next()) {
                // Convert string from database to BoardingOption enum
                //BoardingOption boardingOption = BoardingOption.valueOf(resultSet.getString("boardingOption").toUpperCase());

                // Create a Room object
                Rooms room = new Rooms(
                        resultSet.getInt("roomID"),          // Room ID
                        resultSet.getString("type"),         // Room Type
                        resultSet.getDouble("price"),        // Price
                        resultSet.getString("status")         // Converted BoardingOption
                ) {
                };
                roomList.add(room); // Add each room to the list
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Data", "Invalid boarding option in database.");
        }
    }



    @FXML
    private void handleAdd() {
        ResidentDialog dialog = new ResidentDialog();
        Optional<Resident> result = dialog.showAndWait();
        result.ifPresent(newResident -> {
            // إضافة أو تحديث المقيم في قاعدة البيانات
            addOrUpdateResidentInDatabase(newResident);

            // بعد إضافة المقيم، تحديث حالة الغرفة إلى "Occupied"
            try {
                RoomDAO.updateRoomStatus(Integer.parseInt(newResident.getRoomID()), "Occupied");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
            }

            loadResidents();
            loadAvailableRooms();
        });
    }


    @FXML
    private void handleEdit() {
        Resident selectedResident = residentTableView.getSelectionModel().getSelectedItem();
        if (selectedResident == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a resident to edit.");
            return;
        }

        // حفظ الغرفة القديمة (قبل التعديل)
        String oldRoomID = selectedResident.getRoomID();

        ResidentDialog dialog = new ResidentDialog(selectedResident);
        Optional<Resident> result = dialog.showAndWait();
        result.ifPresent(updatedResident -> {
            // قبل تحديث المقيم، نحدث حالة الغرفة القديمة
            try {
                RoomDAO.updateRoomStatus(Integer.parseInt(oldRoomID), "Available");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
            }

            // بعد تحديث المقيم، نحدث حالة الغرفة الجديدة
            String newRoomID = updatedResident.getRoomID();
            try {
                RoomDAO.updateRoomStatus(Integer.parseInt(newRoomID), "Occupied");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
            }

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

        // بعد الحذف، تحديث حالة الغرفة إلى 'Available'
        try {
            RoomDAO.updateRoomStatus(Integer.parseInt(selectedResident.getRoomID()), "Available");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }

        loadResidents();
        loadAvailableRooms();
    }


    private void addOrUpdateResidentInDatabase(Resident resident) {
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            String checkQuery = "SELECT residentID FROM Residents WHERE residentID = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setInt(1, resident.getId());
                ResultSet resultSet = checkStatement.executeQuery();

                if (resultSet.next()) {
                    // Update record
                    String updateQuery = "UPDATE Residents SET name = ?, roomID = ?, checkInDate = ?, checkOutDate = ?, hasSauna = ?, hasSpaHammam = ?, hasMassage = ?, hasPrivateView = ?, boardingOption = ?, totalCost = ? WHERE residentID = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setString(1, resident.getName());
                        updateStatement.setString(2, resident.getRoomID());
                        updateStatement.setString(3, resident.getCheckInDate());
                        updateStatement.setString(4, resident.getCheckOutDate());
                        updateStatement.setBoolean(5, resident.isHasSauna());
                        updateStatement.setBoolean(6, resident.isHasSpaHammam());
                        updateStatement.setBoolean(7, resident.isHasMassage());
                        updateStatement.setBoolean(8, resident.isHasPrivateView());
                        updateStatement.setString(9, resident.getBoardingOption());  // تأكد من تحديث قيمة boardingOption
                        updateStatement.setDouble(10, resident.getTotalCost());
                        updateStatement.setInt(11, resident.getId());
                        updateStatement.executeUpdate();
                    }
                } else {
                    // Insert new record
                    String insertQuery = "INSERT INTO Residents (name, roomID, checkInDate, checkOutDate, hasSauna, hasSpaHammam, hasMassage, hasPrivateView, boardingOption, totalCost) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setString(1, resident.getName());
                        insertStatement.setString(2, resident.getRoomID());
                        insertStatement.setString(3, resident.getCheckInDate());
                        insertStatement.setString(4, resident.getCheckOutDate());
                        insertStatement.setBoolean(5, resident.isHasSauna());
                        insertStatement.setBoolean(6, resident.isHasSpaHammam());
                        insertStatement.setBoolean(7, resident.isHasMassage());
                        insertStatement.setBoolean(8, resident.isHasPrivateView());
                        insertStatement.setString(9, resident.getBoardingOption());  // تأكد من إضافة boardingOption
                        insertStatement.setDouble(10, resident.getTotalCost());
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
    @FXML
    private void handleCost() {

        Resident selectedResident = residentTableView.getSelectionModel().getSelectedItem();

        if (selectedResident == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a resident to calculate the cost.");
            return;
        }

        CheckoutCostFacade checkoutCostFacade = new CheckoutCostFacade();

        checkoutCostFacade.processAndDisplayResidentDetails(selectedResident.getId());

    }
    private String showOptionDialog() {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Select Option");
        dialog.setHeaderText("Choose Option");
        dialog.setContentText("Enter 'single' to calculate for a single resident or 'all' to calculate for all residents:");

        Optional<String> result = dialog.showAndWait();
        return result.orElse("");
    }
}