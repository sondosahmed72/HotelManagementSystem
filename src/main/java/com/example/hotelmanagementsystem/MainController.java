package com.example.hotelmanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;

import java.awt.event.ActionEvent;
import java.io.IOException;


import javafx.scene.Node;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private StackPane mainContent; // الجزء الذي سيتم تحميل المحتوى فيه عند الضغط على الأزرار
    @FXML
    private Button logoutButton;

    // هذه الدالة سيتم تنفيذها عند الضغط على زر "Resident"
    @FXML
    private void handleResidentClick() {
        try {
            // تحميل صفحة الـ Residents
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelmanagementsystem/Views/Receptionist/ResidentManagement.fxml"));

            BorderPane residentsPage = loader.load();


            // تحديث محتوى الجزء المركزي
            mainContent.getChildren().setAll(residentsPage);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleRoomsClick(javafx.event.ActionEvent actionEvent) {
        try {
            // Load the RoomsManager.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelmanagementsystem/Views/Manager/RoomsManager.fxml"));
            Node roomsContent = loader.load();

            // Clear the mainContent and set the new content
            mainContent.getChildren().clear();
            mainContent.getChildren().add(roomsContent);
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception (log or show an alert)
        }
    }

    @FXML
    public void handleRoomClick(javafx.event.ActionEvent actionEvent) {
        try {
            // Load the RoomsManager.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelmanagementsystem/Views/Receptionist/Room.fxml"));
            Node roomsContent = loader.load();

            // Clear the mainContent and set the new content
            mainContent.getChildren().clear();
            mainContent.getChildren().add(roomsContent);
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception (log or show an alert)
        }
    }

    @FXML
    public void handleResidentsClick(javafx.event.ActionEvent actionEvent) {
        try {
            // Load the RoomsManager.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelmanagementsystem/Views/Manager/ResidentsManager.fxml"));

            Node roomsContent = loader.load();

            // Clear the mainContent and set the new content
            mainContent.getChildren().clear();
            mainContent.getChildren().add(roomsContent);
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception (log or show an alert)
        }
    }

    @FXML
    private void handleWorkersClick() {
        try {
            //System.out.println("Loading WorkerManagement.fxml...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelmanagementsystem/Views/Worker/WorkerManagement.fxml"));
            Parent workerManagementPage = loader.load();
            //System.out.println("FXML loaded successfully.");
            mainContent.getChildren().clear();
            mainContent.getChildren().add(workerManagementPage); // Set the loaded content to the center of the BorderPane
        } catch (IOException e) {
            // System.err.println("Error loading WorkerManagement.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogoutAction(javafx.event.ActionEvent event) {
        // أغلق النافذة الحالية
        Stage currentStage = (Stage) logoutButton.getScene().getWindow();
        currentStage.close();

        try {
            // افتح نافذة تسجيل الدخول
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelmanagementsystem/Views/login/login.fxml")); // عدل المسار
            Parent loginRoot = loader.load();

            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.setScene(new Scene(loginRoot));
            loginStage.show();
        } catch (IOException e) {
            System.err.println("Error loading login.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

}