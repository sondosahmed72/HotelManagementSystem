package com.example.hotelmanagementsystem.manager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class WorkerFormController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField contactField;

    @FXML
    private TextField salaryField;

    @FXML
    private TextField jobTitleField;

    @FXML
    private TableView<Object> workerTable;

    @FXML
    private TableColumn<Worker, String> nameColumn;

    @FXML
    private TableColumn<Worker, String> contactColumn;

    @FXML
    private TableColumn<Worker, Double> salaryColumn;

    @FXML
    private TableColumn<Worker, String> jobTitleColumn;

    @FXML
    private Button submitButton;

    private final ObservableList<Object> workersList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize TableView columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        jobTitleColumn.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));

        workerTable.setItems(workersList);

        // Handle submit button action
        submitButton.setOnAction(e -> addWorker());
    }

    private void addWorker() {
        String name = nameField.getText();
        String contactText = contactField.getText();
        String salaryText = salaryField.getText();
        String jobTitle = jobTitleField.getText();

        if (name.isEmpty() || contactText.isEmpty() || salaryText.isEmpty() || jobTitle.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill all fields.");
            return;
        }

        try {
            double salary = Double.parseDouble(salaryText);
            int contact = Integer.parseInt(contactText);


            Worker newWorker = new Receptionist(name, contact, "defaultPassword", "defaultUsername");
            newWorker.setJobTitle(jobTitle);
            newWorker.setWorkerId(workerTable.getItems().size() + 1);
            workerTable.getItems().add(newWorker);
            showAlert(Alert.AlertType.INFORMATION, "Worker Added", "Worker added successfully!");
            clearFields();
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Salary must be a number.");
        }
    }

    private void clearFields() {
        nameField.clear();
        contactField.clear();
        salaryField.clear();
        jobTitleField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

