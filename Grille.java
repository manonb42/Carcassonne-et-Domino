import java.util.ArrayList;
import java.util.List;

// Principe de base : 
// On veut faire un plateau sur lequel on peut placer des pièces à droite et à gauche autant qu'on veut, comme dans la réalité. 

/// Problème : (*pb1)
// Sauf que peu importe où on place notre 1ere pièce, si on place vers le bas ou vers la gauche un certain nombre de fois, 
// on arrivera à une position negative dans notre tableau donc erreur 

// Autre problème un peu moins intuitif : (*pb2)
// Si on veut placer notre pièce au delà de la partie initialisée dans le constructeur, on ne pourra pas directement 
// placer notre pièce, il faudra initialiser une rangée et/ou une colonne de plus avant de pouvoir placer notre piece, 
// sinon erreur (outofbounds)

// Comment gérer ça ? 
// On prend deux vecteurs dx et dy, respectivement un vecteur pour les x et un vecteur pour les y 
// Et on créer des getters et setters spécifiques à la classe (pas les getters et setter basiques)
// À chaque fois qu'on veut placer notre pièce à un endroit qui aurait normalement fait une erreur, 
// On gère le cas particulier dans les getters et setters. 
// En gros, si on veut placer à une position pas encore initialisée (*pb2), 
// on va tout simplement initialiser une nouvelle colonne ou ligne (selon les besoins) puis on va pouvoir initialiser notre pièce 
// Si on veut placer en negatif (ex : on veut placer notre pièce à la gauche d'une pièce en 0,0)  (*pb1), 
// On déplace tout notre tableau (ici, d'une position à droite, notre piece en 0,0 sera donc en 1,0 et le nouvelle pièce en 0,0)
// On se sert donc des vecteurs pour garder le compte des décalages (pour les prochaines pieces à placer). 
// Dans cet exemple, on aura donc dx = 1 et dy = 0
// A chaque fois que l'utilisateur voudra placer une pièce en un x fixé, il faudra regarder x+dx
// Voir le code !

public class Grille {
    // une grille c'est une liste de liste de pieces
    private List<List<TuileDomino>> pieces = new ArrayList<List<TuileDomino>>();

    private int dx = 0; // vecteur pour x
    private int dy = 0; // vecteur pour y

    Grille() {
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
    public List<List<TuileDomino>> getListPieces() {
        return this.pieces;
    }

    // Pour placer une Tuile p en (x,y)
    public void setPiece(int x, int y, TuileDomino p) {
        if (y + dy >= pieces.size()) {
            // dans le cas ou y+dy > taille du tableau, on ajoute une colonne de Piece null
            // à la fin
            int ncols = pieces.get(0).size();
            int ny = y + dy - pieces.size() + 1;
            for (int i = 0; i < ny; i++) {
                List<TuileDomino> list = new ArrayList<>(ncols);
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
                List<TuileDomino> newlist = new ArrayList<TuileDomino>(nbcols);
                for (int j = 0; j < nbcols; j++) {
                    newlist.add(null);
                }
                pieces.add(0, newlist);
            }
            // puis on tient le compte de notre vecteur, le tableau a été décalé de -y vers
            // le haut
            dy += -y;
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
            dx += -x;
        }
        // une fois qu'on a fait tous les changements de decaler le tableau / ajouter
        // les lignes, on peut enfin ajouter notre piece :
        pieces.get(y + dy).add(x + dx, p);
    }

    // getter d'une piece
    public TuileDomino getPiece(int x, int y) {
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
