public class Joueur {
    private String nom;
    private int nbPoints = 0;
    private Piece pieceactuelle;
    private boolean ia = false;

    Joueur(String nom) {
        this.nom = nom;
    }

    public String getName() {
        return this.nom;
    }

    public Piece getPiece() {
        return this.pieceactuelle;
    }

    public void setPiece(Piece p) {
        this.pieceactuelle = p;
    }

    public int getNbPoints() {
        return this.nbPoints;
    }

    public void setNbPoints(int score) {
        this.nbPoints = score;
    }

    public boolean getIA() {
        return this.ia;
    }

    public void setIA(boolean b) {
        this.ia = b;
    }
}
