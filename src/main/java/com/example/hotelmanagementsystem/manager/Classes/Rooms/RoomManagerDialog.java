package com.example.hotelmanagementsystem.manager.Classes.Rooms;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;

public class RoomManagerDialog extends Dialog<Rooms> {
    private TextField priceField;
    private ComboBox<String> roomTypeComboBox;
    private Label statusLabel;
    private Button saveButton;

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
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().addAll(saveButtonType, cancelButtonType);

        saveButton = (Button) getDialogPane().lookupButton(saveButtonType);

        // Disable the Save button initially
        saveButton.setDisable(true);

        // Add a listener to the priceField to validate input in real-time
        priceField.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(!isValidPrice(newValue));
        });

        // Customize the ResultConverter with validation
        setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                double price = Double.parseDouble(priceField.getText());
                return RoomFactory.createRoom(0, roomTypeComboBox.getValue(), price, "Available");
            }
            return null;
        });
    }

    // Method to validate price input
    private boolean isValidPrice(String input) {
        if (input.isEmpty()) return false;

        try {
            double price = Double.parseDouble(input);
            return price > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Method to show error messages
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
