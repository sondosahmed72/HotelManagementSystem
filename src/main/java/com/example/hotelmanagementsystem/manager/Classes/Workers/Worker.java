package com.example.hotelmanagementsystem.manager.Classes.Workers;

public abstract class Worker implements Cloneable {
    private int workerId  ;
    private String name ;
    private String role ;
    private String jobTitle ;

    public double getSalary() {
        return salary;
    }

    private String contact;
    private double salary;
@Override
protected Object clone()  {
   try {
    return super.clone();
   }
   catch (CloneNotSupportedException e){
       return null;
   }
}

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setPhoneNumber(String contact) {
        this.contact = contact;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setSalary(double salary) {
    this.salary=salary;
}
}
