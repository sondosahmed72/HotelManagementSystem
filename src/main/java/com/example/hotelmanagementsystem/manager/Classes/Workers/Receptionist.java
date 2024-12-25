package com.example.hotelmanagementsystem.manager.Classes.Workers;

public class Receptionist extends Worker implements Cloneable {
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

    public Receptionist(String name, String phoneNumber, String password, String username, double salary) {
        setRole("Reciptionist");
        setJobTitle("Reciptionist");
        setName(name);
        setPhoneNumber(phoneNumber);
        setSalary(salary);
        this.password=password;
        this.username=username;

    }
public Receptionist Clone(){
try {
    return (Receptionist)super.clone();
}catch (Exception ex) {
throw  new AssertionError();
}
}
}

