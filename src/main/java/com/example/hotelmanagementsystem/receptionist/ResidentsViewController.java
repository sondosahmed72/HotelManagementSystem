package com.example.hotelmanagementsystem.receptionist;

import com.example.hotelmanagementsystem.receptionist.Resident;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ResidentsViewController {

    @FXML
    private TableView<Resident> tableView;

    @FXML
    private TableColumn<Resident, Integer> idColumn;

    @FXML
    private TableColumn<Resident, String> nameColumn;

    @FXML
    private TableColumn<Resident, Integer> roomIDColumn;

    @FXML
    private TableColumn<Resident, String> checkInDateColumn;

    @FXML
    private TableColumn<Resident, String> checkOutDateColumn;

    @FXML
    private TableColumn<Resident, String> boardingOptionColumn;

    @FXML
    private TableColumn<Resident, Double> totalCostColumn;

    public void initialize() {
        // إعداد أعمدة الجدول
        idColumn.setCellValueFactory(cellData -> cellData.getValue().residentIDProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        roomIDColumn.setCellValueFactory(cellData -> cellData.getValue().roomIDProperty().asObject());
        checkInDateColumn.setCellValueFactory(cellData -> cellData.getValue().checkInDateProperty());
        checkOutDateColumn.setCellValueFactory(cellData -> cellData.getValue().checkOutDateProperty());
        boardingOptionColumn.setCellValueFactory(cellData -> cellData.getValue().boardingOptionProperty());
        totalCostColumn.setCellValueFactory(cellData -> cellData.getValue().totalCostProperty().asObject());
    }

    public void loadData(ObservableList<Resident> residents) {
        tableView.setItems(residents);
    }
}
