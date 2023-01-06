import javax.swing.*;
import java.awt.*;

public class Configuration extends JFrame{
    Configuration(){
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Configuration");
        setSize(500,100);
        setLocationRelativeTo(null);
        JPanel config = new JPanel();
        config.setLayout(new BorderLayout());
        JPanel elements = new JPanel();
        elements.setLayout(new GridLayout(1,3,50,0));
        config.add(elements,BorderLayout.NORTH);
        add(config);
        JLabel nbJoueur = new JLabel("Nombre de Joueurs :");
        JTextField test = new JTextField(4);
        JButton valider = new JButton("Valider");
        elements.add(nbJoueur);
        elements.add(test);
        elements.add(valider);
        JLabel act = new JLabel("Vous n'avez pas encore choisi");
        config.add(act);
        act.setHorizontalAlignment((int)CENTER_ALIGNMENT);
        valider.addActionListener((e ->{
            String t = test.getText();
            try{
                int i = Integer.valueOf(t);
                if(i<=0 || i>=5 ){
                    act.setText("Veuillez choisir un chiffre entre 1 et 4");
                }else{
                    hide();
                    System.exit(0); // pour le moment (a remplacer)
                }
            }catch(Exception err){
                act.setText("Entrée non valide");
            }
        } ));

    }

    /*Joueur fJoueur(){
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setSize(300,100);
        f.setLayout(new GridLayout(2,2));
        JLabel nomJoueur = new JLabel("Nom Joueur");
        JTextField t = new JTextField(10);
        JButton valid = new JButton("Valider");
        JCheckBox ia = new JCheckBox("IA");
        f.add(nomJoueur);
        f.add(t);
        f.add(valid);
        f.add(ia);
        valid.addActionListener((e ->{
            return new Joueur(t.getText());
        } ));
        hide();
        return j;
        
    }*/
    
}
