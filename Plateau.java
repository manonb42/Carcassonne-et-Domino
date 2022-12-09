public class Plateau {
    private Grille g;

    Plateau(Grille g) {
        this.g = g;
    }

    public Grille getG() {
        return g;
    }

    public void setG(Grille g) {
        this.g = g;
    }

    public boolean placer(TuileDomino p, Coordonnees coordonnee) {
        if (validPlacement(p, coordonnee)) {
            g.setPiece(coordonnee.getX(), coordonnee.getY(), p);
            return true;
        }
        return false;
    }

    private boolean plateauvide() {
        boolean plateauvide = true;
        for (int i = 0; i < g.getListPieces().size(); i++) {
            for (int j = 0; j < g.getListPieces().get(i).size(); j++) {
                if (g.getListPieces().get(i).get(j) != null) {
                    plateauvide = false;
                }
            }
        }
        return plateauvide;
    }

    public int newPoints(TuileDomino p, Coordonnees coordonnee) {
        int points = 0;
        int[][] deltas = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        for (int delta = 0; delta < deltas.length; delta++) {
            int coordX = coordonnee.getX() + deltas[delta][0];
            int coordY = coordonnee.getY() + deltas[delta][1];
            if (g.getPiece(coordX, coordY) != null) {
                if ((p.sidesMatch(g.getPiece(coordX, coordY), delta)))
                    for (int b = 0; b < p.getNumeros()[delta].length; b++) {
                        points += p.getNumeros()[delta][b];

                    }
            }
        }
        return points;
    }

    private boolean validPlacement(TuileDomino p, Coordonnees coordonnee) {
        boolean cotevide = true;
        boolean plateauvide = plateauvide();
        int[][] deltas = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        for (int delta = 0; delta < deltas.length; delta++) {
            int coordX = coordonnee.getX() + deltas[delta][0];
            int coordY = coordonnee.getY() + deltas[delta][1];

            if (g.getPiece(coordX, coordY) != null) {
                cotevide = false;
                if (!(p.sidesMatch(g.getPiece(coordX, coordY), delta)))
                    return false;
            }
        }
        if (g.getPiece(coordonnee.getX(), coordonnee.getY()) != null)
            return false;
        if (plateauvide)
            return true;
        if (cotevide)
            return false;
        return true;
    }

    // test pour la Grille
    public static void main(String[] args) {
        Plateau plateau = new Plateau(new Grille());
        int[][] tab = { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } };
        TuileDomino p1 = new TuileDomino(tab);
        for (int i = 0; i < 133; i++) {
            plateau.placer(p1, new Coordonnees(0, -i));
        }
        // System.out.println(plateau.getG().getPiece(0, -132) != null);
        System.out.println(plateau.getG().getPiece(0, 132) != null);

    }
}
