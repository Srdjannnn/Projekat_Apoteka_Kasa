package apoteka;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class IstorijaProdaje extends JFrame {
    private PanelProdaje panelProdaje;
    private JButton obrisiIstorijuButton;

    public IstorijaProdaje() {
        setTitle("Istorija Prodaje");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel za unos podataka preko klase PanelProdaje
        panelProdaje = new PanelProdaje();
        add(panelProdaje, BorderLayout.CENTER);

        // Dugme za brisanje istorije prodaje
        obrisiIstorijuButton = new JButton("Obriši Istoriju Prodaje");
        obrisiIstorijuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obrisiIstorijuProdaje();
            }
        });
        add(obrisiIstorijuButton, BorderLayout.SOUTH);
    }

    private void obrisiIstorijuProdaje() {
        int odgovor = JOptionPane.showConfirmDialog(this, "Da li ste sigurni da želite da obrišete istoriju prodaje?", "Potvrda Brisanja", JOptionPane.YES_NO_OPTION);
        if (odgovor == JOptionPane.YES_OPTION) {
            // Obriši fajl "racuni.xml"
            File racuniFile = new File("racuni.xml");
            if (racuniFile.exists()) {
                racuniFile.delete();
                JOptionPane.showMessageDialog(this, "Istorija prodaje je uspešno obrisana.", "Brisanje Uspešno", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Nema dostupne istorije prodaje za brisanje.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                IstorijaProdaje istorijaProdaje = new IstorijaProdaje();
                istorijaProdaje.setVisible(true); // Prikazivanje prozora
            }
        });
    }
}
