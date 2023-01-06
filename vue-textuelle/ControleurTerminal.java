public abstract class ControleurTerminal {
    Terminal terminal;

    // Méthode appelée dans la vue (Terminal.java) pour savoir quelle action a été faite 
    public abstract void quelleAction(Joueur joueur);
    

    // Piocher une Tuile
    public TuileDomino piocherPiece(Joueur joueur) {
        // int[][] t = { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }; // pour les tests
        // TuileDomino d = new TuileDomino(t);
        // joueur.setPiece(d);
        joueur.setPiece(terminal.getPartie().getSac().piocher());
        return (TuileDomino) (joueur.getPiece());
    }

    // Vérifie si le plateau est vide 
    public boolean plateauvide() {
            boolean plateauvide = true;
            for (int i = 0; i < terminal.getPartie().getPlateau().getGrille().getListPieces().size(); i++) {
                for (int z = 0; z < terminal.getPartie().getPlateau().getGrille().getListPieces().get(i).size(); z++) {
                    if (terminal.getPartie().getPlateau().getGrille().getListPieces().get(i).get(z) != null) {
                        plateauvide = false;
                    }
                }
            }
            return plateauvide;
        }
    }