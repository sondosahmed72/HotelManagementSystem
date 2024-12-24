package com.example.hotelmanagementsystem.receptionist;
public class Resident {
    private int id;
    private String name;
    private String roomID;
    private String checkInDate;
    private String checkOutDate;
    private boolean hasSauna;
    private boolean hasSpaHammam;
    private boolean hasMassage;
    private boolean hasPrivateView;
    private double totalCost;
    public Resident(int id, String name, String roomID, String checkInDate, String checkOutDate, boolean hasSauna, boolean hasSpaHammam, boolean hasMassage, boolean hasPrivateView) {
        this.id = id;
        this.name = name;
        this.roomID = roomID;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.hasSauna = hasSauna;
        this.hasSpaHammam = hasSpaHammam;
        this.hasMassage = hasMassage;
        this.hasPrivateView = hasPrivateView;
        updateService();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void updateService() {
        Service service = new BasicService();
        if (hasSauna) {
            service = new SaunaDecorator(service);
        }
        if (hasSpaHammam) {
            service = new SpaHammamDecorator(service);
        }
        if (hasMassage) {
            service = new MassageDecorator(service);
        }
        if (hasPrivateView) {
            service = new PrivateViewDecorator(service);
        }
        totalCost = service.getCost();
    }
    public double getTotalCost() {
        return totalCost;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getRoomID() {
        return roomID;
    }
    public String getCheckInDate() {
        return checkInDate;
    }
    public String getCheckOutDate() {
        return checkOutDate;
    }
    public boolean isHasSauna() {
        return hasSauna;
    }
    public boolean isHasSpaHammam() {
        return hasSpaHammam;
    }
    public boolean isHasMassage() {
        return hasMassage;
    }
    public boolean isHasPrivateView() {
        return hasPrivateView;
    }
}