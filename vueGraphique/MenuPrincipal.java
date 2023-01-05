package vueGraphique;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    public MenuPrincipal() {
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        setTitle("MenuPrincipal");
        getContentPane().setLayout(new BorderLayout());
        JLabel menup = new JLabel("Menu Principal");
        add(menup, BorderLayout.NORTH);
        menup.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        menup.setFont(new Font("Verdana", Font.PLAIN, 40));
        JButton domino = new JButton("Domino");
        JButton carcassonne = new JButton("Carcassonne");
        JButton quitter = new JButton("Quitter");
        JPanel bouttons = new JPanel();
        add(bouttons, BorderLayout.CENTER);
        bouttons.setLayout(new GridLayout(3, 1, 0, 0));
        bouttons.add(domino);
        bouttons.add(carcassonne);
        bouttons.add(quitter);
        setLocationRelativeTo(null);
        quitter.addActionListener((e -> {
            System.exit(0);
        }));
        domino.addActionListener((e -> {
            hide();
            new ConfigurationJeu(false);
        }));
        carcassonne.addActionListener((e -> {
            hide();
            new ConfigurationJeu(true);
        }));

    }
}
