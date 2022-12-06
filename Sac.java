public class Sac {
    private int nbPiecesRestantes;
    private Domino[] pieces;

    public Sac(int nbPieces) {
        this.nbPiecesRestantes = nbPieces;
        this.pieces = new Domino[nbPieces];
        for (int i = 0; i < nbPieces; i++) {
            this.pieces[i] = genererPiece();
        }
    }

    public int getPiecesRestantes() {
        return this.nbPiecesRestantes;
    }

    public Domino genererPiece() {
        int tab[][] = new int[4][3];
        Domino p = new Domino(tab);
        for (int i = 0; i < p.getNumeros().length; i++) {
            for (int j = 0; j < p.getNumeros()[i].length; j++) {
                p.setNumeros(i, j, (int) (Math.random() * 4));
            }
        }
        return p;
    }

    public Domino piocher() {
        nbPiecesRestantes--;
        Domino p = this.pieces[nbPiecesRestantes];
        this.pieces[nbPiecesRestantes] = null;
        return p;
    }

}
