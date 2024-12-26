package com.example.hotelmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.InputStream;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file for the Resident Management screen
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelmanagementsystem/sidebar.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelmanagementsystem/Views/login/login.fxml"));
        InputStream stream = getClass().getResourceAsStream("/com/example/hotelmanagementsystem/Style/ManagerSideBar.css");
        if (stream == null) {
            System.out.println("Resource not found");
        } else {
            System.out.println("Resource found");
        }
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hotel Management System - Manager Management");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}