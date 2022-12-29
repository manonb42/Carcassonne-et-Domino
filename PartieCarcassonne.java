public class PartieCarcassonne {
    private Joueur[] joueurs;
    protected PlateauCarcassonne plateau;
    private Sac sac;
    Joueur gagnant = null;
    private boolean fin; // si true, fin de la partie

    PartieCarcassonne(Joueur[] listeJoueurs, PlateauCarcassonne plateau, Sac sac) {
        this.joueurs = listeJoueurs;
        this.plateau = plateau;
        this.sac = sac;
        Paysage[] p = {new Route(),new Route(),new Route(),new Route()};
        TuileCarcassonne tu = new TuileCarcassonne(p);
        plateau.placer(tu, new Coordonnees(0, 0));
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
        for(int i = 0; i<joueurs.length; i++){
            if(!joueurs[i].getAbandon()){
                return;
            }
        }
        fin = true;
    }
    
}
