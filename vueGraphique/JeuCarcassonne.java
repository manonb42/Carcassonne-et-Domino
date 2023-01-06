package vueGraphique;

import javax.imageio.ImageIO;
import javax.swing.*;
import Model.*;
import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

public class JeuCarcassonne extends JFrame {
    private Partie p; // partie en cours
    private ControleurIACarcassonne controleuria;
    private ControleurJoueurCarcassonne controleurj;
    private PieceCGraph c; // pièce de la main actuelle
    private JPanel texte = new JPanel();
    private JPanel plateau = new JPanel();
    private JPanel mainAct = new JPanel(); // main actuelle
    private JPanel play = new JPanel(); // actions (a droite)
    private JTextField choix = new JTextField(); // la ou on peut écrire les coordonées
    private JButton placer = new JButton("Placer la tuile");
    private JButton tourner = new JButton("Tourner la pièce");
    private JButton passer = new JButton("Passer son tour");
    private JButton abandonner = new JButton("Abandonner");
    private JButton finPartie = new JButton("Arreter la partie");
    private JButton placerPion = new JButton("Placer un pion");
    private Joueur jActuel; // joueur actuel
    int iActuel = 0; // pour le compter le joueur actuel
    private JLabel tourAct;
    private JLabel nbPiece;
    private JLabel action;
    private GridBagConstraints gbc = new GridBagConstraints();
    private JScrollPane plat;

