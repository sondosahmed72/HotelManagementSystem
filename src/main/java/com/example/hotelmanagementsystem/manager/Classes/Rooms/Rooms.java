package com.example.hotelmanagementsystem.manager.Classes.Rooms;

public abstract class Rooms {
    private int roomID;
    private String type;
    private double price;
    private String status;
    public Rooms(int id, String type, double price, String status) {
        this.roomID = id;
        this.type = type;
        this.price = price;
        this.status = status;
    }
    public int getRoomID()
    {
        return roomID;
    }
    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

}
