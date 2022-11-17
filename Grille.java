import java.util.ArrayList;
import java.util.List;

public class Grille {
    private List<List<Piece>> pieces = new ArrayList<List<Piece>>();

    private int dx = 0;
    private int dy = 0;

    Grille() {
        for (int i = 0; i < 128; i++) {
            pieces.add(new ArrayList<>());
            for (int j = 0; j < 128; j++) {
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

    public List<List<Piece>> getListPieces() {
        return this.pieces;
    }

    public void setPiece(int x, int y, Piece p) {
        if (y + dy >= 0) {
            int ncols = pieces.get(0).size();
            int ny = y + dy - pieces.size() + 1;
            for (int i = 0; i < ny; i++) {
                List<Piece> list = new ArrayList<>(ncols);
                for (int j = 0; j < ncols; j++) {
                    list.add(null);
                }
                pieces.add(list);
            }
        }
        if (x + dx >= 0) {
            int nligns = pieces.size();
            int nm = x + dx - pieces.get(0).size() + 1;
            for (int i = 0; i < nligns; i++) {
                for (int j = 0; j < nm; j++) {
                    pieces.get(i).add(null);
                }
            }

        }
        if (y + dy < 0) {
            for (int i = 0; i < -(y + dy); i++) {
                int nbcols = pieces.get(0).size();
                List<Piece> newlist = new ArrayList<Piece>(nbcols);
                for (int j = 0; j < nbcols; j++) {
                    newlist.add(null);
                }
                pieces.add(0, newlist);
            }
            dy += -y;
        }
        if (x + dx < 0) {
            for (int i = 0; i < pieces.size(); i++) {
                for (int j = 0; j < -(x + dx) - 1; j++) {
                    pieces.get(i).add(0, null);
                }
            }

            for (int z = 0; z < pieces.size(); z++) {
                if (z == y + dy) {
                    pieces.get(z).add(0, p);

                } else {
                    pieces.get(z).add(0, null);
                }
            }
            dx += -x;
        }
        pieces.get(y + dy).add(x + dx, p);
    }

    public Piece getPiece(int x, int y) {
        if (y + dy >= 0 && y + dy < pieces.size()) {
            if (x + dx >= 0 && x + dx < pieces.get(y + dy).size()) {
                return pieces.get(y + dy).get(x + dx);
            }
        }
        return null;
    }
}
