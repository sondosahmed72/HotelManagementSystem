package com.example.hotelmanagementsystem.manager.Classes.Rooms;

import com.example.hotelmanagementsystem.receptionist.Resident;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;

public class RoomManagerDialog extends Dialog<Rooms> {

    private TextField priceField;
    private ComboBox<String> roomTypeComboBox;

    private Label statusLabel;
    public RoomManagerDialog() {
        initDialog();
    }

    private void initDialog() {
        // Add CSS stylesheet
        getDialogPane().getStylesheets().add(getClass().getResource("/com/example/hotelmanagementsystem/Style/RoomManagerStyle.css").toExternalForm());


        priceField = new TextField();
        roomTypeComboBox = new ComboBox<>();
        statusLabel = new Label("Available");

        // Populate room type and status options
        roomTypeComboBox.getItems().addAll("Single", "Double", "Triple");
        roomTypeComboBox.setValue("Single"); // default value



        // Set style classes
        priceField.getStyleClass().add("dialog-text-field");

        // Layout for dialog
        VBox vbox = new VBox(10);
        vbox.setPadding(new javafx.geometry.Insets(10));
        vbox.setAlignment(Pos.CENTER_LEFT);

        // Add components
        vbox.getChildren().add(new Label("Room Type:"));
        vbox.getChildren().add(roomTypeComboBox);

        vbox.getChildren().add(new Label("Price:"));
        vbox.getChildren().add(priceField);

        vbox.getChildren().add(new Label("Status:"));
        vbox.getChildren().add(statusLabel);

        getDialogPane().setContent(vbox);

        // Buttons
        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().addAll(saveButton, cancelButton);

        // Customize the ResultConverter with validation
        setResultConverter(dialogButton -> {
            if (dialogButton == saveButton) {
                // Validate fields
                if (priceField.getText().isEmpty()) {
                    showError("Please enter a price.");
                    return null; // Prevent closing the dialog
                }
                // If all essential fields are valid, return the Resident object
                return  RoomFactory.createRoom(0, roomTypeComboBox.getValue(),
                        Double.parseDouble(priceField.getText()), "Available");
            }
            return null;
        });
    }

    // Method to show error messages
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
