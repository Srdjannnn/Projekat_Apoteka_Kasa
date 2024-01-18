package apoteka;

import javax.swing.*;

public class ProveraServera extends JFrame {
    private int serverPort; // Port na kojem radi server za autentikaciju

    public ProveraServera(int serverPort) {
        this.serverPort = serverPort;

        setTitle("Provera Servera");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);

        JLabel infoLabel = new JLabel("Server za autentikaciju radi na portu: " + serverPort);
        panel.add(infoLabel);

        setLocationRelativeTo(null); // Centrirajte prozor
    }

    public void showProveraServeraWindow() {
        setVisible(true);
    }

    public static void main(String[] args) {
        // Unesite port na kojem radi server za autentikaciju
        int serverPort = 12345; // Primer port-a

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ProveraServera proveraServera = new ProveraServera(serverPort);
                proveraServera.showProveraServeraWindow();
            }
        });
    }
}
