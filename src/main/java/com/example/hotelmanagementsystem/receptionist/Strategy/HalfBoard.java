package com.example.hotelmanagementsystem.receptionist.Strategy;

// Half Board Strategy
public class HalfBoard implements BoardingOptionStrategy {
    @Override
    public String getName() {
        return "Half board";
    }

    @Override
    public double getCost() {
        return 30.0; // Cost for Half Board
    }
}