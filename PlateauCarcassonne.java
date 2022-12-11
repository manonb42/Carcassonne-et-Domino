public class PlateauCarcassonne{
    private GrilleCarcassonne g;

    PlateauCarcassonne(GrilleCarcassonne g) {
        this.g = g;
    }

    public GrilleCarcassonne getGrille() {
        return g;
    }

    public void setGrille(GrilleCarcassonne g) {
        this.g = g;
    }

    public boolean placer(TuileCarcassonne p, Coordonnees coordonnee) {
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

    private boolean validPlacement(TuileCarcassonne p, Coordonnees coordonnee) {
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

    public static void main(String[] args) {
        PlateauCarcassonne plateau = new PlateauCarcassonne(new GrilleCarcassonne());
        Paysage[] p = {new Route(),new Route(), new Route(),new Route()};   
        TuileCarcassonne t = new TuileCarcassonne(p);
        Paysage[] p2 = {new Route(),new Pre(),new Route(),new Route()};
        TuileCarcassonne t2 = new TuileCarcassonne(p2);
        plateau.placer(t, new Coordonnees(0, 0));
        //System.out.println(plateau.validPlacement(t2,new Coordonnees(0, -1)));
        
    }


}