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

    public TuileCarcassonne piocher(){
        TuileCarcassonne t = (TuileCarcassonne) carcassonne.getPartie().getSac().piocher();
        return t;
    }

    public Joueur prochainJoueur(){
        Joueur tmp = carcassonne.getJoueurActuel();
        do{
            tmp = carcassonne.getPartie().getJoueurs()[++carcassonne.iActuel % carcassonne.getPartie().getJoueurs().length];
            carcassonne.getPartie().fullAbandon();
            if (carcassonne.getPartie().getFin()) {
                carcassonne.finDePartie();
                break;
            }
        }while(tmp.getAbandon());
        return tmp;

    }

    public boolean nbpions(Joueur j){
        return j.getPions()>0;
    }
}
