import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.awt.geom.AffineTransform;


public class Jeu extends JFrame {
    Partie p; //partie en cours  
    PieceCGraph c;      //pièce de la main actuelle
    JLabel texte ;
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


    Jeu(){
        setTitle("Jeu de Carcassonne");  //début
        setSize(1500, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        Joueur[] jou = {new Joueur("Lukas", false), new Joueur("Manon", false),new Joueur("Ilias", false)}; //initialisation de la partie
        Grille g = new Grille();
        Plateau pl = new Plateau(g);
        SacCarcassonne s = new SacCarcassonne();

        p = new Partie(jou, pl, s);

        jActuel = p.getJoueurs()[0];    //joueur actuel 



       
        plateau.setBackground(Color.GRAY);
        plateau.setPreferredSize(new Dimension(500,850));
        
        
        mainAct.setPreferredSize(new Dimension(500,100));
        mainAct.setLayout(new GridLayout(1,5));
        
       /* Paysage[] pay = {new Pre(),new Pre(),new Route(),new Route()};
        TuileCarcassonne tu = new TuileCarcassonne(pay);*/

        
        c = new PieceCGraph((TuileCarcassonne)(p.getSac().piocher())); //premiere pièce
        mainAct.add(c);
        

        
        play.setLayout(new GridLayout(7,1));
        play.setPreferredSize(new Dimension(300,800));
        play.setBackground(Color.LIGHT_GRAY);

        
        texte =  new JLabel("Il reste : " + p.getSac().getPiecesRestantes() + " pièces \nC'est le tour de "+jActuel.getName());

        play.add(texte);
        play.add(choix);
        play.add(placer);
        play.add(tourner);
        play.add(passer);
        play.add(abandonner);
        play.add(finPartie);
        
        
        
        mainAct.setBackground(Color.BLACK);
        add(plateau,BorderLayout.CENTER);
        add(mainAct,BorderLayout.SOUTH);
        add(play,BorderLayout.EAST);
        setVisible(true);

        tourner.addActionListener((ActionEvent e)->{
            BufferedImage i = rotateImageByDegrees(c.image, 90);
            c.image = i;
            paint(getGraphics());
            c.t.tourner(1);
            System.out.println(c.t);
            

        });


        

        passer.addActionListener((ActionEvent e)->{
            prochainJoueur();
            if(p.getSac().getPiecesRestantes()!= 0){
                piocher();
                System.out.println(c.t);
            }else{
                finDePartie();
            }
        });
        abandonner.addActionListener((ActionEvent e)->{
            jActuel.setAbandon(true);
            prochainJoueur();
        });

        finPartie.addActionListener((ActionEvent e)->{
            finDePartie();
        });


        

    }

    class PieceCGraph extends JPanel{
        private BufferedImage image;
        TuileCarcassonne t;


        PieceCGraph(TuileCarcassonne t){
            this.t = t;
            if(t.paysages[0] instanceof Pre && t.paysages[1] instanceof Pre  && t.paysages[2] instanceof Route && t.paysages[3] instanceof Route ){ //associe l'image selon la tuile (pas fini)
                try{
                    image = ImageIO.read(new File("resources/carte1.png"));
                }catch(Exception err){
                    System.out.println(err);
                }

            }else{
                try{
                    image = ImageIO.read(new File("resources/carte2.png"));
                }catch(Exception err){
                    System.out.println(err);
                }

            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);     
        }




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
        g2d.setColor(Color.RED);
        g2d.drawRect(0, 0, newWidth - 1, newHeight - 1);
        g2d.dispose();
    
        return rotated;
    }

    void piocher(){ //fonction pour piocher
        mainAct.remove(c);
        TuileCarcassonne tmp1 = (TuileCarcassonne)(p.getSac().piocher());
        PieceCGraph tmp2 = new PieceCGraph(tmp1);
        c = tmp2;
        mainAct.add(c);
        texte.setText("Il reste : " + p.getSac().getPiecesRestantes() + " pièces \nC'est le tour de "+jActuel.getName());
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
        jActuel = p.getJoueurs()[++iActuel % p.getJoueurs().length];
        p.fullAbandon();
        if(p.getFin()){
            finDePartie();
            break;
        }
        }while(jActuel.getAbandon());
        texte.setText("Il reste : " + p.getSac().getPiecesRestantes() + " pièces \nC'est le tour de "+jActuel.getName());
    }




    public static void main(String[] args) {
        new Jeu(); 
    }
    
}
