package com.example.hotelmanagementsystem.manager.Controllers;
import com.example.hotelmanagementsystem.manager.Classes.Workers.Receptionist;
import com.example.hotelmanagementsystem.manager.Classes.Workers.Worker;
import com.example.hotelmanagementsystem.manager.Classes.Workers.WorkerPrototypeRegistry;
import com.example.hotelmanagementsystem.manager.Classes.Workers.workerMember;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class WorkerFormController {
    @FXML
    public BorderPane mainContent2;
    @FXML
    private TextField nameField;

    @FXML
    private TextField contactField;

    @FXML
    private TextField salaryField;
    @FXML
    private ComboBox<String> workerTypeDropdown; // Dropdown for worker type

    @FXML
    private ComboBox<String> jobRoleDropdown; // Dropdown for job roles
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
    private TableColumn<Worker, String> roleColumn;

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
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;


    @FXML
    private Label detailsUsername;

    @FXML
    private Label detailsPassword;



    private Worker selectedWorker;
    private final ObservableList<Object> workersList = FXCollections.observableArrayList();
    private final WorkerCRUD workerCRUD = new WorkerCRUD();
    private final WorkerPrototypeRegistry prototypeRegistry = new WorkerPrototypeRegistry();


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
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        // Handle row selection
        workerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                openWorkerDetailsPopup((Worker) newSelection);
            }
        });

        // Populate the worker type dropdown
        workerTypeDropdown.setItems(FXCollections.observableArrayList("Ordinary Worker", "Receptionist"));
        workerTypeDropdown.getSelectionModel().selectFirst(); // Default selection

        // Populate the job role dropdown
        jobRoleDropdown.setItems(FXCollections.observableArrayList("Housekeeper", "Chef", "Gardener",    "Event Coordinator",
                "Banquet Server",
                "Sous Chef",
                "Line Cook",
                "Maintenance Technician",
                "Spa Therapist",
                "Security Guard",
                "Valet Parking Attendant",
                "Waiter/Waitress",
                "Bartender",
                "Reservation Agent",
                "Accountant",  "Concierge",
                "Room Service Attendant",
                "Bellhop"));
        jobRoleDropdown.getSelectionModel().selectFirst(); // Default selection

        // Add listener to workerTypeDropdown
        workerTypeDropdown.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            boolean isReceptionist = "Receptionist".equals(newVal);
            usernameField.setVisible(isReceptionist);
            usernameLabel.setVisible(isReceptionist);
            passwordField.setVisible(isReceptionist);
            passwordLabel.setVisible(isReceptionist);

            if (isReceptionist) {
                usernameField.setText("Receptionist");
                passwordField.setText("receptionist1234");
                jobRoleDropdown.setValue("Receptionist");
                jobRoleDropdown.setDisable(true);
            } else {
                usernameField.clear();
                passwordField.clear();
                jobRoleDropdown.setDisable(false);
            }
        });

        workerTable.setItems(workersList);

        // Load workers from database
        loadWorkers();

        // Handle submit button action
        submitButton.setOnAction(e -> addWorker());
    }
    @FXML
    public void loadWorkers() {
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
        String role = workerTypeDropdown.getValue();
        String jobTitle = "Receptionist".equals(role) ? "Receptionist" : jobRoleDropdown.getValue();
        String workerType = workerTypeDropdown.getSelectionModel().getSelectedItem();

        if (role == null || name.isEmpty() || contactText.isEmpty() || salaryText.isEmpty() || jobTitle.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill all fields.");
            return;
        }

        try {
            double salary = Double.parseDouble(salaryText);
            Worker newWorker;

            if ("Receptionist".equals(workerType)) {
                newWorker = (Receptionist) prototypeRegistry.getClone("receptionist");
                ((Receptionist) newWorker).setUsername(usernameField.getText());
                ((Receptionist) newWorker).setPassword(passwordField.getText());
            } else {
                newWorker = (Worker) prototypeRegistry.getClone("worker");
            }

            newWorker.setName(name);
            newWorker.setPhoneNumber(contactText);
            newWorker.setSalary(salary);
            newWorker.setJobTitle(jobTitle);
            newWorker.setRole(role);

            workerCRUD.addWorker(newWorker);
            showAlert(Alert.AlertType.INFORMATION, "Worker Added", "Worker added successfully!");
            clearFields();
            loadWorkers();

        } catch (NumberFormatException | SQLException ex) {
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
    private void openWorkerDetailsPopup(Worker worker) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelmanagementsystem/Views/Worker/Details.fxml"));
            Parent root = loader.load();

            WorkerDetailsController detailsController = loader.getController();
            detailsController.setWorkerDetails(worker);
            detailsController.setMainController(this); // Pass the main controller

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(submitButton.getScene().getWindow()); // Set the owner
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void fillFormWithWorkerData(Worker worker) {
        nameField.setText(worker.getName());
        contactField.setText(worker.getContact());
        salaryField.setText(String.valueOf(worker.getSalary()));
        if (worker instanceof Receptionist) {
            usernameField.setText(((Receptionist) worker).getUsername());
            passwordField.setText(((Receptionist) worker).getPassword());
            workerTypeDropdown.setValue("Receptionist");
            jobRoleDropdown.setDisable(true);
            usernameLabel.setVisible(true);
            usernameField.setVisible(true);
            passwordLabel.setVisible(true);
            passwordField.setVisible(true);
        } else {
            workerTypeDropdown.setValue("Ordinary Worker");
            jobRoleDropdown.setValue(worker.getJobTitle());
            jobRoleDropdown.setDisable(false);
            usernameLabel.setVisible(false);
            usernameField.setVisible(false);
            passwordLabel.setVisible(false);
            passwordField.setVisible(false);
        }
        submitButton.setOnAction(e -> updateWorker(worker));
    }
    private void updateWorker(Worker worker) {
        try {
            worker.setName(nameField.getText());
            worker.setContact(contactField.getText());
            worker.setSalary(Double.parseDouble(salaryField.getText()));

            String selectedType = workerTypeDropdown.getValue();
            String jobTitle = jobRoleDropdown.getValue();

            if ("Receptionist".equals(selectedType)) {
                if (!(worker instanceof Receptionist)) {
                    // Convert Worker to Receptionist
                    Receptionist newReceptionist = new Receptionist(worker.getName(), worker.getContact(), usernameField.getText(), passwordField.getText(), worker.getSalary());
                    newReceptionist.setWorkerId(worker.getWorkerId());
                    worker = newReceptionist;
                }
                ((Receptionist) worker).setUsername(usernameField.getText());
                ((Receptionist) worker).setPassword(passwordField.getText());
                worker.setJobTitle("Receptionist");
            } else {
                if (worker instanceof Receptionist) {
                    // Convert Receptionist to Worker
                    Worker newWorker = new workerMember(worker.getName(), worker.getContact());
                    newWorker.setWorkerId(worker.getWorkerId());
                    newWorker.setSalary(worker.getSalary());
                    worker = newWorker;
                }
                worker.setJobTitle(jobTitle);
            }

            workerCRUD.editWorker(worker);
            loadWorkers();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Worker Updated", "Worker updated successfully!");
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update worker.");
            ex.printStackTrace();
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
        jobRoleDropdown.setDisable(false);
        workerTypeDropdown.getSelectionModel().selectFirst();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


}