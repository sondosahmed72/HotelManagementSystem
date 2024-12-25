package com.example.hotelmanagementsystem.manager.Classes.Workers;

public class workerMember extends Worker {
    public workerMember(String name, String phoneNumber) {
        setRole("Worker");
        setJobTitle("Worker");
        setName(name);
        setPhoneNumber(phoneNumber);

    }

}
