package com.example.hotelmanagementsystem.manager.Classes.Rooms;


public class RoomFactory {
    public static Rooms createRoom(int id, String type, double price, String status) {
        switch (type) {
            case "Single":
                return new SingleRoom(id, price, status);
            case "Double":
                return new DoubleRoom(id,price, status);
            case "Triple":
                return new TripleRoom(id,price, status);
            default:
                throw new IllegalArgumentException("Unknown room type: " + type);
        }
    }
}
