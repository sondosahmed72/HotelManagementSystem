package com.example.hotelmanagementsystem.receptionist.Strategy;

// Interface for Boarding Option Strategy
public interface BoardingOptionStrategy {
    String getName();
    double getCost(); // Cost specific to the boarding option
}