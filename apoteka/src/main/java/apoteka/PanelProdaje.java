package apoteka;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class PanelProdaje extends JPanel {
    private JTable tabelaProdaje;
    private DefaultTableModel modelTabele;

    public PanelProdaje() {
        setLayout(new BorderLayout());

        // Kreiranje tabele za prikaz prodaje
        modelTabele = new DefaultTableModel(new String[]{"Datum", "Ukupna Vrednost"}, 0);
        tabelaProdaje = new JTable(modelTabele);
        tabelaProdaje.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaProdaje.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(tabelaProdaje);
        add(scrollPane, BorderLayout.CENTER);

        ucitajIstorijuProdaje();
    }

    private void ucitajIstorijuProdaje() {
        // Ucitavanje sadržaja iz XML fajla racuni.xml i dodavanje u tabelu
        try {
            File xmlFile = new File("racuni.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;

            if (xmlFile.exists()) {
                doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();

                NodeList racuniList = doc.getElementsByTagName("racun");
                for (int i = 0; i < racuniList.getLength(); i++) {
                    Node racunNode = racuniList.item(i);
                    if (racunNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element racunElement = (Element) racunNode;
                        String datum = racunElement.getAttribute("datum");
                        String ukupnaVrednost = racunElement.getAttribute("ukupnaVrednost");
                        modelTabele.addRow(new String[]{datum, ukupnaVrednost});
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
