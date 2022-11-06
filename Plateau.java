public class Plateau {
    // changer array en arraylist
    private Piece[][] pieces;

    Plateau(Piece[][] pieces) {
        this.pieces = pieces;
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public void setPieces(Piece[][] pieces) {
        this.pieces = pieces;
    }

    public boolean placer(Piece p, Coordonnees coordonnee) {
        if (validPlacement(p, coordonnee)) {
            pieces[coordonnee.getY()][coordonnee.getX()] = p;
            return true;
        }
        return false;
    }

    /*
     * public int score(Joueur j, Piece p, Coordonnees coordonnee){
     * int[][] deltas={{0,1},{1,0},{0,-1},{-1,0}};
     * int score=0;
     * if(placer(p, coordonnee)){
     * for(int delta=0; delta<deltas.length; delta++){
     * int coordX=coordonnee.getX()+deltas[delta][0];
     * int coordY=coordonnee.getY()+deltas[delta][1];
     * if(pieces[coordX][coordY]!=null){
     * score+=pieces[coordX][coordY][delta][0];
     * }
     * }
     * }
     * }
     */

    private boolean validPlacement(Piece p, Coordonnees coordonnee) {
        int[][] deltas = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        for (int delta = 0; delta < deltas.length; delta++) {
            int coordX = coordonnee.getX() + deltas[delta][0];
            int coordY = coordonnee.getY() + deltas[delta][1];
            if (coordX < 0 || coordY < 0)
                return false; // non
            if (pieces[coordX][coordY] != null) {
                if (!p.sidesMatch(pieces[coordX][coordY], delta))
                    return false;
            }
        }
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (pieces[i][j] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * public static void main(String[] args){
     * int tab[][]={{1,2,3},{4,5,6},{7,8,9},{10,11,12}};
     * int tab2[][]={{1,2,3},{4,5,6},{1,2,3},{10,11,12}};
     * Piece premiere=new Piece(tab);
     * Piece deuxieme=new Piece(tab2);
     * System.out.println(premiere.sidesMatch(deuxieme, 0));
     * int coord[]={0,1};
     * Piece[][] pieces1={{null, null, null, null},{null, null, null, null},{null,
     * null, null, null},{null, null, null, null}};
     * Plateau plateau = new Plateau(pieces1);
     * System.out.println(plateau.placer(premiere,coord));
     * System.out.println(plateau.pieces[1][1]==null);
     * }
     */
}
