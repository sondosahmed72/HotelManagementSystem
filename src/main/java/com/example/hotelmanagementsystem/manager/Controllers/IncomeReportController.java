package com.example.hotelmanagementsystem.manager.Controllers;

import com.example.hotelmanagementsystem.manager.Classes.Report.Builder;
import com.example.hotelmanagementsystem.manager.Classes.Report.IncomeReport;
import com.example.hotelmanagementsystem.manager.Classes.Report.IncomeReportCRUD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class IncomeReportController {
    @FXML
    private ComboBox<String> periodComboBox;
    @FXML
    private TableView<IncomeReport> reportTable;
    @FXML
    private TableColumn<IncomeReport, Integer> colReportID;
    @FXML
    private TableColumn<IncomeReport, String> colPeriod;
    @FXML
    private TableColumn<IncomeReport, Double> colTotalIncome;
    @FXML
    private TableColumn<IncomeReport, String> colCreationDateTime;

    private final IncomeReportCRUD incomeReportCRUD = new IncomeReportCRUD();
    private final ObservableList<IncomeReport> reportsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colReportID.setCellValueFactory(new PropertyValueFactory<>("reportID"));
        colPeriod.setCellValueFactory(new PropertyValueFactory<>("period"));
        colTotalIncome.setCellValueFactory(new PropertyValueFactory<>("totalIncome"));
        colCreationDateTime.setCellValueFactory(new PropertyValueFactory<>("formattedCreationDateTime"));
loadReports();
        reportTable.setItems(reportsList);
        reportTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @FXML
    private void generateReport() {
        String selectedPeriod = periodComboBox.getValue();
        if (selectedPeriod != null) {
            try {
                double totalIncome = incomeReportCRUD.calculateTotalIncome(selectedPeriod);
                IncomeReport report = new Builder()
                        .setPeriod(selectedPeriod)
                        .setTotalIncome(totalIncome)
                        .setCreationDateTime(LocalDateTime.now())
                        .build();
                incomeReportCRUD.addIncomeReport(report);
                List<IncomeReport> reports = incomeReportCRUD.getIncomeReports(selectedPeriod);
                reportsList.setAll(reports);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void loadReports() {
        try {
            reportsList.setAll(incomeReportCRUD.getAllIncomeReports());

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load workers from database.");
            e.printStackTrace();
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}