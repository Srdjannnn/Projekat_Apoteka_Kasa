package apoteka;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ProveraSlike extends JFrame {
    public ProveraSlike() {
        setTitle("Provera Slike");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);

        try {
            // Ucitaj sliku "testslika.jpg" iz istog direktorijuma kao Java klasa
            BufferedImage image = ImageIO.read(getClass().getResource("testslika.jpg"));

        
            // Kreiraj JLabel i postavi sliku kao ikonu
            JLabel label = new JLabel(new ImageIcon(image));
            panel.add(label);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLocationRelativeTo(null); // Centrirajte prozor
    }

    public void showProveraSlikeWindow() {
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ProveraSlike proveraSlike = new ProveraSlike();
                proveraSlike.showProveraSlikeWindow();
            }
        });
    }
}
