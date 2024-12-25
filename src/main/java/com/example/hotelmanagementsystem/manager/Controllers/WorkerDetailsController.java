package com.example.hotelmanagementsystem.manager.Controllers;

import com.example.hotelmanagementsystem.manager.Classes.Workers.Receptionist;
import com.example.hotelmanagementsystem.manager.Classes.Workers.Worker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

public class WorkerDetailsController {
    @FXML
    private VBox detailsPane;

    @FXML
    private Label detailsName;

    @FXML
    private Label detailsContact;

    @FXML
    private Label detailsSalary;

    @FXML
    private Label detailsJobTitle;

    @FXML
    private Label detailsUsername;

    @FXML
    private Label detailsPassword;
    @FXML
    private ComboBox<String> workerTypeDropdown; // Dropdown for worker type

    @FXML
    private ComboBox<String> jobRoleDropdown; // Dropdown for job roles
    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;
    private TextField nameField;

    @FXML
    private TextField contactField;

    @FXML
    private TextField salaryField;
    @FXML
    private TextField jobTitleField;
    @FXML
    private Button submitButton;
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;
    @FXML
    private Label detailsRole;


    private Worker selectedWorker;
    private final WorkerCRUD workerCRUD = new WorkerCRUD();
    private final ObservableList<Object> workersList = FXCollections.observableArrayList();

    public void setWorkerDetails(Worker worker) {
        detailsRole.setText("Role : " + worker.getRole()); // Display role
        this.selectedWorker = worker;
        detailsName.setText("Name: " + worker.getName());
        detailsContact.setText("Contact Information: " + worker.getContact());
        detailsSalary.setText("Salary: " + worker.getSalary());
        detailsJobTitle.setText("Job Title: " + worker.getJobTitle());

        if (worker instanceof Receptionist) {
            detailsUsername.setText("Username: " + ((Receptionist) worker).getUsername());
            detailsPassword.setText("Password: " + ((Receptionist) worker).getPassword());
            detailsUsername.setVisible(true);
            detailsPassword.setVisible(true);
        } else {
            detailsUsername.setVisible(false);
            detailsPassword.setVisible(false);
        }
    }

    @FXML
    private void editWorker() {
        if (selectedWorker != null) {
            // Close the popup window
            Stage stage = (Stage) editButton.getScene().getWindow();
            stage.close();

            // Fill the main form with the selected worker's data
            if (mainController != null) {
                mainController.fillFormWithWorkerData(selectedWorker);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Main controller is not set.");
            }

        }
    }

    private WorkerFormController mainController;

    public void setMainController(WorkerFormController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void deleteWorker() {
        if (selectedWorker != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this worker?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        workerCRUD.deleteWorker(selectedWorker.getWorkerId());
                        // Close the popup window
                        Stage stage = (Stage) deleteButton.getScene().getWindow();
                        stage.close();
                        // Refresh the workers list in the main controller
                        if (mainController != null) {
                            mainController.loadWorkers();
                        } else {
                            showAlert(Alert.AlertType.ERROR, "Error", "Main controller is not set.");
                        }
                    } catch (SQLException ex) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete worker.");
                        ex.printStackTrace();
                    }
                }
            });
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void loadWorkers() {
        try {
            workersList.setAll(workerCRUD.getAllWorkers());

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load workers from database.");
            e.printStackTrace();
        }
    }
    private void clearFields() {
        nameField.clear();
        contactField.clear();
        salaryField.clear();
//        jobTitleField.clear();
        usernameField.setVisible(false);
        passwordField.setVisible(false);
        usernameLabel.setVisible(false);
        passwordLabel.setVisible(false);
//        jobRoleDropdown.setDisable(false);
//        workerTypeDropdown.getSelectionModel().selectFirst();
    }
}