package Model;

public class SacDomino extends Sac {
    private TuileDomino[] pieces;

    public SacDomino(int nbPieces) {
        this.nbPiecesRestantes = nbPieces;
        this.pieces = new TuileDomino[nbPieces];
        for (int i = 0; i < nbPieces; i++) {
            this.pieces[i] = genererPiece();
        }
    }

    public TuileDomino genererPiece() {
        int tab[][] = new int[4][3];
        TuileDomino p = new TuileDomino(tab);
        for (int i = 0; i < p.getNumeros().length; i++) {
            for (int j = 0; j < p.getNumeros()[i].length; j++) {
                p.setNumeros(i, j, (int) (Math.random() * 2) + 1);
            }
        }
        return p;
    }

    @Override
    public int getPiecesRestantes() {
        return this.nbPiecesRestantes;
    }

    @Override
    public TuileDomino piocher() {
        nbPiecesRestantes--;
        TuileDomino p = this.pieces[nbPiecesRestantes];
        this.pieces[nbPiecesRestantes] = null;
        return p;
    }
}
