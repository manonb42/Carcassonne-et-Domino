package vueGraphique;
import Model.*;

public class ControleurJoueurCarcassonne extends ControleurCarcassonne {
    public ControleurJoueurCarcassonne(JeuCarcassonne t) {
        this.carcassonne = t;
    }

    public boolean placementPiece(Coordonnees coord){
        if (carcassonne.getPartie().getPlateau().placer(carcassonne.getTuileActuelle().t,coord)) {
            carcassonne.getJoueurActuel().setPiece(null);
            return true;
        } else {
            return false;
        }
    }

    public boolean nbpions(Joueur j){
        return j.getPions()>0;
    }
}
