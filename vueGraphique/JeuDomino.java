package vueGraphique;
import Model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JeuDomino extends JFrame {
    private Partie p; // partie en cours
    private ControleurIADomino controleuria;
    private ControleurJoueurDomino controleurj;
    private TuileDominoGraphique c; // pièce de la main actuelle
    private JPanel texte = new JPanel();
    private JPanel plateau = new JPanel();
    private JPanel mainAct = new JPanel(); // main actuelle
    private JPanel play = new JPanel(); // actions (a droite)
    private JPanel piece = new JPanel(); // la ou est la pièce
    private JTextField choix = new JTextField(); // la ou on peut écrire les coordonées
    private JButton placer = new JButton("Placer la tuile");
    private JButton tourner = new JButton("Tourner la pièce");
    private JButton passer = new JButton("Passer son tour");
    private JButton abandonner = new JButton("Abandonner");
    private JButton finPartie = new JButton("Arreter la partie");
    private Joueur jActuel; // joueur actuel
    int iActuel = 0; // pour le compter le joueur actuel
    private JLabel tourAct;
    private JLabel nbPiece;
    private JLabel action;
    private GridBagConstraints gbc = new GridBagConstraints();
    private JScrollPane plat;

    JeuDomino(Joueur[] jou) {
        setTitle("Jeu de Domino"); // début
        setSize(1500, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        p = configurer(jou);
        jActuel = p.getJoueurs()[0]; // joueur actuel
        gbc.ipadx = 100;
        gbc.ipady = 100;
        controleuria = new ControleurIADomino(this);
        controleurj = new ControleurJoueurDomino(this);

        plateau.setPreferredSize(new Dimension(6000, 6000));
        plateau.setLayout(new GridBagLayout());
        mainAct.setPreferredSize(new Dimension(500, 150));
        mainAct.setLayout(new BorderLayout());

        c = new TuileDominoGraphique((TuileDomino) (p.getSac().piocher())); // premiere pièce
        c.setPreferredSize(new Dimension(120,120));
        JPanel bordG = new JPanel();
        JPanel bordD = new JPanel();
        bordG.setPreferredSize(new Dimension(100,400));
        bordD.setPreferredSize(new Dimension(100,400));

        piece.setPreferredSize(new Dimension(300,400));
        mainAct.add(bordG,BorderLayout.WEST);
        mainAct.add(bordD,BorderLayout.EAST);
        mainAct.add(piece,BorderLayout.CENTER);
        piece.add(c);
        jActuel.setPiece(c.getTuile());

        play.setLayout(new GridLayout(7, 1));
        play.setPreferredSize(new Dimension(300, 850));
        play.setBackground(Color.LIGHT_GRAY);

        texte.setLayout(new GridLayout(3, 1));

        tourAct = new JLabel(
                "C'est au tour de : " + jActuel.getName() + ", vous avez " + jActuel.getNbPoints() + " points !");
        nbPiece = new JLabel("Il reste : " + p.getSac().getPiecesRestantes() + " pièces");
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
        plat = new JScrollPane(plateau, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(plat, BorderLayout.CENTER);
        add(mainAct, BorderLayout.SOUTH);
        add(play, BorderLayout.EAST);
        setVisible(true);

        TuileDominoGraphique debut = new TuileDominoGraphique((TuileDomino) p.getSac().piocher());
        gbc.gridx = 72;
        gbc.gridy = 72;
        plateau.add(debut, gbc);
        p.getPlateau().placer(debut.t, new Coordonnees(0, 0));
        plat.getViewport().setViewPosition(new Point(2450, 2550));

        placer.addActionListener((ActionEvent e) -> {
            String st = choix.getText();
            Coordonnees coord;

            try {
                String chaine[] = st.split(",");
                coord = new Coordonnees(Integer.parseInt(chaine[0]), Integer.parseInt(chaine[1]));
                if(coord.getX()>23 || coord.getY()>23 || coord.getX()<-23 || coord.getY()<-23){
                    action.setText("Les coordonnées sont en dehors du plateau");
                }else if (controleurj.placementPiece(coord)){
                    action.setText("La pièce a bien été placée");
                    gbc.gridx = 72 + coord.getX();
                    gbc.gridy = 72 - coord.getY();
                    c.setPreferredSize(new Dimension(25,25));
                    plateau.add(c, gbc);
                    prochainJoueur();
                }else{
                    action.setText("La pièce n'a pas pu être placée");
                }
            } catch (Exception err) {
                action.setText("Mauvaise entrée. Veuillez saisir les coordonnees sous la forme 0,0");
            }

        });

        tourner.addActionListener((ActionEvent e) -> {
            c.getTuile().tourner(1);
            c.actualiser();
        });

        passer.addActionListener((ActionEvent e) -> {
            prochainJoueur();
        });
        abandonner.addActionListener((ActionEvent e) -> {
            jActuel.setAbandon(true); /////////////
            prochainJoueur();
        });

        finPartie.addActionListener((ActionEvent e) -> {
            finDePartie();
        });

        if (jActuel.getisIA()) {
            controleuria.placerIA();
        }

    }

    public JPanel getPlateau(){
        return this.plateau;
    }

    public JLabel getAction(){
        return this.action;
    }

    public Partie getPartie() {
        return this.p;
    }

    public Joueur getJoueurActuel() {
        return this.jActuel;
    }

    public TuileDominoGraphique getTuileActuelle() {
        return this.c;
    }

    public GridBagConstraints getGridBagConstraints() {
        return this.gbc;
    }

    ///////////////////
    void piocher() { // fonction pour piocher
        piece.remove(c);
        TuileDomino tmp1 = controleurj.piocher();
        TuileDominoGraphique tmp2 = new TuileDominoGraphique(tmp1);
        c = tmp2;
        c.setPreferredSize(new Dimension(120,120));
        piece.add(c);
        nbPiece.setText("Il reste : " + p.getSac().getPiecesRestantes() + " pièces");
        tourAct.setText(
                "C'est le tour de : " + jActuel.getName() + ", vous avez " + jActuel.getNbPoints() + " points !");
    }

    /////// le gagnant 
    void finDePartie() { // quand la partie est finie, affiche une nouvelle fenetre
        for(int i = 0; i<p.getJoueurs().length; i++){
            System.out.print(p.getJoueurs()[i].getName()+" : "+p.getJoueurs()[i].getNbPoints()+" |");
        }
        hide();
        JFrame j = new JFrame();
        j.setDefaultCloseOperation(EXIT_ON_CLOSE);
        j.setTitle("Jeu de Carcassonne");
        j.setSize(400, 400);
        j.setLayout(new BorderLayout());
        j.setLocationRelativeTo(null);
        JLabel win = new JLabel("Fin de la partie, il n'y a pas de gagnant");
        if (gagnant() != null) {
            win.setText("Fin de la partie, le(la) gagnant(e) est : " + gagnant().getName());
            JLabel points = new JLabel("Nombre de Points : "+gagnant().getNbPoints());
            j.add(win, BorderLayout.CENTER);
            j.add(points, BorderLayout.SOUTH);
            points.setHorizontalAlignment((int) CENTER_ALIGNMENT);
            points.setFont(new Font("Arial", Font.BOLD, 20));
        }else{
            j.add(win, BorderLayout.CENTER);
        }


        win.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        win.setFont(new Font("Arial", Font.BOLD, 15));
        j.setVisible(true);
        

    }


    // tout lol
    void prochainJoueur() { // passer au joueur suivant
        jActuel = controleurj.prochainJoueur();
        nbPiece.setText("Il reste : " + p.getSac().getPiecesRestantes() + " pièces");
        tourAct.setText(
                "C'est le tour de : " + jActuel.getName() + ", vous avez " + jActuel.getNbPoints() + " points !");
        if (!jActuel.getAbandon() && jActuel.getisIA() && p.getSac().getPiecesRestantes() > 0) {
            piocher();
            jActuel.setPiece(c.t);
            System.out.println(jActuel.getPiece());
            controleuria.placerIA();
        } else if (p.getSac().getPiecesRestantes() > 0) {
            piocher();
            jActuel.setPiece(c.t);
            //System.out.println(jActuel.getPiece());
        } else {
            finDePartie();
        }
    }

    /// !!!!!
    private Joueur gagnant() {
        Joueur gagnant = p.getJoueurs()[0];
        int gagnantPoint = p.getJoueurs()[0].getNbPoints();
        int nbGagnant = 0;
        for (int i = 0; i < p.getJoueurs().length; i++) {
            if (p.getJoueurs()[i].getNbPoints() > gagnantPoint) {
                gagnant = p.getJoueurs()[i];
                gagnantPoint = p.getJoueurs()[i].getNbPoints();
                nbGagnant=0;
            }else if(p.getJoueurs()[i].getNbPoints() == gagnantPoint){
                nbGagnant ++;
            }else{

            }
        }
        if(nbGagnant!=0){
            return null;
        }else{
            return gagnant;
        }
    }

    private Partie configurer(Joueur[] jou) {
        Grille g = new Grille();
        Plateau pl = new Plateau(g);
        SacDomino s = new SacDomino(30);
        return new Partie(jou, pl, s);
    }

    public static void main(String[] args) {
        Joueur[] j = {new Joueur("Lukas", false),new Joueur("Ilias", true)};
        new JeuDomino(j);
    }
}
