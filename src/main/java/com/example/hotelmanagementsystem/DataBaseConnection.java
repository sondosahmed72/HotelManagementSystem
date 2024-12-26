package com.example.hotelmanagementsystem;
//import com.example.hotelmanagementsystem.manager.Classes.Manager.ManagerService;
import com.example.hotelmanagementsystem.manager.Classes.Manager.manager;

import java.sql.*;

public class DataBaseConnection {

    // Use the relative path to the db folder
    private static final String URL = "jdbc:sqlite:C:/Users/lenovo ideapad/IdeaProjects/HotelManagementSystem/db/data.db";
    private static Connection connection;

    // Method to get the URL of the database
    public static String getDatabaseUrl() {
        return URL;
    }

    // Method to get a connection to the database
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
    public static void deleteManagers() {
        String deleteSql = "DELETE FROM Workers WHERE role = 'Manager'";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteSql)) {

            // Execute the DELETE query
            int rowsAffected = stmt.executeUpdate();

            // Output the result
            if (rowsAffected > 0) {
                System.out.println("All workers with the role 'Manager' have been deleted.");
            } else {
                System.out.println("No workers with the role 'Manager' were found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error deleting workers with the 'Manager' role.");
        }
    }

   public static void addManager(){
        // Initialize the manager instance with values
        manager managerInstance = manager.getInstance();
        managerInstance.setName("Admin");
        managerInstance.setRole("Manager");
        managerInstance.setJobTitle("Manager");
        managerInstance.setPhoneNumber("1234567890");
        managerInstance.setSalary("200000");
        managerInstance.setUsername("admin");
        managerInstance.setPassword("admin123");
        saveManagerToDatabase();
    }

    // Method to add workers to the Workers table, but only if they don't already exist
    public static void addWorkersOnce() {
        String checkWorkerSql = "SELECT COUNT(*) FROM Workers WHERE username = ?";
        String insertWorkerSql = "INSERT INTO Workers (name, role, jobTitle, phoneNumber, salary, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkWorkerSql);
             PreparedStatement workerStmt = conn.prepareStatement(insertWorkerSql)) {
            // Check if worker with username 'ali' exists
            checkStmt.setString(1, "ali");
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.getInt(1) == 0) {
                    // Worker 'Ali' does not exist, insert it
                    workerStmt.setString(1, "Ali");
                    workerStmt.setString(2, "Receptionist");
                    workerStmt.setString(3, "Receptionist");
                    workerStmt.setString(4, "010123777");
                    workerStmt.setString(5, "15000");
                    workerStmt.setString(6, "ali");
                    workerStmt.setString(7, "ali123");
                    workerStmt.executeUpdate();
                }
            }

            System.out.println("Workers added successfully if they were not already present.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Method to show all workers in the Workers table
    public static void showAllWorkers() {
        String selectSql = "SELECT * FROM Workers"; // SQL query to select all workers
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(selectSql);
             ResultSet rs = stmt.executeQuery()) {

            // Check if the result set has any rows
            while (rs.next()) {
                // Retrieve each column's value by column name
                int workerID = rs.getInt("workerID");
                String name = rs.getString("name");
                String role = rs.getString("role");
                String jobTitle =rs.getString("jobTitle");
                String salary =rs.getString("salary");
                String phoneNumber =rs.getString("phoneNumber");
                String username = rs.getString("username");
                String password = rs.getString("password");

                // Display worker information
                System.out.println("Worker ID: " + workerID);
                System.out.println("Name: " + name);
                System.out.println("Role: " + role);
                System.out.println("jobTitle: " + jobTitle);
                System.out.println("salary: " + salary);
                System.out.println("phoneNumber: " + phoneNumber);
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);
                System.out.println("---------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveManagerToDatabase() {
        // Get the singleton instance of the manager
        manager managerInstance = manager.getInstance();

        // SQL to check if a manager already exists in the database
        String checkSql = "SELECT COUNT(*) FROM Workers WHERE role = 'Manager'";
        String insertSql = "INSERT INTO Workers (name, role, jobTitle, phoneNumber, salary, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            // Check if there's already a manager in the database
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("A manager already exists in the database.");
                return; // Exit if a manager already exists
            }

            // Insert the manager into the database if no manager is present
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, managerInstance.getName());
                insertStmt.setString(2, managerInstance.getRole());
                insertStmt.setString(3, managerInstance.getJobTitle());
                insertStmt.setString(4, managerInstance.getPhoneNumber());
                insertStmt.setString(5, managerInstance.getSalary());
                insertStmt.setString(6, managerInstance.getUsername());
                insertStmt.setString(7, managerInstance.getPassword());
                insertStmt.executeUpdate();

                System.out.println("Manager information has been saved to the database.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error saving manager to the database.");
        }
    }




}

