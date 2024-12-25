package com.example.hotelmanagementsystem.receptionist.Facade;

import com.example.hotelmanagementsystem.DataBaseConnection;
import com.example.hotelmanagementsystem.receptionist.Decorator.Service; // استيراد Service
import com.example.hotelmanagementsystem.receptionist.Decorator.BasicService; // استيراد BasicService
import com.example.hotelmanagementsystem.receptionist.Decorator.SaunaDecorator; // استيراد SaunaDecorator
import com.example.hotelmanagementsystem.receptionist.Decorator.SpaHammamDecorator; // استيراد SpaHammamDecorator
import com.example.hotelmanagementsystem.receptionist.Decorator.MassageDecorator; // استيراد MassageDecorator
import com.example.hotelmanagementsystem.receptionist.Decorator.PrivateViewDecorator; // استيراد PrivateViewDecorator
import com.example.hotelmanagementsystem.receptionist.Strategy.BoardingOptionStrategy;
import com.example.hotelmanagementsystem.receptionist.Strategy.BoardingOptionSelector;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.*;

public class CheckoutCost {

    private static final String DB_URL = DataBaseConnection.getDatabaseUrl();
    // دالة حساب الكوست وتحديث totalCost في قاعدة البيانات
    public void calculateAndUpdateCost(int residentID) {
        String query = "SELECT Rooms.price AS roomPrice, Rooms.type AS roomType, " +
                "Residents.checkInDate, Residents.checkOutDate, " +
                "Residents.hasSauna, Residents.hasSpaHammam, Residents.hasMassage, Residents.hasPrivateView, Residents.roomID, " +
                "Residents.boardingOption " + // إضافة boardingOption
                "FROM Residents " +
                "INNER JOIN Rooms ON Residents.roomID = Rooms.roomID " +
                "WHERE Residents.residentID = ?";


        double totalCost = 0.0;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, residentID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double roomPrice = rs.getDouble("roomPrice");
                long checkInTime = Date.valueOf(rs.getString("checkInDate")).getTime();
                long checkOutTime = Date.valueOf(rs.getString("checkOutDate")).getTime();
                int durationInDays = (int) ((checkOutTime - checkInTime) / (1000 * 60 * 60 * 24));
                double roomTotalCost = roomPrice * durationInDays;

                // Get boarding option and calculate its cost
                String boardingOption = rs.getString("boardingOption");
                BoardingOptionStrategy strategy = BoardingOptionSelector.selectStrategy(boardingOption);
                double boardingCost = strategy.getCost() * durationInDays;


                Service service = new BasicService();
                if (rs.getBoolean("hasSauna")) service = new SaunaDecorator(service);
                if (rs.getBoolean("hasSpaHammam")) service = new SpaHammamDecorator(service);
                if (rs.getBoolean("hasMassage")) service = new MassageDecorator(service);
                if (rs.getBoolean("hasPrivateView")) service = new PrivateViewDecorator(service);


                totalCost = roomTotalCost + boardingCost + service.getCost();

                String updateQuery = "UPDATE Residents SET totalCost = ? WHERE residentID = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setDouble(1, totalCost);
                    updateStmt.setInt(2, residentID);
                    int rowsUpdated = updateStmt.executeUpdate();

                    if (rowsUpdated > 0) {
                        System.out.println("Total cost updated successfully for resident ID: " + residentID);
                    } else {
                        System.out.println("Failed to update total cost. No rows affected.");
                    }
                }

