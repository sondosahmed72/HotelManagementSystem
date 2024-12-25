package com.example.hotelmanagementsystem.manager.Controllers;

import com.example.hotelmanagementsystem.manager.Classes.Workers.Worker;
import com.example.hotelmanagementsystem.manager.Classes.Workers.WorkerPrototypeRegistry;
import com.example.hotelmanagementsystem.manager.Classes.Workers.workerMember;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class WorkerFormController {
    public BorderPane mainContent2;
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
    private TableColumn<Worker, String> colName;

    @FXML
    private TableColumn<Worker, String> colContact;

    @FXML
    private TableColumn<Worker, Double> salaryColumn;

    @FXML
    private TableColumn<Worker, String> jobTitleColumn;

    @FXML
    private Button submitButton;
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
    private Button editButton;
    @FXML
    private Button deleteButton;

    private Worker selectedWorker;
    private final ObservableList<Object> workersList = FXCollections.observableArrayList();
    private final WorkerCRUD workerCRUD = new WorkerCRUD();


    public WorkerFormController() {
        // No-argument constructor
    }

    @FXML
    public void initialize() {
        // Initialize TableView columns
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        jobTitleColumn.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));

        // Handle row selection
        workerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedWorker = (Worker) newSelection;
                showWorkerDetails((Worker) newSelection);
            }
        });

        workerTable.setItems(workersList);

        workerTable.setItems(workersList);

        // Load workers from database
        loadWorkers();
        // Handle submit button action
        submitButton.setOnAction(e -> addWorker());
    }
    private void loadWorkers() {
        try {
            workersList.setAll(workerCRUD.getAllWorkers());

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load workers from database.");
            e.printStackTrace();
        }
    }
    @FXML
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


            Worker newWorker =  new workerMember(name, contactText);
                newWorker.setName(name);
                newWorker.setPhoneNumber(contactText);
                newWorker.setSalary(salary);
                newWorker.setJobTitle(jobTitle);

                workerCRUD.addWorker(newWorker);
//            workersList.add(newWorker);
                showAlert(Alert.AlertType.INFORMATION, "Worker Added", "Worker added successfully!");
                clearFields();
                loadWorkers();


        } catch (NumberFormatException  | SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add worker.");
            ex.printStackTrace();
        }
    }
    @FXML
    private void editWorker() {
        if (selectedWorker != null) {
            nameField.setText(selectedWorker.getName());
            contactField.setText(selectedWorker.getContact());
            salaryField.setText(String.valueOf(selectedWorker.getSalary()));
            jobTitleField.setText(selectedWorker.getJobTitle());
            submitButton.setOnAction(e -> {
                try {
                    selectedWorker.setName(nameField.getText());
                    selectedWorker.setContact(contactField.getText());
                    selectedWorker.setSalary(Double.parseDouble(salaryField.getText()));
                    selectedWorker.setJobTitle(jobTitleField.getText());
                    workerCRUD.editWorker(selectedWorker);
                    loadWorkers();
                    clearFields();
                    detailsPane.setVisible(false);
                } catch (SQLException ex) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to edit worker.");
                    ex.printStackTrace();
                }
            });
        }
    }

    @FXML
    private void deleteWorker() {
        if (selectedWorker != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this worker?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        workerCRUD.deleteWorker(selectedWorker.getWorkerId());
                        loadWorkers();
                        detailsPane.setVisible(false);
                    } catch (SQLException ex) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete worker.");
                        ex.printStackTrace();
                    }
                }
            });
        }
    }
    private void showWorkerDetails(Worker worker) {
        detailsPane.setVisible(true);
        detailsName.setText("Name: " + worker.getName());
        detailsContact.setText("Contact Information: " + worker.getContact());
        detailsSalary.setText("Salary: " + worker.getSalary());
        detailsJobTitle.setText("Job Title: " + worker.getJobTitle());
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