module com.example.hotelmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base; // This is required for reflection
    requires com.dlsc.formsfx;
    requires java.sql;
    requires jdk.jdi;
    requires java.desktop;
    opens com.example.hotelmanagementsystem.receptionist to javafx.fxml;
    opens com.example.hotelmanagementsystem.manager to javafx.fxml;
    opens com.example.hotelmanagementsystem.login to javafx.fxml;
    opens com.example.hotelmanagementsystem to javafx.fxml;
    exports com.example.hotelmanagementsystem.receptionist; // Ensure that the package is exported
    exports com.example.hotelmanagementsystem;
    exports com.example.hotelmanagementsystem.manager;
    exports com.example.hotelmanagementsystem.manager.Controllers;
    opens com.example.hotelmanagementsystem.manager.Controllers to javafx.fxml;
    exports com.example.hotelmanagementsystem.manager.Services;
    opens com.example.hotelmanagementsystem.manager.Services to javafx.fxml;
    exports com.example.hotelmanagementsystem.manager.Classes.Rooms;
    opens com.example.hotelmanagementsystem.manager.Classes.Rooms to javafx.fxml;
    exports com.example.hotelmanagementsystem.manager.Classes.Workers;
    opens com.example.hotelmanagementsystem.manager.Classes.Workers to javafx.fxml;
    exports com.example.hotelmanagementsystem.manager.Classes;
    opens com.example.hotelmanagementsystem.manager.Classes to javafx.fxml;
}