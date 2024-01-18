package apoteka;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Stanje {
    private JFrame frame;
    private JButton dodajArtikalButton;
    private JButton prikaziArtikleButton; // Dodajemo dugme za prikaz artikala

    // Definisanje putanje do SQLite baze podataka
    private static final String DATABASE_URL = "jdbc:sqlite:apoteka.db";

    public Stanje() {
        frame = new JFrame("Stanje");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        dodajArtikalButton = new JButton("Dodaj Artikal");
        dodajArtikalButton.setPreferredSize(new Dimension(100, 50));
        dodajArtikalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Otvori prozor za unos stanja artikla
                otvoriProzorZaUnosStanja();
            }
        });

        prikaziArtikleButton = new JButton("Prikaži Artikle");
        prikaziArtikleButton.setPreferredSize(new Dimension(150, 50));
        prikaziArtikleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Otvara klasu Artikli za prikaz svih artikala
                new Artikli().show();
            }
        });

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
        buttonsPanel.add(dodajArtikalButton);
        buttonsPanel.add(prikaziArtikleButton); // Dodajemo dugme za prikaz artikala

        frame.add(buttonsPanel, BorderLayout.NORTH);

        // Inicijalizuj bazu podataka
        inicijalizujBazuPodataka();
    }

    private void otvoriProzorZaUnosStanja() {
        // Implementiranje prozora za unos stanja artikla
        // Koristi sqlite za povezivanje sa bazom podataka
        JFrame unosStanjaFrame = new JFrame("Unos Stanja Artikla");
        unosStanjaFrame.setSize(400, 300);
        unosStanjaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel unosStanjaPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5); 

        JLabel barkodLabel = new JLabel("Barkod:");
        JTextField barkodField = new JTextField(20);
        JLabel nazivLabel = new JLabel("Naziv:");
        JTextField nazivField = new JTextField(20);
        JLabel cenaLabel = new JLabel("Cena:");
        JTextField cenaField = new JTextField(20);
        JLabel kolicinaLabel = new JLabel("Količina:");
        JTextField kolicinaField = new JTextField(20);

        c.gridx = 0;
        c.gridy = 0;
        unosStanjaPanel.add(barkodLabel, c);
        c.gridy = 1;
        unosStanjaPanel.add(nazivLabel, c);
        c.gridy = 2;
        unosStanjaPanel.add(cenaLabel, c);
        c.gridy = 3;
        unosStanjaPanel.add(kolicinaLabel, c);
        c.gridx = 1;
        c.gridy = 0;
        unosStanjaPanel.add(barkodField, c);
        c.gridy = 1;
        unosStanjaPanel.add(nazivField, c);
        c.gridy = 2;
        unosStanjaPanel.add(cenaField, c);
        c.gridy = 3;
        unosStanjaPanel.add(kolicinaField, c);

        JButton sacuvajButton = new JButton("Sačuvaj");
        sacuvajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ocitaj podatke iz polja
                String barkod = barkodField.getText();
                String naziv = nazivField.getText();
                double cena = Double.parseDouble(cenaField.getText());
                int kolicina = Integer.parseInt(kolicinaField.getText());

                // Pozovi funkciju za unos u bazu podataka
                unesiArtikal(barkod, naziv, cena, kolicina);

                // Zatvori prozor za unos stanja
                unosStanjaFrame.dispose();
            }
        });

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2; // Dugme ce zauzimati dve kolone
        unosStanjaPanel.add(sacuvajButton, c);

        unosStanjaFrame.add(unosStanjaPanel);
        unosStanjaFrame.setVisible(true);
    }

    // Funkcija za kreiranje tabele ako ne postoji
    private void kreirajTabelu() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS artikli (" +
                "barkod TEXT PRIMARY KEY," +
                "naziv TEXT NOT NULL," +
                "cena REAL NOT NULL," +
                "kolicina INTEGER NOT NULL" +
                ")"
             )) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Funkcija za unos artikla u bazu podataka ili proemna kolicine ako artikal vec postoji
    public void unesiArtikal(String barkod, String naziv, double cena, int kolicina) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(
                "INSERT OR REPLACE INTO artikli (barkod, naziv, cena, kolicina) VALUES (?, ?, ?, ?)"
             )) {
            statement.setString(1, barkod);
            statement.setString(2, naziv);
            statement.setDouble(3, cena);
            statement.setInt(4, kolicina);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Inicijalizacija baze podataka
    private void inicijalizujBazuPodataka() {
        try {
            // Registruj SQLite JDBC drajver
            Class.forName("org.sqlite.JDBC");
            // Kreiraj tabelu ako ne postoji
            kreirajTabelu();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Stanje stanje = new Stanje();
                stanje.show();
            }
        });
    }
}
