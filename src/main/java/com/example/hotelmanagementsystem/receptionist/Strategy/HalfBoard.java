package com.example.hotelmanagementsystem.receptionist.Strategy;

// Half Board Option
public class HalfBoard implements BoardingOption {
    @Override
    public String getName() {
        return "Half Board";
    }

    @Override
    public double getCost() {
        return 30.0; // Cost for Half Board
    }
}
