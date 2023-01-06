public class Plateau {
    private Grille g;

    Plateau(Grille g) {
        this.g = g;
    }

    public Grille getGrille() {
        return g;
    }

    public void setGrille(Grille g) {
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

    private boolean validPlacement(Tuile p, Coordonnees coordonnee) {
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
        TuileDomino p2 = new TuileDomino(tab);
        TuileDomino p3 = new TuileDomino(tab);

        // System.out.println(plateau.placer(p1, new Coordonnees(0,0)));
        // System.out.println(plateau.placer(p2, new Coordonnees(-1,0)));
        // System.out.println(plateau.placer(p3, new Coordonnees(-2,0)));
        // System.out.println(plateau.getGrille().getPiece(1, 0)); // 922
        // System.out.println(plateau.getGrille().getPiece(0, 0)); // f
        // System.out.println(plateau.getGrille().getPiece(-1,0)); // null 
        // System.out.println(plateau.getGrille().getPiece(-2,0)); // e
        // System.out.println();
        // System.out.println(plateau.getGrille().getListPieces().get(0).get(0)); // e
        // System.out.println(plateau.getGrille().getListPieces().get(0).get(1)); // null 
        // System.out.println(plateau.getGrille().getListPieces().get(0).get(2)); // f

        System.out.println(plateau.placer(p1, new Coordonnees(0,0)));
        System.out.println(plateau.placer(p2, new Coordonnees(-1,0)));

        System.out.println(plateau.getGrille().getPiece(0, 0)); // 922
        System.out.println(plateau.getGrille().getPiece(-1,0)); // f
        System.out.println(plateau.getGrille().getListPieces().get(0).get(0)); // f
        System.out.println(plateau.getGrille().getListPieces().get(0).get(1)); // 922
        System.out.println();

        System.out.println(plateau.placer(p3, new Coordonnees(-2,0)));

        System.out.println(plateau.getGrille().getPiece(1, 0)); // NULL
        System.out.println(plateau.getGrille().getPiece(0, 0)); // 922
        System.out.println(plateau.getGrille().getPiece(-1,0)); // f
        System.out.println(plateau.getGrille().getPiece(-2,0)); // e
        System.out.println(plateau.getGrille().getListPieces().get(0).get(0)); // e
        System.out.println(plateau.getGrille().getListPieces().get(0).get(1)); // f
        System.out.println(plateau.getGrille().getListPieces().get(0).get(2)); // 922

    }
}
