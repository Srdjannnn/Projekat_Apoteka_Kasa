package apoteka;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.File;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class ArtikliPanel extends JPanel {
    private JTextArea itemListTextArea;
    private JTextField barcodeField;
    private JButton printReceiptButton;
    private JButton clearButton;
    private JLabel totalLabel;
    private double ukupnaVrednost = 0.0;
    private Font itemListFont; // Dodajemo promenljivu za font
    private int itemCount = 0; // Dodajemo promenljivu za brojanje artikala

    public ArtikliPanel() {
        setLayout(new BorderLayout());

        itemListTextArea = new JTextArea();
        itemListTextArea.setEditable(false);
        itemListFont = new Font("Arial", Font.PLAIN, 16); //font
        itemListTextArea.setFont(itemListFont); // Postavljanje fonta 
        add(new JScrollPane(itemListTextArea), BorderLayout.CENTER);

        barcodeField = new JTextField(20);
        barcodeField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajArtikalPoBarkodu(barcodeField.getText());
                barcodeField.setText("");
            }
        });

        // Promena boje dugmeta u zelenu
        printReceiptButton = new JButton("Štampaj Račun");
        printReceiptButton.setBackground(Color.GREEN);
        printReceiptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stampajRacun();
            }
        });

        // Promena boje dugmeta ucrvenu
        clearButton = new JButton("Obriši");
        clearButton.setBackground(Color.RED);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obrisiRacun();
            }
        });

        JPanel barcodePanel = new JPanel();
        barcodePanel.add(new JLabel("Barkod:"));
        barcodePanel.add(barcodeField);
        barcodePanel.add(printReceiptButton);
        barcodePanel.add(clearButton);

        add(barcodePanel, BorderLayout.PAGE_END);

        totalLabel = new JLabel("Ukupno: 0.0 RSD");
        add(totalLabel, BorderLayout.PAGE_START);
    }

    // Setter metoda za font
    public void setItemListFont(Font font) {
        itemListFont = font;
        itemListTextArea.setFont(itemListFont);
    }

    private void dodajArtikalPoBarkodu(String barkod) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:apoteka.db");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM artikli WHERE barkod = ?")) {
            statement.setString(1, barkod);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String naziv = resultSet.getString("naziv");
                    double cena = resultSet.getDouble("cena");
                    
                    itemCount++; // Povećajte brojač količine
                    itemListTextArea.append(itemCount + ". " + naziv + " - " + cena + " RSD\n");
                    azurirajUkupnuVrednost(cena);
                } else {
                    JOptionPane.showMessageDialog(this, "Artikal sa datim barkodom nije pronađen.", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void azurirajUkupnuVrednost(double cena) {
        ukupnaVrednost += cena;
        totalLabel.setText("Ukupno: " + ukupnaVrednost + " RSD");
    }

    private void stampajRacun() {
        sacuvajRacunUXML();
        obrisiRacun();
    }

    private void sacuvajRacunUXML() {
        try {
            File xmlFile = new File("racuni.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;
    
            if (xmlFile.exists()) {
                // Ako fajl već postoji, otvaramo postojeći XML dokument
                doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();
            } else {
                // Ako fajl ne postoji, kreiramo novi XML dokument
                doc = dBuilder.newDocument();
                Element rootElement = doc.createElement("racuni");
                doc.appendChild(rootElement);
            }
    
            Element racun = doc.createElement("racun");
            racun.setAttribute("datum", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            racun.setAttribute("ukupnaVrednost", String.valueOf(ukupnaVrednost));
    
            // Dodajemo novi unos ispod prethodnih unosa
            doc.getDocumentElement().appendChild(racun);
    
            // Dodatno, treba da ažuriramo XML fajl sa novim sadržajem
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void obrisiRacun() {
        itemListTextArea.setText("");
        ukupnaVrednost = 0.0;
        itemCount = 0; // Resetujte brojač količine
        totalLabel.setText("Ukupno: 0.0 RSD");
    }
}
