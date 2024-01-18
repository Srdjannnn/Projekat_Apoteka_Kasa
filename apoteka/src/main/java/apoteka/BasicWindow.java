package apoteka;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BasicWindow {
    private JFrame frame;
    private ArtikliPanel artikliPanel;

    public BasicWindow() {
        frame = new JFrame("Kasa - Basic Window");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        artikliPanel = new ArtikliPanel();
        frame.add(artikliPanel, BorderLayout.CENTER);

        JPanel topButtonsPanel = new JPanel();
        topButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Dugme Stanje obojeno u sivo
        JButton stateButton = new JButton("Stanje");
        stateButton.setPreferredSize(new Dimension(100, 100));
        stateButton.setBackground(Color.GRAY); // Siva boja
        topButtonsPanel.add(stateButton);

        // Dugme Istorija Prodaje obojeno u sivo
        JButton historyButton = new JButton("Istorija Prodaje");
        historyButton.setPreferredSize(new Dimension(100, 100));
        historyButton.setBackground(Color.GRAY); // Siva boja
        topButtonsPanel.add(historyButton);

        JButton testButton = new JButton("Test");
        testButton.setPreferredSize(new Dimension(100, 100));
        topButtonsPanel.add(testButton);

        JButton closeButton = new JButton("Ugasi Kasu");
        closeButton.setPreferredSize(new Dimension(100, 100));
        topButtonsPanel.add(closeButton);

        frame.add(topButtonsPanel, BorderLayout.NORTH);

        stateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ne radi nista, siva boja
            }
        });

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ne radi nista, siva boja
            }
        });

        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ovde otvorite prozor klase Test
                Test test = new Test();
                test.showTestWindow();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                BasicWindow basicWindow = new BasicWindow();
                basicWindow.show();
            }
        });
    }
}
