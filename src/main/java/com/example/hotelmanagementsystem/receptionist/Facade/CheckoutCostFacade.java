package com.example.hotelmanagementsystem.receptionist.Facade;

public class CheckoutCostFacade {

    private final CheckoutCost checkoutCost;

    // Constructor
    public CheckoutCostFacade() {
        this.checkoutCost = new CheckoutCost();
    }

    // حساب التكلفة لمقيم معين
    public void calculateCost(int residentID) {
        checkoutCost.calculateAndUpdateCost(residentID);
    }

    // عرض تفاصيل المقيم
    public void showResidentDetails(int residentID) {
        checkoutCost.showResidentDetails(residentID);
    }

    // واجهة مبسطة لتنفيذ العملية بالكامل: حساب التكلفة ثم عرض التفاصيل
    public void processAndDisplayResidentDetails(int residentID) {
        calculateCost(residentID);
        showResidentDetails(residentID);
    }
    public void calculateCostForAllResidents() {
        checkoutCost.calculateAndUpdateCostsForAllResidents();
    }


}