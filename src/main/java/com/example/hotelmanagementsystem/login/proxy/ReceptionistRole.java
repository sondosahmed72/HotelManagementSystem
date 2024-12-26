package com.example.hotelmanagementsystem.login.proxy;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class ReceptionistRole implements SuitableRole {

    private Stage loginStage;
    public ReceptionistRole(Stage loginStage) {
        this.loginStage = loginStage;
    }

    @Override
    public void forwardToRole() {
        System.out.println("Forwarding to Receptionist page...");
        try {
            // Load the receptionist page (sidebar.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelmanagementsystem/Views/Receptionist/sidebar.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Receptionist Page");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the login window after opening the receptionist page
            if (loginStage != null) {
                loginStage.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
