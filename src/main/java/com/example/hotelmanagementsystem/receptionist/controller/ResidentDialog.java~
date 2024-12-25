package com.example.hotelmanagementsystem.receptionist.controller;

import com.example.hotelmanagementsystem.receptionist.Models.Resident;
import com.example.hotelmanagementsystem.receptionist.Models.Room;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;

import java.time.LocalDate;
import java.util.Optional;

public class ResidentDialog extends Dialog<Resident> {

    private TextField nameField;
    private Label selectedRoomLabel;
    private DatePicker checkInDatePicker;
    private DatePicker checkOutDatePicker;
    private CheckBox saunaCheckBox;
    private CheckBox spaHammamCheckBox;
    private CheckBox massageCheckBox;
    private CheckBox privateViewCheckBox;
    private ComboBox<String> boardingOptionComboBox;  // ComboBox لإختيار البوردينج

    // القيمة الافتراضية
    private String selectedRoomID; // Tracks assigned room ID
    private String selectedRoomType; // Tracks selected room type
    private String selectedBoardingOption; // Tracks selected boarding option

    public ResidentDialog() {
        initDialog();
    }

    public ResidentDialog(Resident resident) {
        this();
        populateFields(resident); // Fill fields for editing
    }

    // Initialize Dialog Components
    private void initDialog() {
        // Add CSS stylesheet
        getDialogPane().getStylesheets().add(getClass().getResource("/com/example/hotelmanagementsystem/styles.css").toExternalForm());

        nameField = new TextField();
        selectedRoomLabel = new Label("No Room Selected");
        checkInDatePicker = new DatePicker();
        checkOutDatePicker = new DatePicker();
        saunaCheckBox = new CheckBox("Sauna");
        spaHammamCheckBox = new CheckBox("Spa/Hammam");
        massageCheckBox = new CheckBox("Massage");
        privateViewCheckBox = new CheckBox("Private View Room");

        // ComboBox لإختيار البوردينج
        boardingOptionComboBox = new ComboBox<>();
        boardingOptionComboBox.getItems().addAll("Full board", "Half board", "Bed and Breakfast");
        boardingOptionComboBox.setValue("Full board");  // القيمة الافتراضية

        // Set style classes
        nameField.getStyleClass().add("dialog-text-field");
        checkInDatePicker.getStyleClass().add("dialog-date-picker");

        // Create assignRoomButton
        Button assignRoomButton = new Button("Assign Room");
        assignRoomButton.setOnAction(e -> openRoomSelectionDialog());
        assignRoomButton.getStyleClass().add("dialog-button"); // Apply style

        saunaCheckBox.getStyleClass().add("dialog-checkbox");
        spaHammamCheckBox.getStyleClass().add("dialog-checkbox");
        massageCheckBox.getStyleClass().add("dialog-checkbox");
        privateViewCheckBox.getStyleClass().add("dialog-checkbox");

        // Layout for dialog
        VBox vbox = new VBox(10);
        vbox.setPadding(new javafx.geometry.Insets(10));
        vbox.setAlignment(Pos.CENTER_LEFT);

        // Add components
        vbox.getChildren().add(new Label("Name:"));
        vbox.getChildren().add(nameField);

        vbox.getChildren().add(new Label("Room:"));
        HBox roomBox = new HBox(10);
        roomBox.setAlignment(Pos.CENTER_LEFT);
        roomBox.getChildren().add(selectedRoomLabel);
        roomBox.getChildren().add(assignRoomButton);  // Add assignRoomButton here
        vbox.getChildren().add(roomBox);

        vbox.getChildren().add(new Label("Check-In Date:"));
        vbox.getChildren().add(checkInDatePicker);

        vbox.getChildren().add(new Label("Check-Out Date:"));
        vbox.getChildren().add(checkOutDatePicker);

        vbox.getChildren().add(saunaCheckBox);
        vbox.getChildren().add(spaHammamCheckBox);
        vbox.getChildren().add(massageCheckBox);
        vbox.getChildren().add(privateViewCheckBox);

        // Add Boarding Option ComboBox to the layout
        vbox.getChildren().add(new Label("Boarding Option:"));
        vbox.getChildren().add(boardingOptionComboBox);

        getDialogPane().setContent(vbox);

        // Buttons
        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().addAll(saveButton, cancelButton);

        // Customize the ResultConverter with validation
        setResultConverter(dialogButton -> {
            if (dialogButton == saveButton) {
                // Validate essential fields (name, check-in, check-out, room)
                if (nameField.getText().isEmpty()) {
                    showError("Please enter a name.");
                    return null; // Prevent closing the dialog
                }
                if (checkInDatePicker.getValue() == null) {
                    showError("Please select a check-in date.");
                    return null; // Prevent closing the dialog
                }
                if (checkOutDatePicker.getValue() == null) {
                    showError("Please select a check-out date.");
                    return null; // Prevent closing the dialog
                }
                if (selectedRoomID == null || selectedRoomID.isEmpty()) {
                    showError("Please assign a room.");
                    return null; // Prevent closing the dialog
                }

                // If all essential fields are valid, return the Resident object
                return new Resident(0, nameField.getText(), selectedRoomID,
                        checkInDatePicker.getValue().toString(), checkOutDatePicker.getValue().toString(),
                        saunaCheckBox.isSelected(), spaHammamCheckBox.isSelected(),
                        massageCheckBox.isSelected(), privateViewCheckBox.isSelected(),
                        boardingOptionComboBox.getValue()); // Include boarding option
            }
            return null;
        });
    }

