package Parking;


import java.time.LocalDateTime;

public class ParkArea {

    private boolean spotInUse;
    private String carPlateNumber;
    private LocalDateTime dateTime;
    private int parkSpotNumber;
    private double price;

    public ParkArea(int parkSpotNumber) {
        this.parkSpotNumber = parkSpotNumber;
    }

    public ParkArea(String carPlateNumber, int parkSpotNumber) {
        spotInUse = true;
        this.carPlateNumber = carPlateNumber;
        this.parkSpotNumber = parkSpotNumber;
        dateTime = LocalDateTime.now();

    }

    public boolean getSpotInUse() {
        return spotInUse;
    }

    public void setSpotInUse(boolean spotInUse) {
        this.spotInUse = spotInUse;
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }

    public LocalDateTime getDateTime(){
        return dateTime;
    }

    public void setDateTime(LocalDateTime DateTime) {
        this.dateTime = DateTime;
    }

    public int getParkSpotNumber() {
        return parkSpotNumber;
    }

    public void setParkSpotNumber(int parkSpotNumber) {
        this.parkSpotNumber = parkSpotNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
