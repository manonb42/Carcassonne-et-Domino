package vueTerminal;

import Model.Coordonnees;
import Model.Grille;
import Model.Joueur;
import Model.TuileDomino;

public class ControleurIATerminal extends ControleurTerminal {

    public ControleurIATerminal(Terminal t) {
        this.terminal = t;
    }

    // Méthode appelée par la vue qui détermine l'action de l'IA
    @Override
    public void quelleAction(Joueur joueur) {
        if(placement(joueur)){
            terminal.placement(1);
        } else{
            terminal.passertour();
        }
    }

    public boolean placement(Joueur j) {
        if (plateauvide()) {
            if (terminal.getPartie().getPlateau().placer((TuileDomino) (j.getPiece()), new Coordonnees(0, 0)))
                return true;
        } else if (tryPlacerPiece(j)) {
            return true;
        }
        return false;
    }

    public TuileDomino tournerPiece(Joueur joueur, int i){
        joueur.getPiece().tourner(i);
        return (TuileDomino)joueur.getPiece();
    }

    public boolean tryPlacerPiece(Joueur joueur) {
        Grille plateau = terminal.getPartie().getPlateau().getGrille();
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
                                if (terminal.getPartie().getPlateau().validPlacement(tournerPiece(joueur, 1), new Coordonnees(coordX, coordY))){
                                    isPlaceable=true;
                                    newcoord = new Coordonnees(coordX, coordY);
                                    scoreAct = terminal.getPartie().getPlateau().newPoints((TuileDomino)joueur.getPiece(), newcoord);
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
            if(terminal.getPartie().getPlateau().placer(tournerPiece(joueur, nbTours), bestCoordinates)){
                joueur.setNbPoints(joueur.getNbPoints()+bestScore);
                return true;
            }
        } return false;
    }
}
