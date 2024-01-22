package src;

import java.time.LocalDate;

public class Rental {

    private int rentalID;
    private int customerID;
    private int carID;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private double totalCost;

    // Constructor
    public Rental(int rentalID, int customerID, int carID, LocalDate rentalDate, LocalDate returnDate,double totalCost) {
        this.rentalID = rentalID;
        this.customerID = customerID;
        this.carID = carID;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.totalCost = totalCost;
    }

    // Getters and Setters
    
    public int getRentalID() {
        return rentalID;
    }

    public void setRentalID(int rentalID) {
        this.rentalID = rentalID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "\n" + "\nRental ID: "  + getRentalID() + 
                "\nCustomer ID: " + getCustomerID() + 
                "\nCar ID: "  + getCarID() + 
                "\nRental Date: " + getRentalDate() + 
                "\nReturn Date: " + getReturnDate() + 
                "\nTotal Cost: " + getTotalCost(); 
    }
}

