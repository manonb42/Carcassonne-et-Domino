import java.util.ArrayList;
import java.util.List;


public class Grille {
    // une grille c'est une liste de liste de pieces
    private List<List<TuileDomino>> pieces = new ArrayList<List<TuileDomino>>();

    private int dx = 0; // vecteur pour x
    private int dy = 0; // vecteur pour y

    Grille() {
        for (int i = 0; i < 5; i++) {
            pieces.add(new ArrayList<>());
            for (int j = 0; j < 5; j++) {
                pieces.get(i).add(null);
            }
        }
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public List<List<TuileDomino>> getListPieces() {
        return this.pieces;
    }


    public void setPiece(int x, int y, TuileDomino p) {
        if (y + dy >= pieces.size()) {
            int ncols = pieces.get(0).size();
            int ny = y + dy - pieces.size() + 1;
            for (int i = 0; i < ny; i++) {
                List<TuileDomino> list = new ArrayList<>(ncols);
                for (int j = 0; j < ncols; j++) {
                    list.add(null);
                }
                pieces.add(list);
            }
        }
        if (x + dx >= pieces.get(0).size()) {
            int nligns = pieces.size();
            int nm = x + dx - pieces.get(0).size() + 1;
            for (int i = 0; i < nligns; i++) {
                for (int j = 0; j < nm; j++) {
                    pieces.get(i).add(null);
                }
            }

        }
        if (y + dy < 0) {
            for (int i = 0; i < -y-dy ; i++) {
                int nbcols = pieces.get(0).size();
                List<TuileDomino> newlist = new ArrayList<TuileDomino>(nbcols);
                for (int j = 0; j < nbcols; j++) {
                    newlist.add(null);
                }
                pieces.add(0, newlist);
            }
            dy = -y;
        }
        if (x + dx < 0) {
            for (int i = 0; i < pieces.size(); i++) {
                for (int j = 0; j < -x-dx; j++) {
                    pieces.get(i).add(0, null);
                }
            }
            dx = -x;
        }
        pieces.get(y + dy).set(x+dx, p);
    }

    // getter d'une piece
    public TuileDomino getPiece(int x, int y) {
        if (y + dy >= 0 && y + dy < pieces.size()) {
            if (x + dx >= 0 && x + dx < pieces.get(y + dy).size()) {
                return pieces.get(y + dy).get(x + dx);
            }
        }
        return null;
    }
}