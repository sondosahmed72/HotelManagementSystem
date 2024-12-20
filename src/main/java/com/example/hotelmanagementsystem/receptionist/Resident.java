package com.example.hotelmanagementsystem.receptionist;

import javafx.beans.property.*;

public class Resident {
    private final IntegerProperty residentID;
    private final StringProperty name;
    private final IntegerProperty roomID;
    private final StringProperty checkInDate;
    private final StringProperty checkOutDate;
    private final StringProperty boardingOption;
    private final DoubleProperty totalCost;

    public Resident(int residentID, String name, int roomID, String checkInDate, String checkOutDate, String boardingOption, double totalCost) {
        this.residentID = new SimpleIntegerProperty(residentID);
        this.name = new SimpleStringProperty(name);
        this.roomID = new SimpleIntegerProperty(roomID);
        this.checkInDate = new SimpleStringProperty(checkInDate);
        this.checkOutDate = new SimpleStringProperty(checkOutDate);
        this.boardingOption = new SimpleStringProperty(boardingOption);
        this.totalCost = new SimpleDoubleProperty(totalCost);
    }

    public IntegerProperty residentIDProperty() {
        return residentID;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public IntegerProperty roomIDProperty() {
        return roomID;
    }

    public StringProperty checkInDateProperty() {
        return checkInDate;
    }

    public StringProperty checkOutDateProperty() {
        return checkOutDate;
    }

    public StringProperty boardingOptionProperty() {
        return boardingOption;
    }

    public DoubleProperty totalCostProperty() {
        return totalCost;
    }
}
