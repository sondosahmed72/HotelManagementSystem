package com.example.hotelmanagementsystem.login.controller;

import com.example.hotelmanagementsystem.login.proxy.LoginProxy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private void handleLoginAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        System.out.println("Entered Username: " + username);
        System.out.println("Entered Password: " + password);

        // Get the current login window's stage
        Stage loginStage = (Stage) loginButton.getScene().getWindow();

        // Call the Proxy to handle the login logic
        LoginProxy loginProxy = new LoginProxy();
        loginProxy.authenticate(username, password , loginStage);
    }
}
