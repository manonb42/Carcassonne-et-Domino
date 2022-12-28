public class PartieCarcassonne {
    Joueur[] joueurs;
    private PlateauCarcassonne plateau;
    SacCarcassonne sac;
    Joueur gagnant = null;
    private boolean fin; // si true, fin de la partie

    PartieCarcassonne(Joueur[] listeJoueurs, PlateauCarcassonne plateau, SacCarcassonne sac) {
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

    public PlateauCarcassonne getPlateau() {
        return this.plateau;
    }

    public void setPlateau(PlateauCarcassonne plateau) {
        this.plateau = plateau;
    }

    public SacCarcassonne getSac() {
        return this.sac;
    }

    public void setSac(SacCarcassonne sac) {
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
        int all = 0;
        for(int i = 0; i<joueurs.length; i++){
            if(joueurs[i].getAbandon()){
                all++;
            }
        }
        if(all>=joueurs.length-1) fin = true;
    }
}
