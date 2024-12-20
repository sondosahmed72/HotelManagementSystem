package com.example.hotelmanagementsystem.manager;

public class manager {
    private static manager instance;

    // Step 2: Private constructor to prevent instantiation from other classes
    private manager() {}

    // Step 3: Public method to provide access to the single instance
    public static manager getInstance() {
        if (instance == null) {
            instance = new manager();
        }
        return instance;
    }

    // Manager-specific methods
    public void generateIncomeReport() {
        // Logic to generate income reports
    }

    public void manageWorkers() {
        // Logic to add, edit, or delete worker profiles
    }
}
