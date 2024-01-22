package src;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class App extends Application {

   Connection con;
   Stage window;

   List<Car> car = new ArrayList<>();
   ArrayList<Customer> customer = new ArrayList<>();
   ArrayList<Rental> rental = new ArrayList<>();

   public App() {
       try {
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop2","root","");
       } catch (SQLException err) {
           err.printStackTrace();
       }
   }

   @Override
   public void start(Stage primaryStage) {
       window = primaryStage;
       showLoginScreen();
       window.setTitle("Car Rental Application");
       window.show();
   }

   public static void main(String[] args) {
       launch(args);
   }

   private void showLoginScreen() {
       BorderPane root = new BorderPane();
       HBox loginBox = createLoginBox();
       root.setCenter(loginBox);

       Scene loginScene = new Scene(root, 400, 200);

       window.setScene(loginScene);
   }

   private HBox createLoginBox() {
       TextField usernameField = new TextField();
       PasswordField passwordField = new PasswordField();
       Button loginButton = new Button("Login");

       loginButton.setOnAction(e -> {
           String enteredUsername = usernameField.getText();
           String enteredPassword = passwordField.getText();
           authenticateAndShowMainScreen(enteredUsername, enteredPassword);
       });

       HBox loginBox = new HBox(10, usernameField, passwordField, loginButton);
       loginBox.setAlignment(Pos.CENTER);
       return loginBox;
   }

   private void authenticateAndShowMainScreen(String enteredUsername, String enteredPassword) {
    try (Socket socket = new Socket("localhost", 5556)) {
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(enteredUsername);
        outputStream.writeObject(enteredPassword);
 
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        
        // First, read the object as an Object
        Object obj = inputStream.readObject();
        
        // Check if it's a Boolean
        if (obj instanceof Boolean) {
            boolean isAuthenticated = (Boolean) obj;
            
            if (isAuthenticated) {
                showMainScreen();
            } else {
                System.out.println("Wrong username or password.");
            }
        } else {
            // If it's not a Boolean, assume it's a String (welcome message)
            System.out.println((String) obj);
        }
    } catch (IOException | ClassNotFoundException ex) {
        ex.printStackTrace();
    }
 }
 

   private void showMainScreen() {
       MenuBar menuBar = createMenuBar();
       BorderPane root = new BorderPane();
       root.setTop(menuBar);
       Scene mainScene = new Scene(root, 1024, 768);
       window.setScene(mainScene);
   }

   public MenuBar createMenuBar() {

       MenuBar menuBar = new MenuBar();
       Menu createMenu = new Menu("Create");
       Menu readMenu = new Menu("Read");
       Menu updateMenu = new Menu("Update");
       Menu deleteMenu = new Menu("Delete");
       menuBar.getMenus().addAll(createMenu, readMenu, updateMenu, deleteMenu);

       MenuItem createCar = new MenuItem("Add a new car");
       MenuItem createCustomer = new MenuItem("Add a new customer");
       MenuItem createRental = new MenuItem("Add new rental record");
       createMenu.getItems().addAll(createCar, createCustomer, createRental);

       MenuItem readCar = new MenuItem("View available cars");
       MenuItem readCustomer = new MenuItem("Retrieve customer information");
       MenuItem readRental = new MenuItem("View rental history");
       readMenu.getItems().addAll(readCar, readCustomer, readRental);

       MenuItem updateCar = new MenuItem("Update car information");
       MenuItem updateCustomer = new MenuItem("Update customer information");
       MenuItem updateRental = new MenuItem("Extend/End rental period");
       updateMenu.getItems().addAll(updateCar, updateCustomer, updateRental);

       MenuItem deleteCar = new MenuItem("Remove car");
       MenuItem deleteCustomer = new MenuItem("Remove costumer");
       MenuItem deleteRental = new MenuItem("Cancel a rental");
       deleteMenu.getItems().addAll(deleteCar, deleteCustomer, deleteRental);

       createCar.setOnAction(e -> CreateCarScene());
       createCustomer.setOnAction(e -> CreateCustomerScene());
       createRental.setOnAction(e -> CreateRentalScene());

       readCar.setOnAction(e -> ReadCarScene());
       readCustomer.setOnAction(e -> ReadCustomerScene());
       readRental.setOnAction(e -> ReadRentalScene());

       updateCar.setOnAction(e -> UpdateCarScene());
       updateCustomer.setOnAction(e -> UpdateCustomerScene());
       updateRental.setOnAction(e -> UpdateRentalScene());

       deleteCar.setOnAction(e -> DeleteCarScene());
       deleteCustomer.setOnAction(e -> DeleteCustomerScene());
       deleteRental.setOnAction(e -> DeleteRentalScene());

       return menuBar;
   }

   
   public void CreateCarScene() {

       BorderPane root = new BorderPane();
       root.setTop(createMenuBar());

       TextField carID = new TextField();
       Text carIDText = new Text(" Enter Car ID: ");
       HBox carIDBox = new HBox(carIDText, carID);
       carIDBox.setAlignment(Pos.CENTER);

        TextField carBrand = new TextField();
       Text brandText = new Text(" Enter Car Brand: ");
       HBox carBrandBox = new HBox(brandText, carBrand);
       carBrandBox.setAlignment(Pos.CENTER);

        TextField carModel = new TextField();
       Text modelText = new Text(" Enter Car Model: ");
       HBox carModelBox = new HBox(modelText, carModel);
       carModelBox.setAlignment(Pos.CENTER);

        TextField carYear = new TextField();
       Text yearText = new Text(" Enter Car Year: ");
       HBox carYearBox = new HBox(yearText, carYear);
       carYearBox.setAlignment(Pos.CENTER);
    

        TextField carColor = new TextField();
       Text colorText = new Text(" Enter Car Color: ");
       HBox carColorBox = new HBox(colorText, carColor);
       carColorBox.setAlignment(Pos.CENTER);

        TextField carRentalRate = new TextField();
       Text carRRText = new Text(" Enter Car Rental Rate: ");
       HBox carRentalBox = new HBox(carRRText, carRentalRate);
       carRentalBox.setAlignment(Pos.CENTER);

       Button confirmBttn = new Button("Submit");

        confirmBttn.setOnAction(e -> {

        
        Car newCar = new Car(0, null, null, 0, null, 0);

            newCar.setCarID(Integer.parseInt(carID.getText()));
            newCar.setBrand(carBrand.getText());
            newCar.setModel(carModel.getText());
            newCar.setYear(Integer.parseInt(carYear.getText()));
            newCar.setColor(carColor.getText());
            newCar.setRentalRate(Double.parseDouble(carRentalRate.getText()));
            
            car.add(newCar);

            // Create an instance of CarController
            CarController carController = new CarController(con);

            // Call the createCar method
            carController.createCar(newCar);
            

        });


       VBox content = new VBox(20);

       content.getChildren().addAll(carIDBox, carBrandBox, carModelBox, carYearBox, carColorBox, carRentalBox, confirmBttn);
       content.setAlignment(Pos.CENTER);
       Scene scene = new Scene(root, 1024, 768);
       root.setCenter(content);
       window.setScene(scene);
   }

   public void CreateCustomerScene() {

       BorderPane root = new BorderPane();
       root.setTop(createMenuBar());

       TextField customerID = new TextField();
       Text customerIDText = new Text(" Enter Customer ID: ");
       HBox customerIDBox = new HBox(customerIDText, customerID);
       customerIDBox.setAlignment(Pos.CENTER);

        TextField firstName = new TextField();
       Text FNText = new Text(" Enter First Name: ");
       HBox FNBox = new HBox(FNText, firstName);
       FNBox.setAlignment(Pos.CENTER);

        TextField lastName = new TextField();
       Text LNText = new Text(" Enter Last Name: ");
       HBox LNBox = new HBox(LNText, lastName);
       LNBox.setAlignment(Pos.CENTER);

        TextField email = new TextField();
       Text emailText = new Text(" Enter Email: ");
       HBox emailBox = new HBox(emailText, email);
       emailBox.setAlignment(Pos.CENTER);

        TextField phone = new TextField();
       Text phoneText = new Text(" Enter Phone Number: ");
       HBox phoneBox = new HBox(phoneText, phone);
       phoneBox.setAlignment(Pos.CENTER);

        TextField address = new TextField();
       Text addressText = new Text(" Enter Address: ");
       HBox addressBox = new HBox(addressText, address);
       addressBox.setAlignment(Pos.CENTER);

       Button confirmBttn = new Button("Submit");

       confirmBttn.setOnAction(e -> {

        // Create a new Customer object
        Customer newCustomer = new Customer(0, null, null, null, 0, null);
    
        // Use the setters to set the properties of the new customer
        newCustomer.setCustomerID(Integer.parseInt(customerID.getText()));
        newCustomer.setFirstName(firstName.getText());
        newCustomer.setLastName(lastName.getText());
        newCustomer.setEmail(email.getText());
        newCustomer.setPhone(Integer.parseInt(phone.getText()));
        newCustomer.setAddress(address.getText());
    
        customer.add(newCustomer);

        CustomerController customerController = new CustomerController(con);
        
        // Call a method to handle the new customer (if it exists)
        customerController.createCustomer(newCustomer);

    });
    

       VBox content = new VBox(20);

       content.getChildren().addAll(customerIDBox, FNBox, LNBox, emailBox, phoneBox, addressBox, confirmBttn);
       content.setAlignment(Pos.CENTER);
       Scene scene = new Scene(root, 1024, 768);
       root.setCenter(content);
       window.setScene(scene);
   }

   public void CreateRentalScene() {

       BorderPane root = new BorderPane();
       root.setTop(createMenuBar());

       TextField renterID = new TextField();
       Text renterIDText = new Text(" Enter Renters ID: ");
       HBox renterIDBox = new HBox(renterIDText, renterID);
       renterIDBox.setAlignment(Pos.CENTER);

        TextField customerID = new TextField();
       Text customerIDText = new Text(" Enter Customer ID: ");
       HBox customerIDBox = new HBox(customerIDText, customerID);
       customerIDBox.setAlignment(Pos.CENTER);

        TextField carID = new TextField();
       Text carIDText = new Text(" Enter Car ID: ");
       HBox carIDBox = new HBox(carIDText, carID);
       carIDBox.setAlignment(Pos.CENTER);

       Text rentStartText = new Text(" Enter Rent Start Date: ");
       DatePicker rentStartPicker = new DatePicker();
       HBox rentStartBox = new HBox(rentStartText, rentStartPicker);
       rentStartBox.setAlignment(Pos.CENTER);

       Text rentEndText = new Text(" Enter Rent End Date: ");
       DatePicker rentEndPicker = new DatePicker();
       HBox rentEndBox = new HBox(rentEndText, rentEndPicker);
       rentEndBox.setAlignment(Pos.CENTER);

       Button confirmBttn = new Button("Submit");

       confirmBttn.setOnAction(e -> {
        // Create a new Rental object
        Rental newRental = new Rental(0, 0, 0, null, null, 0.0);
       
        // Use the setters to set the properties of the new rental
        newRental.setRentalID(Integer.parseInt(renterID.getText()));
        newRental.setCustomerID(Integer.parseInt(customerID.getText()));
        newRental.setCarID(Integer.parseInt(carID.getText()));
        
        // Parse the text to the variables rentalDate and returnDate
        LocalDate rentalDate = rentStartPicker.getValue();
        LocalDate returnDate = rentEndPicker.getValue();

        newRental.setRentalDate(rentalDate);
        newRental.setReturnDate(returnDate);
        
        // Get the car with the given ID
        RentalController rentCon = new RentalController(con);
        CarController carCon = new CarController(con);
     
        int inputtedID = Integer.parseInt(carID.getText());
        double dailyRate = carCon.readCarDailyRate(inputtedID);
     
        // Calculate the total cost
        long days = ChronoUnit.DAYS.between(rentalDate, returnDate);
        double totalCost = dailyRate * days;
        
        newRental.setTotalCost(totalCost);
       
        rental.add(newRental);
       
        // Call a method to handle the new rental
        rentCon.createRental(newRental);
     });
     
       
       VBox content = new VBox(20);

       content.getChildren().addAll(renterIDBox, customerIDBox, carIDBox, rentStartBox, rentEndBox, confirmBttn);
       content.setAlignment(Pos.CENTER);
       Scene scene = new Scene(root, 1024, 768);
       root.setCenter(content);
       window.setScene(scene);
   }

   public void ReadCarScene() {
    
        Text availableCarText = new Text("Cars Available for Rent: ");
        TextArea availableCarArea = new TextArea();
        availableCarArea.setMinHeight(600);
        availableCarArea.setEditable(false);
        VBox content = new VBox(30);

    CarController carController = new CarController(con);

        List<Car> cars = carController.readCars();
        for (Car car : cars) {
          availableCarArea.appendText(car.toString());
        }
     
        
        content.getChildren().addAll(availableCarText, availableCarArea);


        BorderPane root = new BorderPane();
        root.setTop(createMenuBar());   
        root.setCenter(content);
        content.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1024, 768);
        window.setScene(scene);
   }

   public void ReadCustomerScene() {
       
        Text customerInfoText = new Text("Enter Customer ID to view information: ");
        TextField customerInfo = new TextField();
        Button customerBttn = new Button("Search");

        TextArea customerArea = new TextArea();
        customerArea.setMinHeight(400);
        customerArea.setEditable(false);


        customerBttn.setOnAction(e-> {
            customerArea.clear();

            CustomerController customerController = new CustomerController(con);

                int customerID = Integer.parseInt(customerInfo.getText());
                Customer customer = customerController.readCustomer(customerID);
                if (customer != null) {
                customerArea.setText(customer.toString());
                } else {
                customerArea.setText("Customer not found");
                }
        
        });

        

        HBox enterTextBox = new HBox(20);
        enterTextBox.getChildren().addAll(customerInfoText, customerInfo, customerBttn);
        enterTextBox.setAlignment(Pos.CENTER);
        VBox content = new VBox(30);
        content.getChildren().addAll(enterTextBox, customerArea);

       
        BorderPane root = new BorderPane();
        root.setTop(createMenuBar());   
        root.setCenter(content);
        content.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 1024, 768);
        window.setScene(scene);
   }

   public void ReadRentalScene() {

       Text rentalInfoText = new Text("Enter Rental ID to view rental history: ");
        TextField rentalInfo = new TextField();
        Button rentalBttn = new Button("Search");

        TextArea rentalRecordArea = new TextArea();
        rentalRecordArea.setMinHeight(400);
        rentalRecordArea.setEditable(false);

        HBox enterTextBox = new HBox(20);
        enterTextBox.getChildren().addAll(rentalInfoText, rentalInfo, rentalBttn);
        enterTextBox.setAlignment(Pos.CENTER);
        VBox content = new VBox(30);
        content.getChildren().addAll(enterTextBox, rentalRecordArea);

            rentalBttn.setOnAction(e -> { 
            rentalRecordArea.clear();

            RentalController rentalController = new RentalController(con);

                int rentalID = Integer.parseInt(rentalInfo.getText());
                Rental rental = rentalController.readRental(rentalID);
                if (rental != null) {
                rentalRecordArea.setText(rental.toString());
                } else {
                rentalRecordArea.setText("Rental not found");
                }
        
        });

       
        BorderPane root = new BorderPane();
        root.setTop(createMenuBar());   
        root.setCenter(content);
        content.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 1024, 768);
        window.setScene(scene);
   }

   public void UpdateCarScene() {
    
       BorderPane root = new BorderPane();
       root.setTop(createMenuBar());

       TextField carID = new TextField();
       Text carIDText = new Text(" Enter Car ID: ");
        Button confirmBttn = new Button("Submit");
       HBox carIDBox = new HBox(10,carIDText, carID, confirmBttn);
       carIDBox.setAlignment(Pos.CENTER);

        TextField carBrand = new TextField();
       Text brandText = new Text("Car Brand: ");
       HBox carBrandBox = new HBox(brandText, carBrand);
       carBrandBox.setAlignment(Pos.CENTER);

        TextField carModel = new TextField();
       Text modelText = new Text("Car Model: ");
       HBox carModelBox = new HBox(modelText, carModel);
       carModelBox.setAlignment(Pos.CENTER);

        TextField carYear = new TextField();
       Text yearText = new Text("Car Year: ");
       HBox carYearBox = new HBox(yearText, carYear);
       carYearBox.setAlignment(Pos.CENTER);
    
        TextField carColor = new TextField();
       Text colorText = new Text("Car Color: ");
       HBox carColorBox = new HBox(colorText, carColor);
       carColorBox.setAlignment(Pos.CENTER);

        TextField carRentalRate = new TextField();
       Text carRRText = new Text(" Car Rental Rate: ");
       HBox carRentalBox = new HBox(carRRText, carRentalRate);
       carRentalBox.setAlignment(Pos.CENTER);

       Button saveChangesBttn = new Button("Save Changes");

       VBox content = new VBox(20);

       

           confirmBttn.setOnAction( e -> {

                CarController carCon = new CarController(con);
                int inputtedID = Integer.parseInt(carID.getText());

                // Get the Car object associated with the carID
                Car car = carCon.readCar(inputtedID);
        
                if (car != null) {
                    carID.setText(String.valueOf(car.getCarID()));
                    carBrand.setText(car.getBrand());
                    carModel.setText(car.getModel());
                    carYear.setText(String.valueOf(car.getYear()));
                    carColor.setText(car.getColor());
                    carRentalRate.setText(String.valueOf(car.getRentalRate()));
                }

   });

        saveChangesBttn.setOnAction(event-> {

            CarController carCon = new CarController(con);

            Car newCar = new Car(0, STYLESHEET_CASPIAN, STYLESHEET_MODENA, 0, STYLESHEET_CASPIAN, 0);

                newCar.setRentalRate(Double.parseDouble(carRentalRate.getText()));
                newCar.setBrand(carBrand.getText());
                newCar.setModel(carModel.getText());
                newCar.setCarID(Integer.parseInt(carID.getText()));
                newCar.setColor(carColor.getText());
                newCar.setYear(Integer.parseInt(carYear.getText()));

                    carCon.updateCar(newCar);
            });

       content.getChildren().addAll(carIDBox,carBrandBox, carModelBox, carYearBox, carColorBox, carRentalBox,saveChangesBttn);
       content.setAlignment(Pos.CENTER);
       Scene scene = new Scene(root, 1024, 768);
       root.setCenter(content);
       window.setScene(scene);
   }

   public void UpdateCustomerScene() {

      BorderPane root = new BorderPane();
       root.setTop(createMenuBar());

       TextField customerID = new TextField();
       Text customerIDText = new Text(" Enter Customer ID: ");
       Button confirmBttn = new Button("Submit");
       HBox customerIDBox = new HBox(10, customerIDText, customerID, confirmBttn);
       customerIDBox.setAlignment(Pos.CENTER);

        TextField firstName = new TextField();
       Text FNText = new Text("First Name: ");
       HBox FNBox = new HBox(FNText, firstName);
       FNBox.setAlignment(Pos.CENTER);

        TextField lastName = new TextField();
       Text LNText = new Text("Last Name: ");
       HBox LNBox = new HBox(LNText, lastName);
       LNBox.setAlignment(Pos.CENTER);

        TextField email = new TextField();
       Text emailText = new Text("Email: ");
       HBox emailBox = new HBox(emailText, email);
       emailBox.setAlignment(Pos.CENTER);

        TextField phone = new TextField();
       Text phoneText = new Text("Phone Number: ");
       HBox phoneBox = new HBox(phoneText, phone);
       phoneBox.setAlignment(Pos.CENTER);

        TextField address = new TextField();
       Text addressText = new Text("Address: ");
       HBox addressBox = new HBox(addressText, address);
       addressBox.setAlignment(Pos.CENTER);


      Button saveChangesBttn = new Button("Save Changes");


        confirmBttn.setOnAction( e -> {

        CustomerController cusCon = new CustomerController(con);
         int inputtedID = Integer.parseInt(customerID.getText());

         // Get the Customer object associated with the customerID
         Customer customer = cusCon.readCustomer(inputtedID);
   
         if (customer != null) {
            firstName.setText(customer.getFirstName());
            lastName.setText(customer.getLastName());
            email.setText(customer.getEmail());
            phone.setText(String.valueOf(customer.getPhone()));
            address.setText(customer.getAddress());
         }

   });

        saveChangesBttn.setOnAction(event-> {

            CustomerController cusCon = new CustomerController(con);

            Customer newCustomer = new Customer(0, null, null, null, 0, null);

                newCustomer.setFirstName(firstName.getText());
                newCustomer.setLastName(lastName.getText());
                newCustomer.setEmail(email.getText());
                newCustomer.setPhone(Integer.parseInt(phone.getText()));
                newCustomer.setAddress(address.getText());
                newCustomer.setCustomerID(Integer.parseInt(customerID.getText()));

                    cusCon.updateCustomer(newCustomer);
            });

        VBox content = new VBox(20);
       content.getChildren().addAll(customerIDBox, FNBox, LNBox, emailBox, phoneBox, addressBox, saveChangesBttn);
       content.setAlignment(Pos.CENTER);
       Scene scene = new Scene(root, 1024, 768);
       root.setCenter(content);
       window.setScene(scene);
   }

    public void UpdateRentalScene() {

     BorderPane root = new BorderPane();
       root.setTop(createMenuBar());

       TextField renterID = new TextField();
       Text renterIDText = new Text(" Enter Renters ID: ");
       Button confirmBttn = new Button("Submit");
       HBox renterIDBox = new HBox(10,renterIDText, renterID, confirmBttn);
       renterIDBox.setAlignment(Pos.CENTER);

       
       Text rentStartText = new Text("Rent Start Date: ");
       DatePicker rentStartPicker = new DatePicker();
       HBox rentStartBox = new HBox(rentStartText, rentStartPicker);
       rentStartBox.setAlignment(Pos.CENTER);

        
       Text rentEndText = new Text("Rent End Date: ");
       DatePicker rentEndPicker = new DatePicker();
       HBox rentEndBox = new HBox(rentEndText, rentEndPicker);
       rentEndBox.setAlignment(Pos.CENTER);

       Button saveChangesBttn = new Button("Save Changes");

       VBox content = new VBox(20);

       confirmBttn.setOnAction( e -> {

        RentalController rentCon = new RentalController(con);
         int inputtedID = Integer.parseInt(renterID.getText());

         // Get the Rental object associated with the renterID
         Rental rental = rentCon.readRental(inputtedID);
   
         if (rental != null) {
            rentStartPicker.setValue(rental.getRentalDate());
            rentEndPicker.setValue(rental.getReturnDate());
         }

   });

        saveChangesBttn.setOnAction(event-> {

             int inputtedID = Integer.parseInt(renterID.getText());

                RentalController rentCon = new RentalController(con);

                LocalDate updatedRentStart = rentStartPicker.getValue();
                LocalDate updatedRentEnd = rentEndPicker.getValue();

                rentCon.updateRental(inputtedID, updatedRentStart, updatedRentEnd);

            });

       content.getChildren().addAll(renterIDBox, rentStartBox, rentEndBox, saveChangesBttn);
       content.setAlignment(Pos.CENTER);
       Scene scene = new Scene(root, 1024, 768);
       root.setCenter(content);
       window.setScene(scene);
   }

      public void DeleteCarScene() {

       BorderPane root = new BorderPane();
       root.setTop(createMenuBar());

       TextField deleteCar = new TextField();
       Text deleteCarText = new Text(" Enter Car ID to delete record: ");
       HBox deleteCarBox = new HBox(10,deleteCarText, deleteCar);
       deleteCarBox.setAlignment(Pos.CENTER);

       Button deleteButton = new Button("Delete");


       VBox content = new VBox(20);

       

        deleteButton.setOnAction(event -> {

            int inputtedID = Integer.parseInt(deleteCar.getText());

            CarController carCon = new CarController(con);

            deleteCar.clear();

            for (Car cr : car) {
                car.removeIf(c -> cr.getCarID() == inputtedID);
            }
                   carCon.deleteCar(inputtedID); 
        });

       content.getChildren().addAll(deleteCarBox,deleteButton);
       content.setAlignment(Pos.CENTER);
       Scene scene = new Scene(root, 1024, 768);
       root.setCenter(content);
       window.setScene(scene);

   }

      public void DeleteCustomerScene() {

        BorderPane root = new BorderPane();
        root.setTop(createMenuBar());

        TextField deleteCustomer = new TextField();
        Text deleteCustomerText = new Text(" Enter Customer ID to delete record: ");
        HBox deleteCustomerBox = new HBox(10,deleteCustomerText, deleteCustomer);
        deleteCustomerBox.setAlignment(Pos.CENTER);

        Button deleteButton = new Button("Delete");

        VBox content = new VBox(20);

            deleteButton.setOnAction(event -> {

                 int inputtedID = Integer.parseInt(deleteCustomer.getText());

            CustomerController cusCon = new CustomerController(con);

            deleteCustomer.clear();

            for (Customer cu : customer) {
                car.removeIf(c -> cu.getCustomerID() == inputtedID);
            }
                   cusCon.deleteCustomer(inputtedID);
        });

        content.getChildren().addAll(deleteCustomerBox,deleteButton);
        content.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1024, 768);
        root.setCenter(content);
        window.setScene(scene);
   }

      public void DeleteRentalScene() {

       BorderPane root = new BorderPane();
        root.setTop(createMenuBar());

        TextField deleteRental = new TextField();
        Text deleteRentalText = new Text(" Enter Rental ID to delete record: ");
        HBox deleteRentalBox = new HBox(10,deleteRentalText, deleteRental);
        deleteRentalBox.setAlignment(Pos.CENTER);

        Button deleteButton = new Button("Delete");

        VBox content = new VBox(20);

            deleteButton.setOnAction(event -> {

            int inputtedID = Integer.parseInt(deleteRental.getText());

            RentalController renCon = new RentalController(con);

            deleteRental.clear();

            for (Rental r : rental) {
                car.removeIf(c -> r.getCustomerID() == inputtedID);
            }
                   renCon.deleteRental(inputtedID);
        });

        content.getChildren().addAll(deleteRentalBox,deleteButton);
        content.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1024, 768);
        root.setCenter(content);
        window.setScene(scene);
   }



}
