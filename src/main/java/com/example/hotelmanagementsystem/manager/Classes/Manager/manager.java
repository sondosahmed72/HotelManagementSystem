package com.example.hotelmanagementsystem.manager.Classes.Manager;

public class manager {
    private static manager instance;

    private String name;
    private String role;
    private String jobTitle;
    private String phoneNumber;
    private String salary;
    private String username;
    private String password;

    // Step 2: Private constructor to prevent instantiation from other classes
    private manager() {}

    // Step 3: Public method to provide access to the single instance
    public static manager getInstance() {
        if (instance == null) {
            instance = new manager();
        }
        return instance;
    }


    public void setName(String name){
        this.name=name;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public void setJobTitle(String jobTitle){
        this.jobTitle=jobTitle;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSalary() {
        return salary;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Manager-specific methods
    public void generateIncomeReport() {
        // Logic to generate income reports
    }

    public void manageWorkers() {
        // Logic to add, edit, or delete worker profiles
    }
}
