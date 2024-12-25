package com.example.hotelmanagementsystem.receptionist.Strategy;

// Utility class for strategy selection
public class BoardingOptionSelector {
    public static BoardingOptionStrategy selectStrategy(String boardingOption) {
        switch (boardingOption) {
            case "Full board":
                return new FullBoard();
            case "Half board":
                return new HalfBoard();
            case "Bed and Breakfast":
                return new BedAndBreakfast();
            default:
                throw new IllegalArgumentException("Invalid boarding option: " + boardingOption);
        }
    }
}


