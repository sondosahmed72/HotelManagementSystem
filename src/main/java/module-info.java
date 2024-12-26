module com.example.hotelmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base; // This is required for reflection
    requires com.dlsc.formsfx;
    requires java.sql;
    requires jdk.jdi;
    requires java.desktop;
    opens com.example.hotelmanagementsystem.Views.Manager to javafx.fxml;
    opens com.example.hotelmanagementsystem to javafx.fxml; // Ensure that the package is exported
    exports com.example.hotelmanagementsystem;
    exports com.example.hotelmanagementsystem.manager.Classes.Manager;
    exports com.example.hotelmanagementsystem.manager.Controllers;
    exports com.example.hotelmanagementsystem.login.controller to javafx.fxml;
    opens com.example.hotelmanagementsystem.login.controller to javafx.fxml;
    opens com.example.hotelmanagementsystem.manager.Controllers to javafx.fxml;
    exports com.example.hotelmanagementsystem.manager.Services;
    opens com.example.hotelmanagementsystem.manager.Services to javafx.fxml;
    exports com.example.hotelmanagementsystem.manager.Classes.Rooms;
    opens com.example.hotelmanagementsystem.manager.Classes.Rooms to javafx.fxml;
    exports com.example.hotelmanagementsystem.manager.Classes.Workers;
    opens com.example.hotelmanagementsystem.manager.Classes.Workers to javafx.fxml;
    exports com.example.hotelmanagementsystem.manager.Classes.Report;
    opens com.example.hotelmanagementsystem.manager.Classes.Report to javafx.fxml;
    opens com.example.hotelmanagementsystem.manager.Classes.Manager to javafx.fxml;
    exports com.example.hotelmanagementsystem.receptionist.controller;
    opens com.example.hotelmanagementsystem.receptionist.controller to javafx.fxml;
    opens com.example.hotelmanagementsystem.receptionist.Models to javafx.base;
    exports com.example.hotelmanagementsystem.receptionist.Facade;
    opens com.example.hotelmanagementsystem.receptionist.Facade to javafx.fxml;
}