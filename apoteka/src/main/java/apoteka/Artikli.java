package apoteka;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public class Artikli {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;

    // Definisanje putanje do SQLite baze podataka
    private static final String DATABASE_URL = "jdbc:sqlite:apoteka.db";

    public Artikli() {
        frame = new JFrame("Artikli");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Kreiramo model tabele
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Barkod");
        tableModel.addColumn("Naziv");
        tableModel.addColumn("Cena");
        tableModel.addColumn("Količina");

        // Kreiramo tabelu sa datim modelom
        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Dodajemo dugme za brisanje artikla
        JButton obrisiArtikalButton = new JButton("Obriši Artikal");
        obrisiArtikalButton.addActionListener(e -> obrisiSelektovaniArtikal());

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(obrisiArtikalButton, BorderLayout.SOUTH);

        // Ucitavamo podatke iz baze i prikazujemo ih u tabeli
        ucitajPodatkeIzBaze();

        // Postavljamo da se redovi automatski sortiraju po barkodu
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        sorter.toggleSortOrder(0);

        frame.setVisible(true);
    }

    // Funkcija za ucitavanje podataka iz baze i prikazivanje u tabeli
    private void ucitajPodatkeIzBaze() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(
                "SELECT barkod, naziv, cena, kolicina FROM artikli"
             );
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String barkod = resultSet.getString("barkod");
                String naziv = resultSet.getString("naziv");
                double cena = resultSet.getDouble("cena");
                int kolicina = resultSet.getInt("kolicina");
                tableModel.addRow(new Object[]{barkod, naziv, cena, kolicina});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Funkcija za brisanje selektovanog artikla iz baze i tabele
    private void obrisiSelektovaniArtikal() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Pitamo korisnika da li želi da obrise artikal
            int option = JOptionPane.showConfirmDialog(frame, "Da li želite da obrišete odabrani artikal?", "Potvrda brisanja", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                // Ako korisnik potvrdi brisanje, obrisemo artikal iz baze
                String barkod = (String) tableModel.getValueAt(selectedRow, 0);
                obrisiArtikalIzBaze(barkod);

                // Zatim uklonimo ga iz tabele
                tableModel.removeRow(selectedRow);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Molimo vas da prvo odaberete artikal za brisanje.", "Niste odabrali artikal", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Funkcija za brisanje artikla iz baze
    private void obrisiArtikalIzBaze(String barkod) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM artikli WHERE barkod = ?"
             )) {
            statement.setString(1, barkod);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Dodajte ovu metodu za dohvatanje svih artikala iz baze
    public List<Artikal> dohvatiSveArtikle() {
        List<Artikal> artikli = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(
                "SELECT barkod, naziv, cena, kolicina FROM artikli"
             );
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String barkod = resultSet.getString("barkod");
                String naziv = resultSet.getString("naziv");
                double cena = resultSet.getDouble("cena");
                int kolicina = resultSet.getInt("kolicina");
                Artikal artikal = new Artikal(barkod, naziv, cena, kolicina);
                artikli.add(artikal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artikli;
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Artikli artikli = new Artikli();
            artikli.show();
        });
    }
}
