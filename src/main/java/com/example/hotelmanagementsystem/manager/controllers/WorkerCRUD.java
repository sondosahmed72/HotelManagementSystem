package com.example.hotelmanagementsystem.manager.Controllers;

import com.example.hotelmanagementsystem.DataBaseConnection;
import com.example.hotelmanagementsystem.manager.Classes.Workers.Receptionist;
import com.example.hotelmanagementsystem.manager.Classes.Workers.Worker;
import com.example.hotelmanagementsystem.manager.Classes.Workers.WorkerPrototypeRegistry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkerCRUD {
    private final WorkerPrototypeRegistry prototypeRegistry = new WorkerPrototypeRegistry();

    public void addWorker(Worker worker) throws SQLException {
        String query = "INSERT INTO Workers (name, role, jobTitle, phoneNumber, salary, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnection()) {
            if (conn == null || conn.isClosed()) {
                System.err.println("Failed to establish a database connection.");
                return;
            }
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, worker.getName());
                preparedStatement.setString(2, worker.getRole());
                preparedStatement.setString(3, worker.getJobTitle());
                preparedStatement.setString(4, worker.getContact());
                preparedStatement.setDouble(5, worker.getSalary());

                if (worker instanceof Receptionist) {
                    preparedStatement.setString(6, ((Receptionist) worker).getUsername());
                    preparedStatement.setString(7, ((Receptionist) worker).getPassword());
                } else {
                    preparedStatement.setNull(6, java.sql.Types.VARCHAR);
                    preparedStatement.setNull(7, java.sql.Types.VARCHAR);
                }

                preparedStatement.executeUpdate();
            }
        }
    }

    public void editWorker(Worker worker) throws SQLException {
        String query = "UPDATE Workers SET name = ?, role = ?, jobTitle = ?, phoneNumber = ?, salary = ?, username = ?, password = ? WHERE workerID = ?";
        try (Connection conn = DataBaseConnection.getConnection()) {
            if (conn == null || conn.isClosed()) {
                System.err.println("Failed to establish a database connection.");
                return;
            }
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, worker.getName());
                pstmt.setString(2, worker.getRole());
                pstmt.setString(3, worker.getJobTitle());
                pstmt.setString(4, worker.getContact());
                pstmt.setDouble(5, worker.getSalary());

                if (worker instanceof Receptionist) {
                    pstmt.setString(6, ((Receptionist) worker).getUsername());
                    pstmt.setString(7, ((Receptionist) worker).getPassword());
                } else {
                    pstmt.setNull(6, java.sql.Types.VARCHAR);
                    pstmt.setNull(7, java.sql.Types.VARCHAR);
                }

                pstmt.setInt(8, worker.getWorkerId());
                pstmt.executeUpdate();
            }
        }
    }

    public void deleteWorker(int workerId) throws SQLException {
        String query = "DELETE FROM Workers WHERE workerID = ?";
        try (Connection conn = DataBaseConnection.getConnection()) {
            if (conn == null || conn.isClosed()) {
                System.err.println("Failed to establish a database connection.");
                return;
            }
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, workerId);
                pstmt.executeUpdate();
            }
        }
    }

    public List<Worker> getAllWorkers() throws SQLException {
        List<Worker> workers = new ArrayList<>();
        String query = "SELECT * FROM Workers";
        try (Connection conn = DataBaseConnection.getConnection()) {
            if (conn == null || conn.isClosed()) {
                System.err.println("Failed to establish a database connection.");
                return workers;
            }
            try (PreparedStatement pstmt = conn.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Worker worker;
                    if ("Receptionist".equals(rs.getString("role"))) {
                        worker = prototypeRegistry.getClone("receptionist");
                        ((Receptionist) worker).setUsername(rs.getString("username"));
                        ((Receptionist) worker).setPassword(rs.getString("password"));
                    } else {
                        worker = prototypeRegistry.getClone("worker");
                    }

                    if (worker != null) {
                        worker.setWorkerId(rs.getInt("workerID"));
                        worker.setName(rs.getString("name"));
                        worker.setRole(rs.getString("role"));
                        worker.setJobTitle(rs.getString("jobTitle"));
                        worker.setPhoneNumber(rs.getString("phoneNumber"));
                        worker.setSalary(rs.getDouble("salary"));
                        workers.add(worker);

                        System.out.println("Fetched Worker: " + worker.getName() + ", " + worker.getContact() + ", " + worker.getSalary() + ", " + worker.getJobTitle());
                    }
                }
            }
        }
        return workers;
    }
}