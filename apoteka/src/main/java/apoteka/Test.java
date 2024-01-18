package apoteka;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test extends JFrame {
    public Test() {
        setTitle("Test");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        JButton proveriSlikeButton = new JButton("Proveri Slike");
        JButton proveriRacunButton = new JButton("Proveri Račun");
        JButton proveriServerButton = new JButton("Proveri Server");
        JButton proveraBazeButton = new JButton("Proveri Bazu");

        proveriSlikeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Otvora prozor klase ProveraSlike
                ProveraSlike proveraSlike = new ProveraSlike();
                proveraSlike.showProveraSlikeWindow();
            }
        });

        proveriRacunButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Otvora prozor klase ProveraRacuna
                ProveraRacuna proveraRacuna = new ProveraRacuna();
                proveraRacuna.showProveraRacunaWindow();
            }
        });

        proveriServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int serverPort = 12345; // broj porta koji proveravamo
                ProveraServera proveraServera = new ProveraServera(serverPort);
                proveraServera.setVisible(true);
            }
        });

        proveraBazeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String databaseFilePath = "apoteka.db"; //putanja do baze podataka
                ProveraBaze proveraBaze = new ProveraBaze(databaseFilePath);
                proveraBaze.setVisible(true);
            }
        });

        add(proveriSlikeButton);
        add(proveriRacunButton);
        add(proveriServerButton);
        add(proveraBazeButton);

        setLocationRelativeTo(null); 
    }

    public void showTestWindow() {
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Test test = new Test();
                test.showTestWindow();
            }
        });
    }
}
