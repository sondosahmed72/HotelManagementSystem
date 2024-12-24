package com.example.hotelmanagementsystem.manager;

public class workerMember extends Worker {
    public workerMember(String name, int phoneNumber, String pass, String userName) {
        setRole("Worker");
        setJobTitle("Worker");
        setName(name);
        setPhoneNumber(phoneNumber);
        setPassword(pass);
        setUserName(userName);
    }

}
