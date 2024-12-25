package com.example.hotelmanagementsystem.receptionist.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class MainController {

    @FXML
    private StackPane mainContent; // الجزء الذي سيتم تحميل المحتوى فيه عند الضغط على الأزرار


    // هذه الدالة سيتم تنفيذها عند الضغط على زر "Resident"
    @FXML
    private void handleResidentClick() {
        try {
            // تحميل صفحة الـ Residents
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelmanagementsystem/ResidentManagement.fxml"));

            BorderPane residentsPage = loader.load();


            // تحديث محتوى الجزء المركزي
            mainContent.getChildren().setAll(residentsPage);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleWorkersClick() {
        try {
            System.out.println("Loading WorkerManagement.fxml...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelmanagementsystem/Worker/WorkerManagement.fxml"));
            Parent workerManagementPage = loader.load(); // This line throws the exception
            System.out.println("FXML loaded successfully.");
            mainContent.getChildren().setAll(workerManagementPage);

        } catch (IOException e) {
            System.err.println("Error loading WorkerManagement.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}



