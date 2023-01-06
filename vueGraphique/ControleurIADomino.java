package vueGraphique;

import java.awt.Dimension;

import Model.*;

public class ControleurIADomino extends ControleurDomino {

    ControleurIADomino(JeuDomino t) {
        this.domino = t;
    }


    public boolean tryPlacerPiece(Joueur joueur) {
        Grille plateau = domino.getPartie().getPlateau().getGrille();
        int[][] deltas = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        Coordonnees bestCoordinates=new Coordonnees(0, 0);
        Coordonnees newcoord = new Coordonnees(0, 0);
        int nbTours=0;
        boolean isPlaceable=false;
        int bestScore=0;
        int scoreAct = 0;
        for (int i = 0; i < plateau.getListPieces().size(); i++) {
            for (int j = 0; j < plateau.getListPieces().get(i).size(); j++) {
                if (plateau.getListPieces().get(i).get(j) != null) {
                    for (int delta = 0; delta < deltas.length; delta++) {
                        int coordX = j + deltas[delta][0] -plateau.getDx();
                        int coordY = i + deltas[delta][1]-plateau.getDy();
                        if (plateau.getPiece(coordX, coordY)== null) {
                            for(int k=0; k<4; k++){
                                if (domino.getPartie().getPlateau().validPlacement(tournerPiece(joueur,1), new Coordonnees(coordX, coordY))){
                                    isPlaceable=true;
                                    newcoord = new Coordonnees(coordX, coordY);
                                    scoreAct = domino.getPartie().getPlateau().newPoints((TuileDomino)joueur.getPiece(), newcoord);
                                    if(bestScore<scoreAct){
                                        bestScore = scoreAct;
                                        bestCoordinates = newcoord;
                                        nbTours = k+1;
                                    }
                                }   
                            }
                        }
                    }
                }
            }
        } 
        if(isPlaceable){
            if(domino.getPartie().getPlateau().placer(tournerPiece(joueur, nbTours), bestCoordinates)){
                domino.getTuileActuelle().actualiser();
                domino.getPartie().getPlateau().placer(domino.getTuileActuelle().getTuile(),bestCoordinates);
                domino.getJoueurActuel().setNbPoints(domino.getJoueurActuel().getNbPoints() + domino.getPartie().getPlateau().newPoints(domino.getTuileActuelle().getTuile(), bestCoordinates));
                domino.getAction().setText("La pièce a bien été placée");
                domino.getGridBagConstraints().gridx = 72 + bestCoordinates.getX();
                domino.getGridBagConstraints().gridy = 72 - bestCoordinates.getY();
                domino.getTuileActuelle().setPreferredSize(new Dimension(25, 25));
                domino.getPlateau().add(domino.getTuileActuelle(), domino.getGridBagConstraints());
            }
        } return false;
    }

    

    
    

    public TuileDomino tournerPiece(Joueur joueur, int i){
        joueur.getPiece().tourner(i);
        return (TuileDomino)joueur.getPiece();
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

    void placerIA() {
        tryPlacerPiece(domino.getJoueurActuel());
        domino.prochainJoueur();
    }
}
