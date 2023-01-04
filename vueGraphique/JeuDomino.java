package vueGraphique;
import javax.swing.*;

import Model.Coordonnees;
import Model.Grille;
import Model.Joueur;
import Model.Partie;
import Model.Plateau;
import Model.SacDomino;
import Model.TuileDomino;
import Model.TuileDominoGraphique;

import java.awt.*;
import java.awt.event.*;


public class JeuDomino extends JFrame{
    Partie p; //partie en cours  
    TuileDominoGraphique c;      //pièce de la main actuelle
    JPanel texte = new JPanel() ;
    JPanel plateau = new JPanel();
    JPanel mainAct = new JPanel(); //main actuelle
    JPanel play = new JPanel();     //actions (a droite)
    JTextField choix = new JTextField(); //la ou on peut écrire les coordonées
    JButton placer = new JButton("Placer la tuile");
    JButton tourner = new JButton("Tourner la pièce");
    JButton passer = new JButton("Passer son tour");
    JButton abandonner = new JButton("Abandonner");
    JButton finPartie = new JButton("Arreter la partie");
    Joueur jActuel; //joueur actuel
    int iActuel = 0; // pour le compter le joueur actuel
    JLabel tourAct;
    JLabel nbPiece;
    JLabel action;
    GridBagConstraints gbc = new GridBagConstraints();
    JScrollPane plat;


