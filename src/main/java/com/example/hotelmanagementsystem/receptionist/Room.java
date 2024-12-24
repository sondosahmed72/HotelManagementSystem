package com.example.hotelmanagementsystem.receptionist;

public class Room {
    private int id;
    private String type;
    private double price;
    private String status;

    // Constructor
    public Room(int id, String type, double price, String status) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.status = status;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

//    public String getBoardingOptionName() {
//        return boardingOption.getName();
//    }
//
//    public double getTotalCost() {
//        return price + boardingOption.getCost(); // Total cost = Room cost + Boarding option cost
//    }

//    // Setters
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public void setBoardingOption(BoardingOption boardingOption) {
//        this.boardingOption = boardingOption;
//    }
//    // Add getter for boardingOption
//    public BoardingOption getBoardingOption() {
//        return boardingOption;
//    }
}
