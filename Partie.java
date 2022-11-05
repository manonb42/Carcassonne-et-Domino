public class Partie {
    private Joueur[] joueurs;
    private Plateau plateau;
    private Sac sac;



    Partie(Joueur[] listeJoueurs, Plateau plateau, Sac sac){
        this.joueurs=listeJoueurs;
        this.plateau=plateau;
        this.sac=sac;
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

    public void setSac(Sac sac) {
        this.sac = sac;
    }


}