    JeuCarcassonne(Joueur[] jou) {
        setTitle("Jeu de Carcassonne"); // début
        setSize(1500, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        Grille g = new Grille();
        Plateau pl = new Plateau(g);
        SacCarcassonne s = new SacCarcassonne();

        p = new Partie(jou, pl, s);

        jActuel = p.getJoueurs()[0]; // joueur actuel
        gbc.ipadx = 73;
        gbc.ipady = 73;

        plateau.setPreferredSize(new Dimension(6000, 6000));
        plateau.setLayout(new GridBagLayout());

        mainAct.setPreferredSize(new Dimension(500, 100));
        mainAct.setLayout(new GridLayout(1, 5));

        c = new PieceCGraph((TuileCarcassonne) (p.getSac().piocher())); // premiere pièce
        mainAct.add(c);
        jActuel.setPiece(c.t);
        controleuria=new ControleurIACarcassonne(this);
        controleurj=new ControleurJoueurCarcassonne(this);

        play.setLayout(new GridLayout(7, 1));
        play.setPreferredSize(new Dimension(300, 900));
        play.setBackground(Color.LIGHT_GRAY);

        texte.setLayout(new GridLayout(3, 1));

        tourAct = new JLabel("C'est au tour de : " + jActuel.getName() + ", vous avez " + jActuel.getPions() + " pions");
        nbPiece = new JLabel("Il reste : " + p.getSac().getPiecesRestantes() + " pièces");
        action = new JLabel("choisissez une action");

        texte.add(tourAct);
        texte.add(nbPiece);
        texte.add(action);

        placerPion.setVisible(false);

        play.add(texte);
        play.add(choix);
        play.add(placer);
        play.add(tourner);
        play.add(passer);
        play.add(abandonner);
        play.add(placerPion);

        plat = new JScrollPane(plateau, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(plat, BorderLayout.CENTER);
        add(mainAct, BorderLayout.SOUTH);
        add(play, BorderLayout.EAST);
        setVisible(true);
        Paysage[] pay = { new Route(), new Route(), new Route(), new Route() };
        TuileCarcassonne tu = new TuileCarcassonne(pay);

        PieceCGraph debut = new PieceCGraph(tu);
        gbc.gridx = 72;
        gbc.gridy = 72;
        plateau.add(debut, gbc);
        p.getPlateau().placer(tu, new Coordonnees(0, 0));
        plat.getViewport().setViewPosition(new Point(2450, 2550));

        placer.addActionListener((ActionEvent e) -> {
            String st = choix.getText();
            Coordonnees coord;

            try {
                String chaine[] = st.split(",");
                coord = new Coordonnees(Integer.parseInt(chaine[0]), Integer.parseInt(chaine[1]));
                if(coord.getX()>36 || coord.getY()>36 || coord.getX()<-36 || coord.getY()<-36){
                    action.setText("Les coordonnées sont en dehors du plateau");
                }else if (controleurj.placementPiece(coord)){
                    action.setText("La pièce a bien été placée");
                    gbc.gridx = 72 + coord.getX();
                    gbc.gridy = 72 - coord.getY();
                    plateau.add(c, gbc);
                    if (controleurj.nbpions(jActuel)) {
                        placerPion();
                    } else {
                        prochainJoueur();
                    }
                } else {
                    action.setText("La pièce n'a pas pu être placée");
                }
            } catch (Exception err) {
                action.setText("Mauvaise entrée. Veuillez saisir les coordonnees sous la forme 0,0");
            }

        });

        tourner.addActionListener((ActionEvent e) -> {
            BufferedImage i = rotateImageByDegrees(c.image, 90);
            c.image = i;
            paint(getGraphics());
            c.t.tourner(1);

        });

        passer.addActionListener((ActionEvent e) -> {
            prochainJoueur();
        });
        abandonner.addActionListener((ActionEvent e) -> {
            jActuel.setAbandon(true);
            prochainJoueur();
        });

        finPartie.addActionListener((ActionEvent e) -> {
            finDePartie();
        });

        placerPion.addActionListener((ActionEvent e) -> {
            try {
                String str = choix.getText();
                int i = Integer.valueOf(str);
                if (i >= 0 && i <= 4) {
                    jActuel.setPions(jActuel.getPions() - 1);
                    c.fillzone(jActuel.getColor(),i);
                    c.paint(c.getGraphics());
                    c.t.getPaysages()[i].setPion(true);
                    action.setText("Le pion a été placé en " + i);
                } else {
                    action.setText("Le pion n'a pas été placé");
                }
            } catch (Exception err) {
                action.setText("Le pion n'a pas été placé");
            } finally {
                prochainJoueur();
            }
        });

        if (jActuel.getisIA()) {
            controleuria.placerIA();
        }

    }

    class PieceCGraph extends JPanel {
        public BufferedImage image;
        TuileCarcassonne t;

        PieceCGraph(TuileCarcassonne t) {
            this.t = t;
            if (t.getPaysages()[0] instanceof Pre && t.getPaysages()[1] instanceof Pre && t.getPaysages()[2] instanceof Route
                    && t.getPaysages()[3] instanceof Route) { // associe l'image selon la tuile (pas fini)
                try {
                    image = ImageIO.read(new File("resources/carte1.png"));
                } catch (Exception err) {
                    System.out.println(err);
                }

            } else if (t.getPaysages()[0] instanceof Village && t.getPaysages()[1] instanceof Route
                    && t.getPaysages()[2] instanceof Route && t.getPaysages()[3] instanceof Pre) {
                try {
                    image = ImageIO.read(new File("resources/carte2.png"));
                } catch (Exception err) {
                    System.out.println(err);
                }

            } else if (t.getPaysages()[0] instanceof Village && t.getPaysages()[1] instanceof Village
                    && t.getPaysages()[2] instanceof Route && t.getPaysages()[3] instanceof Village) {
                try {
                    image = ImageIO.read(new File("resources/carte3.png"));
                } catch (Exception err) {
                    System.out.println(err);
                }
            } else if (t.getPaysages()[0] instanceof Village && t.getPaysages()[1] instanceof Route
                    && t.getPaysages()[2] instanceof Route && t.getPaysages()[3] instanceof Village) {
                try {
                    image = ImageIO.read(new File("resources/carte4.png"));
                } catch (Exception err) {
                    System.out.println(err);
                }
            } else if (t.getPaysages()[0] instanceof Village && t.getPaysages()[1] instanceof Route
                    && t.getPaysages()[2] instanceof Route && t.getPaysages()[3] instanceof Route) {
                try {
                    image = ImageIO.read(new File("resources/carte5.png"));
                } catch (Exception err) {
                    System.out.println(err);
                }
            } else if (t.getPaysages()[0] instanceof Route && t.getPaysages()[1] instanceof Pre && t.getPaysages()[2] instanceof Route
                    && t.getPaysages()[3] instanceof Pre) {
                try {
                    image = ImageIO.read(new File("resources/carte6.png"));
                } catch (Exception err) {
                    System.out.println(err);
                }
            } else if (t.getPaysages()[0] instanceof Pre && t.getPaysages()[1] instanceof Route && t.getPaysages()[2] instanceof Route
                    && t.getPaysages()[3] instanceof Route) {
                try {
                    image = ImageIO.read(new File("resources/carte7.png"));
                } catch (Exception err) {
                    System.out.println(err);
                }
            } else if (t.getPaysages()[0] instanceof Village && t.getPaysages()[1] instanceof Pre && t.getPaysages()[2] instanceof Pre
                    && t.getPaysages()[3] instanceof Pre) {
                try {
                    image = ImageIO.read(new File("resources/carte8.png"));
                } catch (Exception err) {
                    System.out.println(err);
                }
            } else if (t.getPaysages()[0] instanceof Village && t.getPaysages()[1] instanceof Village
                    && t.getPaysages()[2] instanceof Pre && t.getPaysages()[3] instanceof Pre) {
                try {
                    image = ImageIO.read(new File("resources/carte9.png"));
                } catch (Exception err) {
                    System.out.println(err);
                }
            } else if (t.getPaysages()[0] instanceof Village && t.getPaysages()[1] instanceof Village
                    && t.getPaysages()[2] instanceof Pre && t.getPaysages()[3] instanceof Village) {
                try {
                    image = ImageIO.read(new File("resources/carte10.png"));
                } catch (Exception err) {
                    System.out.println(err);
                }
            } else if (t.getPaysages()[0] instanceof Pre && t.getPaysages()[1] instanceof Pre && t.getPaysages()[2] instanceof Pre
                    && t.getPaysages()[3] instanceof Pre) {
                try {
                    image = ImageIO.read(new File("resources/carte11.png"));
                } catch (Exception err) {
                    System.out.println(err);
                }
            } else if (t.getPaysages()[0] instanceof Pre && t.getPaysages()[1] instanceof Pre && t.getPaysages()[2] instanceof Route
                    && t.getPaysages()[3] instanceof Pre) {
                try {
                    image = ImageIO.read(new File("resources/carte12.png"));
                } catch (Exception err) {
                    System.out.println(err);
                }
            } else if (t.getPaysages()[0] instanceof Village && t.getPaysages()[1] instanceof Pre && t.getPaysages()[2] instanceof Pre
                    && t.getPaysages()[3] instanceof Village) {
                try {
                    image = ImageIO.read(new File("resources/carte13.png"));
                } catch (Exception err) {
                    System.out.println(err);
                }
            } else if (t.getPaysages()[0] instanceof Village && t.getPaysages()[1] instanceof Route
                    && t.getPaysages()[2] instanceof Pre && t.getPaysages()[3] instanceof Route) {
                try {
                    image = ImageIO.read(new File("resources/carte14.png"));
                } catch (Exception err) {
                    System.out.println(err);
                }
            } else if (t.getPaysages()[0] instanceof Village && t.getPaysages()[1] instanceof Pre
                    && t.getPaysages()[2] instanceof Route && t.getPaysages()[3] instanceof Route) {
                try {
                    image = ImageIO.read(new File("resources/carte15.png"));
                } catch (Exception err) {
                    System.out.println(err);
                }
            } else if (t.getPaysages()[0] instanceof Pre && t.getPaysages()[1] instanceof Village && t.getPaysages()[2] instanceof Pre
                    && t.getPaysages()[3] instanceof Village) {
                try {
                    image = ImageIO.read(new File("resources/carte16.png"));
                } catch (Exception err) {
                    System.out.println(err);
                }
            } else if (t.getPaysages()[0] instanceof Village && t.getPaysages()[1] instanceof Village
                    && t.getPaysages()[2] instanceof Village && t.getPaysages()[3] instanceof Village) {
                try {
                    image = ImageIO.read(new File("resources/carte17.png"));
                } catch (Exception err) {
                    System.out.println(err);
                }
            } else if (t.getPaysages()[0] instanceof Route && t.getPaysages()[1] instanceof Route
                    && t.getPaysages()[2] instanceof Route && t.getPaysages()[3] instanceof Route) {
                try {
                    image = ImageIO.read(new File("resources/carte18.png"));
                } catch (Exception err) {
                    System.out.println(err);
                }
            } else {
                try {
                    image = ImageIO.read(new File("resources/carte19.png"));
                } catch (Exception err) {
                    System.out.println(err);
                }
            }


        }

        public BufferedImage getImage(){
            return this.image;
        }

        protected void fillzone(Color c,int cote){
            if(cote == 0){
                for(int i = 10; i< 20; i++){
                    for(int j = 40; j<50;j++){
                        int rgb = c.getRGB();
                        image.setRGB(j, i, rgb);
                    }
                }



            }else if(cote == 1){
                for(int i = 40; i< 50; i++){
                    for(int j = 70; j<80;j++){
                        int rgb = c.getRGB();
                        image.setRGB(j, i, rgb);
                    }
                }

            }else if(cote == 2){
                for(int i = 70; i< 80; i++){
                    for(int j = 40; j<50;j++){
                        int rgb = c.getRGB();
                        image.setRGB(j, i, rgb);
                    }
                }

            }else{
                for(int i = 40; i< 50; i++){
                    for(int j = 10; j<20;j++){
                        int rgb = c.getRGB();
                        image.setRGB(j, i, rgb);
                    }
                }

            }



        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }

    }

    public Partie getPartie() {
        return this.p;
    }

    public Joueur getJoueurActuel() {
        return this.jActuel;
    }

    public PieceCGraph getTuileActuelle() {
        return this.c;
    }

    public GridBagConstraints getGridBagConstraints() {
        return this.gbc;
    }

    public JLabel getAction(){
        return this.action;
    }

    public JPanel getPlateau(){
        return this.plateau;
    }

    public BufferedImage rotateImageByDegrees(BufferedImage img, double angle) { // fonction pour tourner de 90 deg
                                                                                 // l'image
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, this);
        g2d.drawRect(0, 0, newWidth - 1, newHeight - 1);
        g2d.dispose();

        return rotated;
    }

    void piocher() { // fonction pour piocher
        mainAct.remove(c);
        TuileCarcassonne tmp1 = controleurj.piocher();
        PieceCGraph tmp2 = new PieceCGraph(tmp1);
        c = tmp2;
        mainAct.add(c);
        nbPiece.setText("Il reste : " + p.getSac().getPiecesRestantes() + " pièces");
        tourAct.setText("C'est le tour de : " + jActuel.getName() + " vous avez " + jActuel.getPions() + " pions");
    }

    void finDePartie() { // quand la partie est finie, affiche une nouvelle fenetre
        hide();
        JFrame j = new JFrame();
        j.setTitle("Jeu de Carcassonne");
        j.setSize(400, 400);
        j.setLayout(new BorderLayout());
        j.setLocationRelativeTo(null);
        JLabel win = new JLabel("Fin de la partie");
        j.add(win, BorderLayout.CENTER);
        win.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        win.setFont(new Font("Arial", Font.BOLD, 20));
        j.setVisible(true);
        j.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    void prochainJoueur() { // passer au joueur suivant
        placer.setVisible(true);
        tourner.setVisible(true);
        passer.setVisible(true);
        abandonner.setVisible(true);
        placerPion.setVisible(false);
        do {
            jActuel = controleurj.prochainJoueur();
        } while (jActuel.getAbandon());
        nbPiece.setText("Il reste : " + p.getSac().getPiecesRestantes() + " pièces");
        tourAct.setText("C'est le tour de : " + jActuel.getName() + " vous avez " + jActuel.getPions() + " pions");
        if (!jActuel.getAbandon() && jActuel.getisIA() && p.getSac().getPiecesRestantes() > 0) {
            piocher();
            jActuel.setPiece(c.t);
            controleuria.placerIA();
        } else if (p.getSac().getPiecesRestantes() > 0) {
            piocher();
            jActuel.setPiece(c.t);
        } else {
            finDePartie();
        }
    }

    void placerPion() {
        placerPion.setVisible(true);
        action.setText("Ou voulez vous placer un pion ?");
        placer.setVisible(false);
        tourner.setVisible(false);
        passer.setVisible(false);
        abandonner.setVisible(false);
    }

}