    // Populate fields for editing resident
    private void populateFields(Resident resident) {
        nameField.setText(resident.getName());
        checkInDatePicker.setValue(LocalDate.parse(resident.getCheckInDate()));
        checkOutDatePicker.setValue(LocalDate.parse(resident.getCheckOutDate()));

        selectedRoomID = resident.getRoomID();
        selectedRoomLabel.setText("Room ID: " + resident.getRoomID() + " (" + selectedRoomType + " - " + selectedBoardingOption + ")");
        saunaCheckBox.setSelected(resident.isHasSauna());
        spaHammamCheckBox.setSelected(resident.isHasSpaHammam());
        massageCheckBox.setSelected(resident.isHasMassage());
        privateViewCheckBox.setSelected(resident.isHasPrivateView());

        // Set boarding option for editing
        boardingOptionComboBox.setValue(resident.getBoardingOption());
    }

    // Room Selection Dialog
    private void openRoomSelectionDialog() {
        // استدعاء الكنترولر لتحميل الغرف المتاحة
        ResidentController controller = new ResidentController();
        controller.loadAvailableRooms(); // تحميل البيانات

        Dialog<Room> roomDialog = new Dialog<>();
        roomDialog.setTitle("Available Rooms");

        ListView<Room> roomListView = new ListView<>();
        roomListView.setItems(controller.getRoomList()); // عرض الغرف المتاحة في الليست

        // تخصيص طريقة عرض العناصر في ListView
        roomListView.setCellFactory(param -> new ListCell<Room>() {
            @Override
            protected void updateItem(Room item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    // عرض معلومات الغرفة بشكل مرتب
                    setText("Room ID: " + item.getId() + " (" + item.getType()  + ")");
                    setStyle("-fx-background-color: #f0f0f0; -fx-padding: 5px;"); // إضافة لون خلفية وتباعد للخلايا
                }
            }
        });

        roomDialog.getDialogPane().setContent(roomListView);

        // زر لتحديد الغرفة
        ButtonType assignButton = new ButtonType("Assign", ButtonBar.ButtonData.OK_DONE);
        roomDialog.getDialogPane().getButtonTypes().addAll(assignButton, ButtonType.CANCEL);

        roomDialog.setResultConverter(dialogButton -> {
            if (dialogButton == assignButton) {
                return roomListView.getSelectionModel().getSelectedItem();
            }
            return null;
        });

        Optional<Room> result = roomDialog.showAndWait();
        result.ifPresent(selectedRoom -> {
            selectedRoomID = String.valueOf(selectedRoom.getId());
            selectedRoomLabel.setText("Room: " + selectedRoomID + " (" + selectedRoom.getType() +  ")");
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
