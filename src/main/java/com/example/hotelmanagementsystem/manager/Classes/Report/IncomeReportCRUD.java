package com.example.hotelmanagementsystem.manager.Classes.Report;

import com.example.hotelmanagementsystem.DataBaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IncomeReportCRUD {
    public void addIncomeReport(IncomeReport report) throws SQLException {
        String query = "INSERT INTO IncomeReports (period, totalIncome, creationDateTime) VALUES (?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, report.getPeriod());
            pstmt.setDouble(2, report.getTotalIncome());
            pstmt.setTimestamp(3, Timestamp.valueOf(report.getCreationDateTime()));
            pstmt.executeUpdate();
        }
    }

    public List<IncomeReport> getIncomeReports(String period) throws SQLException {
        List<IncomeReport> reports = new ArrayList<>();
        String query = "SELECT * FROM IncomeReports WHERE period = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, period);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Timestamp timestamp = rs.getTimestamp("creationDateTime");
                    LocalDateTime creationDateTime = (timestamp != null) ? timestamp.toLocalDateTime() : null;
                    IncomeReport report = new Builder()
                            .setReportID(rs.getInt("reportID"))
                            .setPeriod(rs.getString("period"))
                            .setTotalIncome(rs.getDouble("totalIncome"))
                            .setCreationDateTime(creationDateTime)
                            .build();
                    reports.add(report);
                }
            }
        }
        return reports;
    }
    public List<IncomeReport> getAllIncomeReports() throws SQLException {
        List<IncomeReport> reports = new ArrayList<>();
        String query = "SELECT * FROM IncomeReports";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Timestamp timestamp = rs.getTimestamp("creationDateTime");
                    LocalDateTime creationDateTime = (timestamp != null) ? timestamp.toLocalDateTime() : null;
                    IncomeReport report = new Builder()
                            .setReportID(rs.getInt("reportID"))
                            .setPeriod(rs.getString("period"))
                            .setTotalIncome(rs.getDouble("totalIncome"))
                            .setCreationDateTime(creationDateTime)
                            .build();
                    reports.add(report);
                }
            }
        }
        return reports;
    }
    public double calculateTotalIncome(String period) throws SQLException {
        String query = "";
        switch (period) {
            case "Weekly":
                query = "SELECT SUM(totalCost) AS totalIncome FROM Residents WHERE checkInDate >= DATE('now', '-7 days')";
                break;
            case "Monthly":
                query = "SELECT SUM(totalCost) AS totalIncome FROM Residents WHERE checkInDate >= DATE('now', 'start of month')";
                break;
            case "Annual":
                query = "SELECT SUM(totalCost) AS totalIncome FROM Residents WHERE checkInDate >= DATE('now', 'start of year')";
                break;
        }

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("totalIncome");
            }
        }
        return 0;
    }
}