package vueGraphique;

import java.awt.Dimension;

import Model.*;

public class ControleurIADomino extends ControleurDomino{

    ControleurIADomino(JeuDomino t) {
        this.domino = t;
    }

    public boolean tryPlacerPiece(Joueur joueur) {
        Grille plateau = domino.getPartie().getPlateau().getGrille();
        int[][] deltas = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        for (int i = 0; i < plateau.getListPieces().size(); i++) {
            for (int j = 0; j < plateau.getListPieces().get(i).size(); j++) {
                if (plateau.getPiece(j, i) != null) {
                    for (int delta = 0; delta < deltas.length; delta++) {
                        int coordX = j + deltas[delta][0];
                        int coordY = i + deltas[delta][1];
                        if (plateau.getPiece(coordX, coordY) == null && coordX<46 && coordX>=0 && coordY<46 && coordY>=0) {
                            for (int k = 0; k < 4; k++) {
                                domino.getTuileActuelle().getTuile().tourner(k);
                                domino.getTuileActuelle().actualiser();
                                if (domino.getPartie().getPlateau().placer(domino.getTuileActuelle().getTuile(),
                                        new Coordonnees(coordX, coordY))) {
                                    Coordonnees coord = new Coordonnees(coordX, coordY);
                                    domino.getJoueurActuel().setNbPoints(
                                            domino.getJoueurActuel().getNbPoints() + domino.getPartie().getPlateau()
                                                    .newPoints(domino.getTuileActuelle().getTuile(), coord));
                                    domino.action.setText("La pièce a bien été placée");
                                    domino.getGridBagConstraints().gridx = 72 + coord.getX();
                                    domino.getGridBagConstraints().gridy = 72 - coord.getY();
                                    domino.getTuileActuelle().setPreferredSize(new Dimension(25,25));
                                    domino.plateau.add(domino.getTuileActuelle(), domino.gbc);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
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