                String checkQuery = "SELECT totalCost FROM Residents WHERE residentID = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                    checkStmt.setInt(1, residentID);
                    ResultSet rsCheck = checkStmt.executeQuery();
                    if (rsCheck.next()) {
                        System.out.println("Verified Total Cost: " + rsCheck.getDouble("totalCost"));
                    }
                }
            } else {
                System.out.println("No data found for resident ID: " + residentID);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
    public void calculateAndUpdateCostsForAllResidents() {
        String query = "SELECT Residents.residentID, Rooms.price AS roomPrice, Rooms.type AS roomType, " +
                "Residents.checkInDate, Residents.checkOutDate, " +
                "Residents.hasSauna, Residents.hasSpaHammam, Residents.hasMassage, Residents.hasPrivateView, Residents.roomID, " +
                "Residents.boardingOption " +
                "FROM Residents " +
                "INNER JOIN Rooms ON Residents.roomID = Rooms.roomID";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int residentID = rs.getInt("residentID");
                double roomPrice = rs.getDouble("roomPrice");
                long checkInTime = Date.valueOf(rs.getString("checkInDate")).getTime();
                long checkOutTime = Date.valueOf(rs.getString("checkOutDate")).getTime();
                int durationInDays = (int) ((checkOutTime - checkInTime) / (1000 * 60 * 60 * 24));
                double roomTotalCost = roomPrice * durationInDays;

                // Get boarding option and calculate its cost
                String boardingOption = rs.getString("boardingOption");
                BoardingOptionStrategy strategy = BoardingOptionSelector.selectStrategy(boardingOption);
                double boardingCost = strategy.getCost() * durationInDays;

                // Decorator pattern for additional services
                Service service = new BasicService();
                if (rs.getBoolean("hasSauna")) service = new SaunaDecorator(service);
                if (rs.getBoolean("hasSpaHammam")) service = new SpaHammamDecorator(service);
                if (rs.getBoolean("hasMassage")) service = new MassageDecorator(service);
                if (rs.getBoolean("hasPrivateView")) service = new PrivateViewDecorator(service);

                double totalCost = roomTotalCost + boardingCost + service.getCost();

                // Update total cost in the database for this resident
                String updateQuery = "UPDATE Residents SET totalCost = ? WHERE residentID = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setDouble(1, totalCost);
                    updateStmt.setInt(2, residentID);
                    int rowsUpdated = updateStmt.executeUpdate();

                    if (rowsUpdated > 0) {
                        System.out.println("Total cost updated successfully for resident ID: " + residentID);
                    } else {
                        System.out.println("Failed to update total cost. No rows affected for resident ID: " + residentID);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
    // دالة لعرض بيانات المقيم مع التكلفة الإجمالية والخدمات المستخدمة
    // دالة لعرض بيانات المقيم مع التكلفة الإجمالية والخدمات المستخدمة
    public void showResidentDetails(int residentID) {
        // حساب التكلفة وتحديث قاعدة البيانات
        calculateAndUpdateCost(residentID);

        String query = "SELECT Residents.name, Residents.checkInDate, Residents.checkOutDate, " +
                "Rooms.type AS roomType, Residents.totalCost, " +
                "Residents.hasSauna, Residents.hasSpaHammam, Residents.hasMassage, Residents.hasPrivateView " +
                "FROM Residents " +
                "INNER JOIN Rooms ON Residents.roomID = Rooms.roomID " +
                "WHERE Residents.residentID = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, residentID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String checkInDate = rs.getString("checkInDate");
                String checkOutDate = rs.getString("checkOutDate");
                String roomType = rs.getString("roomType");
                double totalCost = rs.getDouble("totalCost");

                StringBuilder servicesUsed = new StringBuilder();
                if (rs.getBoolean("hasSauna")) servicesUsed.append("Sauna, ");
                if (rs.getBoolean("hasSpaHammam")) servicesUsed.append("Spa Hammam, ");
                if (rs.getBoolean("hasMassage")) servicesUsed.append("Massage, ");
                if (rs.getBoolean("hasPrivateView")) servicesUsed.append("Private View, ");

                if (servicesUsed.length() > 0) {
                    servicesUsed.setLength(servicesUsed.length() - 2);  // إزالة الفاصلة الزائدة
                }

                // عرض التفاصيل في نافذة Alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Resident Cost Details");
                alert.setHeaderText("Cost Details for Resident ID: " + residentID);
                alert.setContentText("Name: " + name +
                        "\nRoom Type: " + roomType +
                        "\nCheck-in Date: " + checkInDate +
                        "\nCheck-out Date: " + checkOutDate +
                        "\nTotal Cost: " + totalCost + " EGP" +  // عرض التكلفة الإجمالية هنا
                        "\nServices Used: " + servicesUsed.toString()+
                        "\nTotal Cost: " + totalCost + " EGP");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            System.out.println("Error fetching resident details: " + e.getMessage());
        }
    }



    // دالة لعرض التنبيهات
    public void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
