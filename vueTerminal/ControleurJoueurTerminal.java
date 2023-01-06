package vueTerminal;

import Model.Coordonnees;
import Model.Joueur;
import Model.TuileDomino;

public class ControleurJoueurTerminal extends ControleurTerminal{

    public ControleurJoueurTerminal(Terminal t){
        this.terminal=t;
    }

    public void setTerminal(Terminal t){
        this.terminal=t;
    }

    @Override
    public void quelleAction(Joueur joueur) {
        if (joueur.getAbandon()) { 
            return;
        }
        int actionAEffectuer = terminal.demanderAction();
        if (actionAEffectuer == 1) {
            placement(joueur);
        } else if (actionAEffectuer == 2) {
            tournerPiece(joueur);
            terminal.printTuile((TuileDomino) joueur.getPiece());
            quelleAction(joueur);
        } else if (actionAEffectuer == 3) {
            terminal.passertour();
        } else if (actionAEffectuer == 4) { 
            terminal.abandon();
            joueur.setAbandon(true);
            terminal.getPartie().fullAbandon();
        } else {
            terminal.mauvaiseentree();
            quelleAction(joueur);
        }
    }

    public void placement(Joueur j) {
        boolean plateauvide = plateauvide();
        if (plateauvide) {
            if (terminal.getPartie().getPlateau().placer((TuileDomino) (j.getPiece()), new Coordonnees(0, 0))) {
                terminal.placement(1);
            } else {
                terminal.placement(2);
                quelleAction(j);
            }
        } else {
            Coordonnees coord = terminal.lectureCoordonnee();
            if (terminal.getPartie().getPlateau().placer((TuileDomino) j.getPiece(), coord)) {
                int m = terminal.getPartie().getPlateau().newPoints((TuileDomino) j.getPiece(), coord);
                j.setNbPoints(j.getNbPoints() + m);
                terminal.placement(3);
            } else {
                terminal.placement(2);
                quelleAction(j);
            }
        }
    }

    public void tournerPiece(Joueur joueur) {
        String sens = terminal.quelSens();
        int nbTours = terminal.nbTours();
        if (sens.equals("droite")) {
            joueur.getPiece().tourner(nbTours);
        } else if (sens.equals("gauche")) {
            joueur.getPiece().tourner(-nbTours);
        }
    }
}
