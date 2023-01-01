import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

public class JeuDomino extends JFrame{
    Partie p; //partie en cours  
    TestDomino c;      //pièce de la main actuelle
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
        

        
        c = new TestDomino((TuileDomino)(p.getSac().piocher())); //premiere pièce
        JPanel jp = new JPanel();
        mainAct.add(jp);
        JPanel jp2 = new JPanel();
        mainAct.add(jp2);
        mainAct.add(c);
        

        
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

        TestDomino debut = new TestDomino((TuileDomino)p.getSac().piocher());
        gbc.gridx = 72;
        gbc.gridy = 72;
        plateau.add(debut,gbc);
        p.plateau.placer(debut.t, new Coordonnees(0, 0));
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



        

    }


    public BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {  //fonction pour tourner de 90 deg l'image
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

    void piocher(){ //fonction pour piocher
        mainAct.remove(c);
        TuileDomino tmp1 = (TuileDomino)(p.getSac().piocher());
        TestDomino tmp2 = new TestDomino(tmp1);
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
        JLabel win = new JLabel("Fin de la partie");
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
        if(p.getSac().getPiecesRestantes()!= 0){
            piocher();
            System.out.println(c.t);
        }else{
            finDePartie();
        }
    }

    public static void main(String[] args) {
        Joueur[] jou = {new Joueur("Lukas", false), new Joueur("Manon", false),new Joueur("Ilias", false),new Joueur("Nael", false)};
        new JeuDomino(jou);
    }
}
