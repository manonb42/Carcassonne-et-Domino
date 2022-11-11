import java.util.*;

public class Plateau {
    // changer array en arraylist
    // private Piece[][] pieces;
    private List<List<Piece>> pieces = new ArrayList<List<Piece>>();

    Plateau() {
        for (int i = 0; i < 128; i++) {
            pieces.add(new ArrayList<>());
            for (int j = 0; j < 128; j++) {
                pieces.get(i).add(null);
            }
        }
    }

    public List<List<Piece>> getPieces() {
        return pieces;
    }

    public void setPieces(List<List<Piece>> pieces) {
        this.pieces = pieces;
    }

    public boolean placer(Piece p, Coordonnees coordonnee) {
        if (validPlacement(p, coordonnee)) {
            // pieces[coordonnee.getY()][coordonnee.getX()] = p;
            pieces.get(coordonnee.getY()).add(coordonnee.getX(), p);
            return true;
        }
        return false;
    }

    // a changer
    private boolean validPlacement(Piece p, Coordonnees coordonnee) {
        boolean cotevide = true;
        boolean plateauvide = true;
        int[][] deltas = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        for (int delta = 0; delta < deltas.length; delta++) {
            int coordX = coordonnee.getX() + deltas[delta][0];
            int coordY = coordonnee.getY() + deltas[delta][1];
            if (coordX < 0 || coordY < 0)
                return false; // non

            if (pieces.get(coordX).get(coordY) != null) {
                cotevide = false;
                if (!(p.sidesMatch(pieces.get(coordX).get(coordY), delta)))
                    return false;
            }
        }
        for (int i = 0; i < pieces.size(); i++) {
            for (int j = 0; j < pieces.get(i).size(); j++) {
                if (pieces.get(i).get(j) != null) {
                    plateauvide = false;
                }
            }
        }
        if (plateauvide)
            return true;
        if (cotevide)
            return false;
        return true;
    }

}
