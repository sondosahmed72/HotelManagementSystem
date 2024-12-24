package com.example.hotelmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static final String URL = "jdbc:sqlite:E:/Pattern/sondos/HotelManagementSystem/db/data.db";  // مسار قاعدة البيانات

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file for the Resident Management screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelmanagementsystem/sidebar.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hotel Management System - Resident Management");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
