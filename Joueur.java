public class Joueur {
    private String nom; 
    private int nbPoints=0;
    private Piece pieceactuelle;

    Joueur(String nom){
        this.nom=nom;
    }

    public String getName(){
        return this.nom;
    }

    public Piece getPiece(){
        return this.pieceactuelle;
    }

    public void setPiece(Piece p){
        this.pieceactuelle=p;
    }

    public int getNbPoints(){
        return this.nbPoints;
    }

    public void setNbPoints(int score){
        this.nbPoints+=score;
    }

    /* pas necessaire a priori 
    // défosser la piece d'un joueur 
    private void defosser(Piece p){
        if(this.pieceactuelle==p){
            this.pieceactuelle=null;
        } 
    }*/
}
