module com.example.hotelmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires jdk.jdi;
    opens com.example.hotelmanagementsystem.receptionist to javafx.fxml;
    opens com.example.hotelmanagementsystem.manager to javafx.fxml;
    opens com.example.hotelmanagementsystem.login to javafx.fxml;
    opens com.example.hotelmanagementsystem to javafx.fxml;
    exports com.example.hotelmanagementsystem;
}