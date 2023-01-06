package vueGraphique;

import javax.swing.*;

import Model.Joueur;

import java.awt.*;
import java.awt.event.ActionEvent;

public class ConfigurationJeu extends JFrame {
    boolean carc;
    JLabel joueur1 = new JLabel("Joueur 1");
    JLabel joueur2 = new JLabel("Joueur 2");
    JLabel joueur3 = new JLabel("Joueur 3");
    JLabel joueur4 = new JLabel("Joueur 4");

    JTextField j1 = new JTextField(20);
    JTextField j2 = new JTextField(20);
    JTextField j3 = new JTextField(20);
    JTextField j4 = new JTextField(20);

    JCheckBox ia1 = new JCheckBox("I.A");
    JCheckBox ia2 = new JCheckBox("I.A");
    JCheckBox ia3 = new JCheckBox("I.A");
    JCheckBox ia4 = new JCheckBox("I.A");

    JButton valider = new JButton("Valider");

    GridBagConstraints gbc = new GridBagConstraints();

    ConfigurationJeu(boolean boole) {
        carc = boole;
        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(joueur1, gbc);
        gbc.gridx = 1;
        add(j1, gbc);
        gbc.gridx = 2;
        add(ia1, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(joueur2, gbc);
        gbc.gridx = 1;
        add(j2, gbc);
        gbc.gridx = 2;
        add(ia2, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(joueur3, gbc);
        gbc.gridx = 1;
        add(j3, gbc);
        gbc.gridx = 2;
        add(ia3, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(joueur4, gbc);
        gbc.gridx = 1;
        add(j4, gbc);
        gbc.gridx = 2;
        add(ia4, gbc);

        gbc.gridx = 4;
        gbc.gridy = 4;
        add(valider, gbc);

        valider.addActionListener((ActionEvent e) -> {
            Joueur[] tab = new Joueur[4];
            Joueur p1 = new Joueur(j1.getText(), ia1.isSelected());
            Joueur p2 = new Joueur(j2.getText(), ia2.isSelected());
            Joueur p3 = new Joueur(j3.getText(), ia3.isSelected());
            Joueur p4 = new Joueur(j4.getText(), ia4.isSelected());
            if (!p1.getName().equals("")) {
                p1.setColor(Color.BLUE);
                tab[0] = p1;
            }
            if (!p2.getName().equals("")) {
                p2.setColor(Color.YELLOW);
                tab[1] = p2;
            }
            if (!p3.getName().equals("")) {
                p3.setColor(Color.GREEN);
                tab[2] = p3;
            }
            if (!p4.getName().equals("")) {
                p4.setColor(Color.RED);
                tab[3] = p4;
            }

            int a = 0;
            for (int i = 0; i < tab.length; i++) {
                if (tab[i] != null) {
                    a++;
                }
            }
            Joueur[] t = new Joueur[a];
            int b = 0;
            for (int i = 0; i < tab.length; i++) {
                if (tab[i] != null) {
                    t[b] = tab[i];
                    b++;
                }
            }
            if (t.length <= 0)
                System.exit(0);
            hide();
            if (carc) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run(){
                        new JeuCarcassonne(t);
                    }
                });
            } else {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run(){
                        new JeuDomino(t);
                    }
                });
            }

        });
    }
}
