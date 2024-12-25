package com.example.hotelmanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;

import java.io.IOException;


import javafx.scene.Node;
public class MainController {

    @FXML
    private StackPane mainContent; // الجزء الذي سيتم تحميل المحتوى فيه عند الضغط على الأزرار

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
}