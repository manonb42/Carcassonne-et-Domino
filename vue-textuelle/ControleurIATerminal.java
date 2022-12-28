public class ControleurIATerminal extends ControleurTerminal {

    ControleurIATerminal(Terminal t) {
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
        for (int i = 0; i < plateau.getListPieces().size(); i++) {
            for (int j = 0; j < plateau.getListPieces().get(i).size(); j++) {
                if (plateau.getListPieces().get(i).get(j) != null) {
                    for (int delta = 0; delta < deltas.length; delta++) {
                        int coordX = j + deltas[delta][0];
                        int coordY = i + deltas[delta][1];
                        if (plateau.getPiece(coordX, coordY) == null) {
                            for(int k=0; k<4; k++){
                                if (terminal.getPartie().getPlateau().placer(tournerPiece(joueur, k), new Coordonnees(coordX, coordY))){
                                    return true;
                                }   
                            }
                        }
                    }
                }
            }
        } return false; 
    }
}
