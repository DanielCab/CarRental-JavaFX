// Server.java
package src;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 5556;

    // Hardcoded username and password for simplicity, normally this would be terrible practice since there is no encryption or security.
    private static final String VALID_USERNAME = "user";
    private static final String VALID_PASSWORD = "pass";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Use of threads to handle the client request.

                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            // Receive username and password from the client
            String receivedUsername = (String) inputStream.readObject();
            String receivedPassword = (String) inputStream.readObject();
     
            // Authenticate the user
            boolean isAuthenticated = authenticateUser(receivedUsername, receivedPassword);
     
            // Send the authentication result back to the client
            outputStream.writeObject(isAuthenticated);

     
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
     }
     

    private static boolean authenticateUser(String username, String password) {
        // Compare the received username and password with our harcoded values
        return VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password);
    }
}
