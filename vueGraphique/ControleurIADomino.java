package vueGraphique;
import Model.*;

public class ControleurIADomino {
    JeuDomino domino;

    ControleurIADomino(JeuDomino t){
        this.domino = t;
    }
    

    public boolean tryPlacerPiece(Joueur joueur) {
        Grille plateau = domino.getPartie().getPlateau().getGrille();
        int[][] deltas = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        for (int i = 0; i < plateau.getListPieces().size(); i++) {
            for (int j = 0; j < plateau.getListPieces().get(i).size(); j++) {
                if (plateau.getListPieces().get(i).get(j) != null) {
                    for (int delta = 0; delta < deltas.length; delta++) {
                        int coordX = j + deltas[delta][0];
                        int coordY = i + deltas[delta][1];
                        if (plateau.getPiece(coordX, coordY) == null) {
                            for(int k=0; k<4; k++){
                                domino.getTuileActuelle().getTuile().tourner(k);
                                if (domino.getPartie().getPlateau().placer(domino.getTuileActuelle().getTuile(), new Coordonnees(coordX, coordY))){
                                    Coordonnees coord = new Coordonnees(coordX, coordY);
                                    domino.getJoueurActuel().setNbPoints(domino.getJoueurActuel().getNbPoints()+domino.getPartie().plateau.newPoints(domino.getTuileActuelle().getTuile(),coord ));
                                    domino.action.setText("La pièce a bien été placée");
                                    domino.getGridBagConstraints().gridx = 72 + coord.getX();
                                    domino.getGridBagConstraints().gridy = 72 - coord.getY();
                                    domino.plateau.add(domino.getTuileActuelle(),domino.gbc);
                                    return true;
                                }   
                            }
                        }
                    }
                }
            }
        } return false; 
    }

    public boolean plateauvide() {
        boolean plateauvide = true;
        for (int i = 0; i < domino.getPartie().getPlateau().getGrille().getListPieces().size(); i++) {
            for (int z = 0; z < domino.getPartie().getPlateau().getGrille().getListPieces().get(i).size(); z++) {
                if (domino.getPartie().getPlateau().getGrille().getListPieces().get(i).get(z) != null) {
                    plateauvide = false;
                }
            }
        }
        return plateauvide;
    }

    void placerIA(){
        tryPlacerPiece(domino.getJoueurActuel());
        domino.prochainJoueur();
    }
}