package com.example.hotelmanagementsystem.receptionist;

// Full Board Option
public class FullBoard implements BoardingOption {
    @Override
    public String getName() {
        return "Full Board";
    }

    @Override
    public double getCost() {
        return 50.0; // Cost for Full Board
    }
}
