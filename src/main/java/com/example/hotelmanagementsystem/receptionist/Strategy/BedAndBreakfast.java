package com.example.hotelmanagementsystem.receptionist.Strategy;

// Bed and Breakfast Strategy
public class BedAndBreakfast implements BoardingOptionStrategy {
    @Override
    public String getName() {
        return "Bed and Breakfast";
    }

    @Override
    public double getCost() {
        return 20.0; // Cost for Bed and Breakfast
    }
}