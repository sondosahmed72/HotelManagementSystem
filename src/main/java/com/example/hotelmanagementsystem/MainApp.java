package com.example.hotelmanagementsystem;

import com.example.hotelmanagementsystem.receptionist.ResidentsViewController;
import com.example.hotelmanagementsystem.receptionist.Resident;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainApp extends Application {

    private static final String URL = "jdbc:sqlite:E:/Pattern/sondos/HotelManagementSystem/db/data.db";

    @Override
    public void start(Stage stage) throws Exception {
        // تحميل ملف FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelmanagementsystem/ResidentsView.fxml"));
        Parent root = loader.load();

        // الحصول على الـ Controller
        ResidentsViewController controller = loader.getController();
        controller.loadData(fetchResidentsFromDatabase());

        // إعداد المشهد (Scene)
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Residents Data");
        stage.show();
    }

    private ObservableList<Resident> fetchResidentsFromDatabase() {
        ObservableList<Resident> residents = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection(URL)) {
            String query = "SELECT * FROM Residents";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    residents.add(new Resident(
                            rs.getInt("residentID"),
                            rs.getString("name"),
                            rs.getInt("roomID"),
                            rs.getString("checkInDate"),
                            rs.getString("checkOutDate"),
                            rs.getString("boardingOption"),
                            rs.getDouble("totalCost")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return residents;
    }

    public static void main(String[] args) {
        launch();
    }
}
