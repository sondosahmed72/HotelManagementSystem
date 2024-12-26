package com.example.hotelmanagementsystem.login.proxy;
import com.example.hotelmanagementsystem.DataBaseConnection;
import javafx.stage.Stage;

import java.sql.SQLException;

import java.sql.*;


public class LoginProxy implements SuitableRole {

    private SuitableRole suitableRole;

    // Authenticate the user and decide which role to forward to
    public void authenticate(String username, String password, Stage loginStage) {
        // This is where you check the user's role from the database
        String userRole = checkUserRole(username, password);  // This should query the database
        if (userRole == null) {
            System.out.println("No user found with the given username and password");
            return;
        }

        if (userRole.equals("Reciptionist")) {
            suitableRole = new ReceptionistRole(loginStage);
        } else if (userRole.equals("Manager")) {
            suitableRole = new ManagerRole(loginStage);
        } else {
            System.out.println("Invalid role");
            return;
        }
        // Forward the user to the suitable page
        forwardToRole();
    }

    @Override
    public void forwardToRole() {
        suitableRole.forwardToRole();
    }
    private static final String dbUrl = DataBaseConnection.getDatabaseUrl();

    private String checkUserRole(String username, String password) {
        // Hardcoded manager credentials
        if (username.equals("admin") && password.equals("admin123")) {
            System.out.println("Hardcoded login: Manager role found for admin");
            return "Manager"; // Return Manager role if credentials match
        }
        String role = null;
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            String sql = "SELECT role FROM Workers WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, username.trim());
                stmt.setString(2, password.trim());
                ResultSet rs = stmt.executeQuery();

                // Check if a result was returned
                if (rs.next()) {
                    role = rs.getString("role");
                    System.out.println("Entered Username: " + username);
                    System.out.println("Entered Password: " + password);
                    System.out.println("Role found: " + role); // Print the role from the database

                } else {
                    System.out.println("No matching user found in the database.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

}
