package com.example.hotelmanagementsystem.receptionist.Strategy;

// Context class to manage boarding options
public class BoardingOptionContext {
    private BoardingOptionStrategy strategy;

    // Constructor to set strategy dynamically
    public BoardingOptionContext(BoardingOptionStrategy strategy) {
        this.strategy = strategy;
    }

    // Setter for changing strategy at runtime
    public void setStrategy(BoardingOptionStrategy strategy) {
        this.strategy = strategy;
    }

    // Delegates to the current strategy
    public String getBoardingOptionName() {
        return strategy.getName();
    }

    public double getBoardingOptionCost() {
        return strategy.getCost();
    }
}
