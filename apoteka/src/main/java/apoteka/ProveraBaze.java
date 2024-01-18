package apoteka;

import javax.swing.*;

import java.io.File;

public class ProveraBaze extends JFrame {
    private String databaseFilePath; // Putanja do baze podataka

    public ProveraBaze(String databaseFilePath) {
        this.databaseFilePath = databaseFilePath;

        setTitle("Provera Baze Podataka");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);

        JLabel infoLabel;
        if (proveriPostojanjeBaze()) {
            infoLabel = new JLabel("Baza podataka " + databaseFilePath + " postoji.");
        } else {
            infoLabel = new JLabel("Baza podataka " + databaseFilePath + " ne postoji.");
        }

        panel.add(infoLabel);

        setLocationRelativeTo(null); // Centrirajte prozor
    }

    private boolean proveriPostojanjeBaze() {
        File databaseFile = new File(databaseFilePath);
        return databaseFile.exists();
    }

    public void showProveraBazeWindow() {
        setVisible(true);
    }

    public static void main(String[] args) {
        // Unesite putanju do baze podataka
        String databaseFilePath = "apoteka.db"; // Primer putanje

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ProveraBaze proveraBaze = new ProveraBaze(databaseFilePath);
                proveraBaze.showProveraBazeWindow();
            }
        });
    }
}
