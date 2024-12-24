package com.example.hotelmanagementsystem.receptionist.Strategy;

import com.example.hotelmanagementsystem.receptionist.Models.Room;

// Factory for Room Creation
public class RoomFactory {
    public static Room createRoom(int id, String type, String status, String boardingOption) {
        double price;
        switch (type) {
            case "Single":
                price = 100.0;
                break;
            case "Double":
                price = 150.0;
                break;
            case "Triple":
                price = 200.0;
                break;
            default:
                throw new IllegalArgumentException("Invalid Room Type");
        }

        BoardingOption option = BoardingOptionFactory.createBoardingOption(boardingOption);
        return new Room(id, type, price, status);
    }
}
