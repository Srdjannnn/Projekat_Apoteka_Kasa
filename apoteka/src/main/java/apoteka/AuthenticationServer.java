package apoteka;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationServer implements Runnable {
    private ServerSocket serverSocket;
    private Map<String, String> userCredentials; // Mapa za kredencijale
    private Map<String, String> userPrivileges;  // Mapa za privilegije

    public AuthenticationServer() {
        try {
            serverSocket = new ServerSocket(4321);
            userCredentials = new HashMap<>();
            userPrivileges = new HashMap<>();

            // Dodaj kredencijale
            userCredentials.put("admin", "admin");
            userCredentials.put("basic", "basic");

            // Dodaj privilegije
            userPrivileges.put("admin", "plus");
            userPrivileges.put("basic", "standard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            String credentials = input.readLine();
            String response = authenticateUser(credentials);
            output.println(response);

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String authenticateUser(String credentials) {
        System.out.println("Primljeni kredencijali: " + credentials);

        String[] parts = credentials.split(":");
        if (parts.length != 2) {
            return "neispravni podaci";
        }

        String username = parts[0];
        String password = parts[1];
        String storedPassword = userCredentials.get(username);

        if (storedPassword != null && storedPassword.equals(password)) {
            return userPrivileges.get(username); // standard ili plus
        } else {
            return "kredencijali nisu tačni";
        }
    }
}

