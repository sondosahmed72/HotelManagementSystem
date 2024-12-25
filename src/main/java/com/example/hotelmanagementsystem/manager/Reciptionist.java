package com.example.hotelmanagementsystem.manager;

import com.example.hotelmanagementsystem.manager.Classes.Workers.Worker;

public class Reciptionist extends Worker implements Cloneable {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Reciptionist(String name, String phoneNumber, String password, String username, double salary) {
        setRole("Reciptionist");
        setJobTitle("Reciptionist");
        setName(name);
        setPhoneNumber(phoneNumber);
        setSalary(salary);
        this.password=password;
        this.username=username;

    }
public Reciptionist Clone(){
try {
    return (Reciptionist)super.clone();
}catch (Exception ex) {
throw  new AssertionError();
}
}
}

