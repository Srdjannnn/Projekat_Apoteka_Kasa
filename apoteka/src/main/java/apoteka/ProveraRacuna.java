package apoteka;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ProveraRacuna extends JFrame {
    public ProveraRacuna() {
        setTitle("Provera Računa");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);

        JButton proveriButton = new JButton("Proveri Da Li Postoji Račun");
        panel.add(proveriButton);

        proveriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proveriPostojanjeRacuna();
            }
        });

        setLocationRelativeTo(null); 
    }

    private void proveriPostojanjeRacuna() {
        File racuniFile = new File("racuni.xml");
        if (racuniFile.exists()) {
            JOptionPane.showMessageDialog(this, "Fajl 'racuni.xml' postoji u projektu.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Fajl 'racuni.xml' ne postoji u projektu.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void showProveraRacunaWindow() {
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ProveraRacuna proveraRacuna = new ProveraRacuna();
                proveraRacuna.showProveraRacunaWindow();
            }
        });
    }
}
