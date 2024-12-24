module com.example.hotelmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base; // This is required for reflection
    requires com.dlsc.formsfx;
    requires java.sql;
    requires jdk.jdi;
    opens com.example.hotelmanagementsystem.receptionist to javafx.fxml;
    opens com.example.hotelmanagementsystem.manager to javafx.fxml;
    opens com.example.hotelmanagementsystem.login to javafx.fxml;
    opens com.example.hotelmanagementsystem to javafx.fxml;
    exports com.example.hotelmanagementsystem.receptionist; // Ensure that the package is exported
    exports com.example.hotelmanagementsystem;
    exports com.example.hotelmanagementsystem.receptionist.Decorator;
    opens com.example.hotelmanagementsystem.receptionist.Decorator to javafx.fxml;
    exports com.example.hotelmanagementsystem.receptionist.controller;
    opens com.example.hotelmanagementsystem.receptionist.controller to javafx.fxml;
    exports com.example.hotelmanagementsystem.receptionist.Models;
    opens com.example.hotelmanagementsystem.receptionist.Models to javafx.fxml;
}