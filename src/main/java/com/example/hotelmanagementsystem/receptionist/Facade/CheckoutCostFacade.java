package com.example.hotelmanagementsystem.receptionist.Facade;

public class CheckoutCostFacade {

    private final CheckoutCost checkoutCost;

    // Constructor
    public CheckoutCostFacade() {
        this.checkoutCost = new CheckoutCost();
    }

    //Calculate the cost for a specific resident
    public void calculateCost(int residentID) {
        checkoutCost.calculateAndUpdateCost(residentID);
    }

    // Show resident details
    public void showResidentDetails(int residentID) {
        checkoutCost.showResidentDetails(residentID);
    }


    public void processAndDisplayResidentDetails(int residentID) {
        calculateCost(residentID);
        showResidentDetails(residentID);
    }



}