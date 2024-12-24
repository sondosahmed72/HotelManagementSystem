package com.example.hotelmanagementsystem.manager;

public abstract class Worker implements Cloneable {
    private int workerId  ;
    private String name ;
    private String role ;
    private String userName ;
    private String Password ;
    private String jobTitle ;
    private int phoneNumber;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
