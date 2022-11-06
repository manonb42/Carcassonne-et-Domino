// import java.util.Arrays;

public class Sac {
    private int nbPiecesRestantes;
    private Piece[] pieces;

    public Sac(int nbPieces) {
        this.nbPiecesRestantes = nbPieces;
        this.pieces = new Piece[nbPieces];
        for (int i = 0; i < nbPieces; i++) {
            this.pieces[i] = genererPiece();
        }
    }

    public int getPiecesRestantes() {
        return this.nbPiecesRestantes;
    }

    public Piece genererPiece() {
        int tab[][] = new int[4][3];
        Piece p = new Piece(tab);
        for (int i = 0; i < p.getNumeros().length; i++) {
            for (int j = 0; j < p.getNumeros()[i].length; j++) {
                p.setNumeros(i, j, (int) (Math.random() * 4));
            }
        }
        return p;
    }

    public Piece piocher() {
        nbPiecesRestantes--;
        Piece p = this.pieces[nbPiecesRestantes];
        this.pieces[nbPiecesRestantes] = null;
        return p;
    }

}
