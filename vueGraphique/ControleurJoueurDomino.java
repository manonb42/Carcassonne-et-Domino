package vueGraphique;

import Model.*;

public class ControleurJoueurDomino extends ControleurDomino {

    ControleurJoueurDomino(JeuDomino t) {
        this.domino = t;
    }

    public boolean placementPiece(Coordonnees coord){
        if (domino.getPartie().plateau.placer(domino.getTuileActuelle().getTuile(), coord)) {
            domino.getJoueurActuel().setNbPoints(domino.getJoueurActuel().getNbPoints() + domino.getPartie().getPlateau().newPoints(domino.getTuileActuelle().getTuile(), coord));
            domino.getJoueurActuel().setPiece(null);
            return true;
        } else {
            return false;
        }
    }

}
