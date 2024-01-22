package src;

public class Car {
    private int carID;
    private String brand;
    private String model;
    private int year;
    private String color;
    private double rentalRate;

    public Car(int carID, String brand, String model, int year, String color, double rentalRate) {
        this.carID = carID;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.rentalRate = rentalRate;
    }

    // Getter methods
    public int getCarID() {
        return carID;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public double getRentalRate() {
        return rentalRate;
    }

    // Setter methods
    public void setCarID(int carID) {
        this.carID = carID;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setRentalRate(double rentalRate) {
        this.rentalRate = rentalRate;
    }

@Override
public String toString() { 
    return "\n" + "\nCar ID: " + getCarID() + 
            "\nCar Brand:" + getBrand() + 
            "\nCar Model: " + getModel() + 
            "\nCar Year: " + getYear() + 
            "\nCar Color: " + getColor() + 
            "\nDaily Rental Rate: " + getRentalRate();
}

}

