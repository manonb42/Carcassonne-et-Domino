public class Joueur {
    private String nom;
    private int nbPoints = 0;
    private Tuile pieceactuelle;
    private boolean ia = false;
    private boolean abandon; // si true, fin de la partie pour le joueur
    private int pions = 8;

    Joueur(String nom, boolean ia) {
        this.nom = nom;
        this.ia=ia;
    }
    Joueur(String nom, boolean ia) {
        this.nom = nom;
        this.ia = ia;
    }

    public String getName() {
        return this.nom;
    }

    public void setName(String name){
        this.nom=name;
    }

    public Tuile getPiece() {
        return this.pieceactuelle;
    }

    public void setPiece(Tuile p) {
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

    public boolean getAbandon() {
        return abandon;
    }

    public void setAbandon(boolean abandon) {
        this.abandon = abandon;
    }
    
    public void setPions(int pions) {
        this.pions = pions;
    }
}