    JeuDomino(Joueur [] jou){
        setTitle("Jeu de Domino");  //début
        setSize(1500, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);


        Grille g = new Grille();
        Plateau pl = new Plateau(g);
        SacDomino s = new SacDomino(20);

        p = new Partie(jou, pl, s);

        jActuel = p.getJoueurs()[0];    //joueur actuel 
        gbc.ipadx = 100;
        gbc.ipady = 100;



       
        plateau.setPreferredSize(new Dimension(3000,3000));
        plateau.setLayout(new GridBagLayout());


        


        
        
        mainAct.setPreferredSize(new Dimension(500,100));
        mainAct.setLayout(new GridLayout(1,5));
        

        
        c = new TuileDominoGraphique((TuileDomino)(p.getSac().piocher())); //premiere pièce
        JPanel jp = new JPanel();
        mainAct.add(jp);
        JPanel jp2 = new JPanel();
        mainAct.add(jp2);
        mainAct.add(c);
        jActuel.setPiece(c.t);
        

        
        play.setLayout(new GridLayout(7,1));
        play.setPreferredSize(new Dimension(300,800));
        play.setBackground(Color.LIGHT_GRAY);

        texte.setLayout(new GridLayout(3,1));

        tourAct = new JLabel("C'est le tour de : "+jActuel.getName()+", vous avez "+ jActuel.getNbPoints()+" points !");
        nbPiece = new JLabel("Il reste : "+p.getSac().getPiecesRestantes()+" pièces");
        action = new JLabel("choisissez une action");


        texte.add(tourAct);
        texte.add(nbPiece);
        texte.add(action);
        


        play.add(texte);
        play.add(choix);
        play.add(placer);
        play.add(tourner);
        play.add(passer);
        play.add(abandonner);
        plat = new JScrollPane(plateau,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        
        
        add(plat,BorderLayout.CENTER);
        add(mainAct,BorderLayout.SOUTH);
        add(play,BorderLayout.EAST);
        setVisible(true);

        TuileDominoGraphique debut = new TuileDominoGraphique((TuileDomino)p.getSac().piocher());
        gbc.gridx = 72;
        gbc.gridy = 72;
        plateau.add(debut,gbc);
        p.getPlateau().placer(debut.t, new Coordonnees(0, 0));
        plat.getViewport().setViewPosition(new Point(900,1100));


        

        placer.addActionListener((ActionEvent e)->{
            String st = choix.getText();
            Coordonnees coord;

            try {
                String chaine[] = st.split(",");
                coord = new Coordonnees(Integer.parseInt(chaine[0]), Integer.parseInt(chaine[1]));
                if(p.plateau.validPlacement(c.t, coord)){
                    p.plateau.placer(c.t, coord);
                    jActuel.setNbPoints(jActuel.getNbPoints()+p.plateau.newPoints(c.t, coord));
                    action.setText("La pièce a bien été placée");
                    gbc.gridx = 72 + coord.getX();
                    gbc.gridy = 72 - coord.getY();
                    plateau.add(c,gbc);
                    prochainJoueur();
                }else{
                    action.setText("La pièce n'a pas pu être placée");
                }
            } catch (Exception err) {
                action.setText("Mauvaise entrée. Veuillez saisir les coordonnees sous la forme 0,0");
            }

        });


        tourner.addActionListener((ActionEvent e)->{
            c.t.tourner(1);
            c.actualiser();
            

        });


        

        passer.addActionListener((ActionEvent e)->{
            prochainJoueur();
        });
        abandonner.addActionListener((ActionEvent e)->{
            jActuel.setAbandon(true);
            prochainJoueur();
        });

        finPartie.addActionListener((ActionEvent e)->{
            finDePartie();
        });


        if(jActuel.getisIA()){
            placerIA();
        }

        

    }


    void piocher(){ //fonction pour piocher
        mainAct.remove(c);
        TuileDomino tmp1 = (TuileDomino)(p.getSac().piocher());
        TuileDominoGraphique tmp2 = new TuileDominoGraphique(tmp1);
        c = tmp2;
        mainAct.add(c);
        nbPiece.setText("Il reste : " + p.getSac().getPiecesRestantes() + " pièces");
        tourAct.setText("C'est le tour de : "+jActuel.getName()+", vous avez "+ jActuel.getNbPoints()+" points !");
    }

    void finDePartie(){ //quand la partie est finie, affiche une nouvelle fenetre
        hide();
        JFrame j = new JFrame();
        j.setTitle("Jeu de Carcassonne");
        j.setSize(400,400);
        j.setLayout(new BorderLayout());
        j.setLocationRelativeTo(null);
        JLabel win = new JLabel("Fin de la partie, il n'y a pas de gagnant");
        if(p.getGagnant()!= null){
            win.setText("Fin de la partie, le gagnant est : "+p.getGagnant().getName());
        }
       
        j.add(win,BorderLayout.CENTER);
        win.setHorizontalAlignment((int)CENTER_ALIGNMENT);
        win.setFont(new Font("Arial",Font.BOLD,20));
        j.setVisible(true);
        j.setDefaultCloseOperation(EXIT_ON_CLOSE);


    }

    void prochainJoueur(){ //passer au joueur suivant
        do{
        jActuel = p.getJoueurs()[++iActuel%p.getJoueurs().length];
        p.fullAbandon();
        if(p.getFin()){
            finDePartie();
            break;
        }
        }while(jActuel.getAbandon());
        nbPiece.setText("Il reste : " + p.getSac().getPiecesRestantes() + " pièces");
        tourAct.setText("C'est le tour de : "+jActuel.getName()+", vous avez "+ jActuel.getNbPoints()+" points !");
        if(!jActuel.getAbandon() && jActuel.getisIA() && p.getSac().getPiecesRestantes()>0 ){
            piocher();
            placerIA();
        }else if(p.getSac().getPiecesRestantes()>0){
            piocher();
        }else{
            finDePartie();
        }
    }

    void placerIA(){
        tryPlacerPiece(jActuel);
        prochainJoueur();


    }

 



    public boolean tryPlacerPiece(Joueur joueur) {
        Grille plateau = p.getPlateau().getGrille();
        int[][] deltas = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        for (int i = 0; i < plateau.getListPieces().size(); i++) {
            for (int j = 0; j < plateau.getListPieces().get(i).size(); j++) {
                if (plateau.getListPieces().get(i).get(j) != null) {
                    for (int delta = 0; delta < deltas.length; delta++) {
                        int coordX = j + deltas[delta][0];
                        int coordY = i + deltas[delta][1];
                        if (plateau.getPiece(coordX, coordY) == null) {
                            for(int k=0; k<4; k++){
                                c.t.tourner(k);
                                if (p.getPlateau().placer(c.t, new Coordonnees(coordX, coordY))){
                                    Coordonnees coord = new Coordonnees(coordX, coordY);
                                    jActuel.setNbPoints(jActuel.getNbPoints()+p.plateau.newPoints(c.t,coord ));
                                    action.setText("La pièce a bien été placée");
                                    gbc.gridx = 72 + coord.getX();
                                    gbc.gridy = 72 - coord.getY();
                                    this.plateau.add(c,gbc);
                                    return true;
                                }   
                            }
                        }
                    }
                }
            }
        } return false; 
    }

    public boolean plateauvide() {
        boolean plateauvide = true;
        for (int i = 0; i < p.getPlateau().getGrille().getListPieces().size(); i++) {
            for (int z = 0; z < p.getPlateau().getGrille().getListPieces().get(i).size(); z++) {
                if (p.getPlateau().getGrille().getListPieces().get(i).get(z) != null) {
                    plateauvide = false;
                }
            }
        }
        return plateauvide;
    }


}
