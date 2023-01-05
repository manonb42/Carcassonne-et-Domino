package vueGraphique;
import Model.*;

public abstract class ControleurDomino {
    JeuDomino domino;

    public TuileDomino piocher(){
        return (TuileDomino) (domino.getPartie().getSac().piocher());
    }

    public void tourner(TuileDomino t){
        t.tourner(1);
    }
    
}
