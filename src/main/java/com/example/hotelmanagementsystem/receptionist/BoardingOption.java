package com.example.hotelmanagementsystem.receptionist;

// Interface for Boarding Option
public interface BoardingOption {


    static BoardingOption fromString(String boardingOption) {
        switch (boardingOption) {
            case "Full Board":
                return new FullBoard();
            case "Half Board":
                return new HalfBoard();
            case "Bed and Breakfast":
                return new BedAndBreakfast();
            default:
                throw new IllegalArgumentException("Invalid boarding option: " + boardingOption);
        }
    }

    String getName();
    double getCost(); // Cost specific to the boarding option
}
