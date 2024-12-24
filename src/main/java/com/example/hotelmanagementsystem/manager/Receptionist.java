package com.example.hotelmanagementsystem.manager;

public class Receptionist extends Worker {

    public Receptionist(String name,int phoneNumber,String pass,String userName) {
 setRole("Receptionist");
 setJobTitle("Receptionist");
 setName(name);
 setPhoneNumber(phoneNumber);
 setPassword(pass);
 setUserName(userName);
    }

}

