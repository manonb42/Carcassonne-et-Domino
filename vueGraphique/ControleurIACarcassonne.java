package vueGraphique;

import java.awt.image.BufferedImage;

import Model.*;

public class ControleurIACarcassonne extends ControleurCarcassonne {

    ControleurIACarcassonne(JeuCarcassonne t) {
        this.carcassonne = t;
    }

    void placerIA() {
        tryPlacerPiece(carcassonne.getJoueurActuel());
        carcassonne.prochainJoueur();
    }

    public boolean tryPlacerPiece(Joueur joueur) {
        Grille plateau = carcassonne.getPartie().getPlateau().getGrille();
        int[][] deltas = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        for (int i = 0; i < plateau.getListPieces().size(); i++) {
            for (int j = 0; j < plateau.getListPieces().get(i).size(); j++) {
                if (plateau.getListPieces().get(i).get(j)  != null) {
                    for (int delta = 0; delta < deltas.length; delta++) {
                        int coordX = j + deltas[delta][0] -plateau.getDx();
                        int coordY = i + deltas[delta][1] -plateau.getDy();
                        if (plateau.getPiece(coordX, coordY) == null && coordX<72 && coordX>=0 && coordY<72 && coordY>=0) {
                            for (int k = 0; k < 4; k++) {
                                carcassonne.getTuileActuelle().t.tourner(k);
                                BufferedImage buffer = carcassonne
                                        .rotateImageByDegrees(carcassonne.getTuileActuelle().image, k * 90);
                                carcassonne.getTuileActuelle().image = buffer;
                                carcassonne.paint(carcassonne.getGraphics());
                                if (carcassonne.getPartie().getPlateau().placer(carcassonne.getTuileActuelle().t,
                                        new Coordonnees(coordX, coordY))) {
                                    Coordonnees coord = new Coordonnees(coordX, coordY);
                                    carcassonne.getAction().setText("La pièce a bien été placée");
                                    carcassonne.getGridBagConstraints().gridx = 72 + coord.getX();
                                    carcassonne.getGridBagConstraints().gridy = 72 - coord.getY();
                                    carcassonne.getPlateau().add(carcassonne.getTuileActuelle(),
                                            carcassonne.getGridBagConstraints());
                                    if (joueur.getPions() > 0) {
                                        placerpartisan(coord, joueur);
                                        
                                    }
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
        for (int i = 0; i < carcassonne.getPartie().getPlateau().getGrille().getListPieces().size(); i++) {
            for (int z = 0; z < carcassonne.getPartie().getPlateau().getGrille().getListPieces().get(i).size(); z++) {
                if (carcassonne.getPartie().getPlateau().getGrille().getListPieces().get(i).get(z) != null) {
                    plateauvide = false;
                }
            }
        }
        return plateauvide;
    }

    public void placerpartisan(Coordonnees coordonnees, Joueur joueur) {
        int i = (int) (Math.random() * (3));
        joueur.setPions(joueur.getPions() - 1);
        carcassonne.getTuileActuelle().fillzone(carcassonne.getJoueurActuel().getColor(),i);
        carcassonne.getTuileActuelle().paint(carcassonne.getTuileActuelle().getGraphics());
        carcassonne.getTuileActuelle().t.getPaysages()[i].setPion(true);
        carcassonne.getAction().setText("Le pion a été placé en " + i);
    }
}
