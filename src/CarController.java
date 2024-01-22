package src;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarController {

    private Connection connection;

    public CarController(Connection connection) {
        this.connection = connection;
    }

   public void createCar(Car car) {
   String sql = "INSERT INTO Cars (CarID, Brand, Model, Year, Color, RentalRate) VALUES (?, ?, ?, ?, ? ,?)";

   try {
       PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, car.getCarID());
        pstmt.setString(2, car.getBrand());
        pstmt.setString(3, car.getModel());
        pstmt.setInt(4, car.getYear());
        pstmt.setString(5, car.getColor());
        pstmt.setDouble(6, car.getRentalRate());
       pstmt.executeUpdate();
   } catch (SQLException e) {
       e.printStackTrace();
   }
}


public List<Car> readCars() {

   String sql = "SELECT * FROM Cars";
   List<Car> cars = new ArrayList<>();

   try {
       PreparedStatement pstmt = connection.prepareStatement(sql);
       ResultSet rs = pstmt.executeQuery();

       while (rs.next()) {
           int carID = rs.getInt("CarID");
           String brand = rs.getString("Brand");
           String model = rs.getString("Model");
           int year = rs.getInt("Year");
           String color = rs.getString("Color");
           double rentalRate = rs.getDouble("RentalRate");
           Car car = new Car(carID, brand, model, year, color, rentalRate);
           cars.add(car);
       }
   } catch (SQLException e) {
       e.printStackTrace();
   }

   return cars; // Return the list of cars
}

public Car readCar(int carID) {
    String sql = "SELECT * FROM Cars WHERE CarID = ?";
    Car car = null;
 
    try {
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, carID);
        ResultSet rs = pstmt.executeQuery();
 
        if (rs.next()) {
            String brand = rs.getString("Brand");
            String model = rs.getString("Model");
            int year = rs.getInt("Year");
            String color = rs.getString("Color");
            double rentalRate = rs.getDouble("RentalRate");
            car = new Car(carID, brand, model, year, color, rentalRate);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
 
    return car; // Return the car with the specified carID
 }
 
public double readCarDailyRate(int carID) {
    String sql = "SELECT RentalRate FROM Cars WHERE CarID = ?";
 
    try {
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, carID);
        ResultSet rs = pstmt.executeQuery();
 
        if (rs.next()) {
            return rs.getDouble("RentalRate");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
 
    return 0.0; // Return 0.0 if car was not found or daily rate was not available
    
 }
 
public void updateCar(Car car) {
    String sql = "UPDATE Cars SET Brand = ?, Model = ?, Year = ?, Color = ?, RentalRate = ? WHERE CarID = ?";
  
    try {
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, car.getBrand());
        pstmt.setString(2, car.getModel());
        pstmt.setInt(3, car.getYear());
        pstmt.setString(4, car.getColor());
        pstmt.setDouble(5, car.getRentalRate());
        pstmt.setInt(6, car.getCarID());
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
 }
 

public void deleteCar(int carID) {
  String sqlCars = "DELETE FROM Cars WHERE CarID = ?";
  String sqlRentals = "DELETE FROM Rentals WHERE CarID = ?";

  try {
      PreparedStatement pstmtCars = connection.prepareStatement(sqlCars);
      pstmtCars.setInt(1, carID);
      pstmtCars.executeUpdate();

      PreparedStatement pstmtRentals = connection.prepareStatement(sqlRentals);
      pstmtRentals.setInt(1, carID);
      pstmtRentals.executeUpdate();
  } catch (SQLException e) {
      e.printStackTrace();
  }
}
}

