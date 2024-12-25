package com.example.hotelmanagementsystem.receptionist.Strategy;

// Full Board Strategy
public class FullBoard implements BoardingOptionStrategy {
    @Override
    public String getName() {
        return "Full board";
    }

    @Override
    public double getCost() {
        return 50.0; // Cost for Full Board
    }
}
