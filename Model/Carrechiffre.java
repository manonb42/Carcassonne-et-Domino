package Model;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Carrechiffre extends JPanel {
    int t;
    JLabel chiffre;


    Carrechiffre(){
        setVisible(true);
        setSize(10,10);
        setBackground(Color.PINK);
        setPreferredSize(new Dimension(5,5));

    }


    Carrechiffre(int tt){
        t = tt;

        setVisible(true);
        setSize(5,5);
        chiffre = new JLabel(String.valueOf(t));
        add(chiffre);
        setBackground(Color.CYAN);
        setPreferredSize(new Dimension(5,5));


    }

    void setChiffre(int i){
        chiffre.setText(String.valueOf(i));
    }
    
    
}
