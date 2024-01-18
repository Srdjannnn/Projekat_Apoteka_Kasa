package apoteka;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class LoginWindow {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton serverButton;
    private AuthenticationServer server; // Dodaj referencu na server
    private boolean isServerRunning = false; // Dodaj flag za proveru da li je server pokrenut

    public LoginWindow() {
        frame = new JFrame("Login");
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        usernameField = new JTextField();
        usernameField.setBounds(50, 30, 200, 30);
        frame.add(usernameField);

        passwordField = new JPasswordField();
        passwordField.setBounds(50, 70, 200, 30);
        frame.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(50, 110, 200, 30);
        frame.add(loginButton);

        serverButton = new JButton("Server");
        serverButton.setBounds(50, 150, 200, 30);
        serverButton.setBackground(Color.RED);// crvena default
        frame.add(serverButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                login(username, password);
            }
        });

        serverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isServerRunning) {
                    server = new AuthenticationServer(); // Pokrece server
                    new Thread(server).start(); // Pokrece server u novoj niti
                    isServerRunning = true;
                    serverButton.setBackground(Color.GREEN); // Menja boju u zeleno
                }
            }
        });
    }

    public void login(String username, String password) {
        try {
            Socket socket = new Socket("localhost", 4321);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    
            out.println(username + ":" + password);
    
            String response = in.readLine();
    
            socket.close();
    
            if ("plus".equals(response)) {
                // Pokrece Plus klasu
                PlusWindow plusWindow = new PlusWindow();
                plusWindow.show();
            } else if ("standard".equals(response)) {
                // Pokrece Basic klasu
                BasicWindow basicWindow = new BasicWindow();
                basicWindow.show();
            } else {
                JOptionPane.showMessageDialog(frame, "Kredencijali nisu tačni");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Ne može se uspostaviti veza sa serverom");
        }
    }
    

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.show();
    }
}
