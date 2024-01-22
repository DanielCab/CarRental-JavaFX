package src;

public class Customer {
    
    private int customerID;
    private String firstName;
    private String lastName;
    private String email;
    private int phone;
    private String address;

    // Constructor
    public Customer(int customerID, String firstName, String lastName, String email, int phone, String address) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
   // Getter methods
    public int getCustomerID() {
        return customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    // Setter methods
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() { 
    return "\n"+"\nCustomer ID: " + getCustomerID() + 
            "\nFirst Name: " + getFirstName() + 
            "\nLast Name: " + getLastName() + 
            "\nEmail: " + getEmail() + 
            "\nPhone: " + getPhone() + 
            "\nAddress: " + getAddress();
}
}

