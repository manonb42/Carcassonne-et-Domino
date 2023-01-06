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
        Coordonnees bestCoordinates = new Coordonnees(0, 0);
        int nbTours = 0;
        boolean isPlaceable = false;
        int bestScore = 0;
        for (int i = 0; i < plateau.getListPieces().size(); i++) {
            for (int j = 0; j < plateau.getListPieces().get(i).size(); j++) {
                if (plateau.getPiece(j, i) != null) {
                    for (int delta = 0; delta < deltas.length; delta++) {
                        int coordX = j + deltas[delta][0];
                        int coordY = i + deltas[delta][1];
                        if (plateau.getPiece(coordX, coordY) == null && coordX < 46 && coordX >= 0 && coordY < 46
                                && coordY >= 0) {
                            for (int k = 0; k < 4; k++) {
                                if (domino.getPartie().getPlateau().validPlacement(tournerPiece(joueur, k), new Coordonnees(coordX, coordY))) {
                                    isPlaceable = true;
                                    Coordonnees newcoord = new Coordonnees(coordX, coordY);
                                    int score = domino.getPartie().getPlateau().newPoints((TuileDomino) joueur.getPiece(), newcoord);
                                    if (bestScore < score) {
                                        bestScore = score;
                                        bestCoordinates = newcoord;
                                        nbTours = k;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (isPlaceable) {
            domino.getTuileActuelle().getTuile().tourner(nbTours);
            domino.getTuileActuelle().actualiser();
            domino.getPartie().getPlateau().placer(domino.getTuileActuelle().getTuile(),bestCoordinates);
            domino.getJoueurActuel().setNbPoints(domino.getJoueurActuel().getNbPoints() + domino.getPartie().getPlateau().newPoints(domino.getTuileActuelle().getTuile(), bestCoordinates));
            domino.getAction().setText("La pièce a bien été placée");
            domino.getGridBagConstraints().gridx = 72 + bestCoordinates.getX();
            domino.getGridBagConstraints().gridy = 72 - bestCoordinates.getY();
            domino.getTuileActuelle().setPreferredSize(new Dimension(25, 25));
            domino.getPlateau().add(domino.getTuileActuelle(), domino.getGridBagConstraints());
            return true;
            
        }
        return false;
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
