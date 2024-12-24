package com.example.hotelmanagementsystem.receptionist;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.LocalDate;

public class ResidentDialog extends Dialog<Resident> {
    private TextField nameField;
    private TextField roomIDField;
    private DatePicker checkInDatePicker;
    private DatePicker checkOutDatePicker;
    private CheckBox saunaCheckBox;
    private CheckBox spaHammamCheckBox;
    private CheckBox massageCheckBox;
    private CheckBox privateViewCheckBox;
    private Service service;

    // Constructor used for editing an existing resident
    public ResidentDialog(Resident resident) {
        this();  // Call the default constructor to initialize the UI components

        // Now that the UI components are initialized, set their values
        nameField.setText(resident.getName());
        roomIDField.setText(resident.getRoomID());
        checkInDatePicker.setValue(LocalDate.parse(resident.getCheckInDate()));
        checkOutDatePicker.setValue(LocalDate.parse(resident.getCheckOutDate()));
        saunaCheckBox.setSelected(resident.isHasSauna());
        spaHammamCheckBox.setSelected(resident.isHasSpaHammam());
        massageCheckBox.setSelected(resident.isHasMassage());
        privateViewCheckBox.setSelected(resident.isHasPrivateView());
    }

    // Default constructor
    public ResidentDialog() {
        this.service = new BasicService();
        nameField = new TextField();
        roomIDField = new TextField();
        checkInDatePicker = new DatePicker();
        checkOutDatePicker = new DatePicker();
        saunaCheckBox = new CheckBox("Sauna");
        spaHammamCheckBox = new CheckBox("Spa/Hammam");
        massageCheckBox = new CheckBox("Massage");
        privateViewCheckBox = new CheckBox("Private View Room");

        // Adding labels next to fields
        Label nameLabel = new Label("Resident Name:");
        Label roomIDLabel = new Label("Room ID:");
        Label checkInDateLabel = new Label("Check-in Date:");
        Label checkOutDateLabel = new Label("Check-out Date:");

        // Set placeholders for fields
        nameField.setPromptText("Enter resident name");
        roomIDField.setPromptText("Enter room ID");
        checkInDatePicker.setPromptText("Select check-in date");
        checkOutDatePicker.setPromptText("Select check-out date");

        // Layout grid setup
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Adding elements to the grid
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(roomIDLabel, 0, 1);
        grid.add(roomIDField, 1, 1);
        grid.add(checkInDateLabel, 0, 2);
        grid.add(checkInDatePicker, 1, 2);
        grid.add(checkOutDateLabel, 0, 3);
        grid.add(checkOutDatePicker, 1, 3);
        grid.add(saunaCheckBox, 0, 4, 2, 1);
        grid.add(spaHammamCheckBox, 0, 5, 2, 1);
        grid.add(massageCheckBox, 0, 6, 2, 1);
        grid.add(privateViewCheckBox, 0, 7, 2, 1);

        // Set content for the dialog
        this.getDialogPane().setContent(grid);

        // Adding buttons to the dialog
        ButtonType okButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);

        // Set result converter for dialog
        this.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new Resident(0, nameField.getText(), roomIDField.getText(),
                        checkInDatePicker.getValue().toString(), checkOutDatePicker.getValue().toString(),
                        saunaCheckBox.isSelected(), spaHammamCheckBox.isSelected(),
                        massageCheckBox.isSelected(), privateViewCheckBox.isSelected());
            }
            return null;
        });

        // Adding listeners to update service costs
        saunaCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> updateService());
        spaHammamCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> updateService());
        massageCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> updateService());
        privateViewCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> updateService());
    }

    private void updateService() {
        service = new BasicService();
        if (saunaCheckBox.isSelected()) {
            service = new SaunaDecorator(service);
        }
        if (spaHammamCheckBox.isSelected()) {
            service = new SpaHammamDecorator(service);
        }
        if (massageCheckBox.isSelected()) {
            service = new MassageDecorator(service);
        }
        if (privateViewCheckBox.isSelected()) {
            service = new PrivateViewDecorator(service);
        }

        double totalCost = service.getCost();
        showAlert(Alert.AlertType.INFORMATION, "Total Service Cost", "The total cost is: " + totalCost);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
