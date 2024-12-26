package com.example.hotelmanagementsystem.login.proxy;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerRole implements SuitableRole {

    private Stage loginStage;
    public ManagerRole(Stage loginStage) {
        this.loginStage = loginStage;
    }


    @Override
    public void forwardToRole() {
        System.out.println("Forwarding to Manager page...");
        try {
            // Load the Manager page (sidebar.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelmanagementsystem/Views/Manager/SideBarforManager.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Manager Page");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the login window after opening the Manager page
            if (loginStage != null) {
                loginStage.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
