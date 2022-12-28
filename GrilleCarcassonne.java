import java.util.ArrayList;
import java.util.List;


public class GrilleCarcassonne {
        // une grille c'est une liste de liste de pieces
    private List<List<TuileCarcassonne>> pieces = new ArrayList<List<TuileCarcassonne>>();

    private int dx = 0; // vecteur pour x
    private int dy = 0; // vecteur pour y

    GrilleCarcassonne() {
        // on initialise la liste avec une quantité initiale de 128 * 128
        // 128 est un nombre arbitraire, il fallait juste deja initialiser une partie
        for (int i = 0; i < 128; i++) {
            pieces.add(new ArrayList<>());
            for (int j = 0; j < 30; j++) {
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

    // retourne la liste de liste de pieces
    public List<List<TuileCarcassonne>> getListPieces() {
        return this.pieces;
    }

    // Pour placer une Tuile p en (x,y)
    public void setPiece(int x, int y, TuileCarcassonne p) {
        if (y + dy >= pieces.size()) {
            // dans le cas ou y+dy > taille du tableau, on ajoute une colonne de Piece null
            // à la fin
            int ncols = pieces.get(0).size();
            int ny = y + dy - pieces.size() + 1;
            for (int i = 0; i < ny; i++) {
                List<TuileCarcassonne> list = new ArrayList<>(ncols);
                for (int j = 0; j < ncols; j++) {
                    list.add(null);
                }
                pieces.add(list);
            }
        }
        if (x + dx >= pieces.get(0).size()) {
            // x + dx > taille du tableau : on ajoute une ligne de Piece null à la fin
            int nligns = pieces.size();
            int nm = x + dx - pieces.get(0).size() + 1;
            for (int i = 0; i < nligns; i++) {
                for (int j = 0; j < nm; j++) {
                    pieces.get(i).add(null);
                }
            }

        }
        if (y + dy < 0) {
            // si y + dy < 0 :
            // on decale tout notre tableau vers le haut en ajoutant une ligne de Piece null
            // au debut
            for (int i = 0; i < -(y + dy); i++) {
                int nbcols = pieces.get(0).size();
                List<TuileCarcassonne> newlist = new ArrayList<TuileCarcassonne>(nbcols);
                for (int j = 0; j < nbcols; j++) {
                    newlist.add(null);
                }
                pieces.add(0, newlist);
            }
            // puis on tient le compte de notre vecteur, le tableau a été décalé de -y vers
            // le haut
            dy += 1;
        }
        if (x + dx < 0) {
            // si x + dx< 0
            // on decale tout le tableau vers la droite en ajoutant une colonne de null au
            // debut
            // c'est a dire pour chaque ligne on ajoute une Piece null au debut
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
            // pareil, on tient le compte de notre vecteur dx
            dx += 1;
        }
        // une fois qu'on a fait tous les changements de decaler le tableau / ajouter
        // les lignes, on peut enfin ajouter notre piece :
        pieces.get(y + dy).add(x + dx, p);
    }

    // getter d'une piece
    public TuileCarcassonne getPiece(int x, int y) {
        // on veut la piece en (x,y) sauf qu'il faut faire attention au vecteur de
        // decalage

        // on verifie si la piece est bien à l'interieur de la grille
        if (y + dy >= 0 && y + dy < pieces.size()) {
            if (x + dx >= 0 && x + dx < pieces.get(y + dy).size()) {
                // si oui, alors elle existe et donc on peut la return :
                return pieces.get(y + dy).get(x + dx);
            }
        }
        // sinon :
        return null;
    }

    
}
