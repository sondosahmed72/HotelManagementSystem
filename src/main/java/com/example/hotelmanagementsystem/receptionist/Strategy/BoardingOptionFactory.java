package com.example.hotelmanagementsystem.receptionist.Strategy;

// Factory for creating Boarding Options
public class BoardingOptionFactory {
    public static BoardingOption createBoardingOption(String option) {
        switch (option) {
            case "Full Board":
                return new FullBoard();
            case "Half Board":
                return new HalfBoard();
            case "Bed and Breakfast":
                return new BedAndBreakfast();
            default:
                throw new IllegalArgumentException("Invalid boarding option");
        }
    }
}
