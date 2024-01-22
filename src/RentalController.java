package src;

import java.sql.*;
import java.time.LocalDate;

public class RentalController {

    private Connection connection;

    public RentalController(Connection connection) {
        this.connection = connection;
    }

   public void createRental(Rental rental) {
        String sql = "INSERT INTO Rentals (RentalID, CustomerID, CarID, RentalDate, ReturnDate, TotalCost) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, rental.getRentalID());
            pstmt.setInt(2, rental.getCustomerID());
            pstmt.setInt(3, rental.getCarID());
            pstmt.setDate(4, java.sql.Date.valueOf(rental.getRentalDate()));
            pstmt.setDate(5, java.sql.Date.valueOf(rental.getReturnDate()));
            pstmt.setDouble(6, rental.getTotalCost());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Rental readRental(int rentalID) {
        String sql = "SELECT * FROM Rentals WHERE RentalID = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, rentalID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int customerID = rs.getInt("CustomerID");
                int carID = rs.getInt("CarID");
                LocalDate rentalDate = rs.getObject("RentalDate",LocalDate.class);
                LocalDate returnDate = rs.getObject("ReturnDate",LocalDate.class);
                double totalCost = rs.getDouble("TotalCost");
                return new Rental(rentalID, customerID, carID, rentalDate, returnDate, totalCost);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if rental was not found
    }

    public void updateRental(int renterID, LocalDate rentStartDate, LocalDate rentEndDate) {
        String sql = "UPDATE Rentals SET RentalDate = ?, ReturnDate = ? WHERE RentalID = ?";
     
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setDate(1, java.sql.Date.valueOf(rentStartDate));
            pstmt.setDate(2, java.sql.Date.valueOf(rentEndDate));
            pstmt.setInt(3, renterID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
     }
     
     
    public void deleteRental(int rentalID) {
        String sql = "DELETE FROM Rentals WHERE RentalID = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, rentalID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



  