package Model;

import java.io.Serializable;

public class Partie implements Serializable {
    private Joueur[] joueurs;
    public Plateau plateau;
    private Sac sac;
    Joueur gagnant = null;
    private boolean fin; // si true, fin de la partie

    public Partie(Joueur[] listeJoueurs, Plateau plateau, Sac sac) {
        this.joueurs = listeJoueurs;
        this.plateau = plateau;
        this.sac = sac;
    }

    public Joueur[] getJoueurs() {
        return this.joueurs;
    }

    public void setJoueurs(Joueur[] listeJoueurs) {
        this.joueurs = listeJoueurs;
    }

    public Plateau getPlateau() {
        return this.plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public Sac getSac() {
        return this.sac;
    }

    public void setSac(SacDomino sac) {
        this.sac = sac;
    }

    public void setGagnant(Joueur g) {
        this.gagnant = g;
    }

    public boolean getFin(){
        return fin;
    }
    public void setFin(boolean fin) {
        this.fin = fin;
    }

    public void fullAbandon() {
        int ab = 0;
        for(int i = 0; i<joueurs.length; i++){
            if(joueurs[i].getAbandon()){
                ab++;
            }
        }
        if(ab>=joueurs.length-1){
            fin = true;
        }
    }

    public Joueur getGagnant() {
        return gagnant;
    }


}
