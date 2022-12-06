public class Joueur {
    private String nom;
    private int nbPoints = 0;
    private TuileDomino pieceactuelle;
    private boolean ia = false;

    Joueur(String nom) {
        this.nom = nom;
    }

    public String getName() {
        return this.nom;
    }

    public TuileDomino getPiece() {
        return this.pieceactuelle;
    }

    public void setPiece(TuileDomino p) {
        this.pieceactuelle = p;
    }

    public int getNbPoints() {
        return this.nbPoints;
    }

    public void setNbPoints(int score) {
        this.nbPoints = score;
    }

    public boolean getisIA() {
        return this.ia;
    }

    public void setisIA(boolean b) {
        this.ia = b;
    }
}
