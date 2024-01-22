package src;

import java.sql.*;

public class CustomerController {

    private Connection connection;

    public CustomerController(Connection connection) {
        this.connection = connection;
    }

    public void createCustomer(Customer customer) {
        String sql = "INSERT INTO Customers (CustomerID, FirstName, LastName, Email, Phone, Address) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, customer.getCustomerID());
            pstmt.setString(2, customer.getFirstName());
            pstmt.setString(3, customer.getLastName());
            pstmt.setString(4, customer.getEmail());
            pstmt.setInt(5, customer.getPhone());
            pstmt.setString(6, customer.getAddress());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer readCustomer(int customerID) {
        String sql = "SELECT * FROM Customers WHERE CustomerID = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, customerID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String email = rs.getString("Email");
                int phone = rs.getInt("Phone");
                String address = rs.getString("Address");
                return new Customer(customerID, firstName, lastName, email, phone, address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if customer was not found
    }

    public void updateCustomer(Customer customer) {
        String sql = "UPDATE Customers SET FirstName = ?, LastName = ?, Email = ?, Phone = ?, Address = ? WHERE CustomerID = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setInt(4, customer.getPhone());
            pstmt.setString(5, customer.getAddress());
            pstmt.setInt(6, customer.getCustomerID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 public void deleteCustomer(int customerID) {
   String sqlCustomers = "DELETE FROM Customers WHERE CustomerID = ?";
   String sqlRentals = "DELETE FROM Rentals WHERE CustomerID = ?";

   try {
       PreparedStatement pstmtCustomers = connection.prepareStatement(sqlCustomers);
       pstmtCustomers.setInt(1, customerID);
       pstmtCustomers.executeUpdate();

       PreparedStatement pstmtRentals = connection.prepareStatement(sqlRentals);
       pstmtRentals.setInt(1, customerID);
       pstmtRentals.executeUpdate();
   } catch (SQLException e) {
       e.printStackTrace();
   }
 }
}

