package vueGraphique;

import Model.*;

public class ControleurJoueurDomino extends ControleurDomino {

    ControleurJoueurDomino(JeuDomino t) {
        this.domino = t;
    }

    public boolean placementPiece(Coordonnees coord){
        if (domino.getPartie().getPlateau().placer(domino.getTuileActuelle().getTuile(), coord)) {
            domino.getJoueurActuel().setNbPoints(domino.getJoueurActuel().getNbPoints() + domino.getPartie().getPlateau().newPoints(domino.getTuileActuelle().getTuile(), coord));
            domino.getJoueurActuel().setPiece(null);
            return true;
        } else {
            return false;
        }
    }

    public TuileDomino piocher(){
        TuileDomino t = (TuileDomino) (domino.getPartie().getSac().piocher());
        return t;
    }

    public Joueur prochainJoueur() {
        Joueur tmp = domino.getJoueurActuel();
        do {
            tmp = domino.getPartie().getJoueurs()[++domino.iActuel % domino.getPartie().getJoueurs().length];
            domino.getPartie().fullAbandon();
            if (domino.getPartie().getFin()) {
                domino.finDePartie();
                break;
            }
        } while (tmp.getAbandon());

        return tmp;
    }

}
