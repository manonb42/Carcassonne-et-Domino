import java.util.Scanner;

public class Terminal {
    Partie p;

    // affichage de la piece
    private static void affichePiece(Piece piece) {

        System.out.println("Votre pièce est :\n");
        System.out.print(" ");
        for (int z = 0; z < 3; z++) {
            System.out.print(piece.getNumeros()[0][z] + " ");
        }
        System.out.print("\n");
        for (int i = 2, j = 0; i >= 0; i--, j++) {
            System.out.println(piece.getNumeros()[3][i] + "     " + piece.getNumeros()[1][j]);
        }
        System.out.print(" ");
        for (int z = 2; z >= 0; z--) {
            System.out.print(piece.getNumeros()[2][z] + " ");
        }
    }

    // piocher une piece
    private Piece piocherPiece(Joueur joueur) {
        joueur.setPiece(p.getSac().piocher());
        return joueur.getPiece();
    }

    // lire les coordonnees
    private Coordonnees lectureCoordonnee(String coordonnee) {
        String chaine[] = coordonnee.split(",");
        return new Coordonnees(Integer.parseInt(chaine[0]), Integer.parseInt(chaine[1]));
    }

    // tourner une piece
    private void tournerPiece(Joueur joueur) {
        Scanner droitegauche = new Scanner(System.in);
        Scanner tour = new Scanner(System.in);
        System.out.println("Voulez vous tourner votre pièce vers la droite ou vers la gauche ?");
        String sens = droitegauche.nextLine();
        System.out.println("Combien de quart de tour voulez vous effectuer ?");
        int nbTours = tour.nextInt();
        if (sens.equals("droite")) {
            joueur.getPiece().tourner(nbTours);
        } else if (sens.equals("gauche")) {
            joueur.getPiece().tourner(-nbTours);
        } else
            throw new Error("mauvais sens der rotation: droite ou gauche attendu");
    }

    // placer une piece sur le plateau
    private void placement(Joueur j) {
        Scanner coord = new Scanner(System.in);
        boolean plateauvide = true;
        for (int i = 0; i < p.getPlateau().getPieces().size(); i++) {
            for (int z = 0; z < p.getPlateau().getPieces().get(i).size(); z++) {
                if (p.getPlateau().getPieces().get(i).get(z) != null) {
                    plateauvide = false;
                }
            }
        }
        if (plateauvide) {
            if (p.getPlateau().placer(j.getPiece(), new Coordonnees(0, 0))) {
                System.out.println("Succès ! La pièce a bien été placée");
            } else {
                System.out.println("Vous ne pouvez pas placer la pièce à cet endroit");
                quelleAction(j);
            }
        } else {
            System.out.println("Où voulez vous placer votre pièce ? Sous la forme \"1,1\" ");
            if (p.getPlateau().placer(j.getPiece(), lectureCoordonnee(coord.nextLine()))) {
                System.out.println("Succès ! La pièce a bien été placée");
            } else {
                System.out.println("Vous ne pouvez pas placer la pièce à cet endroit");
                quelleAction(j);
            }
        }

    }

    // choisir l'action a effectuer
    private void quelleAction(Joueur joueur) {
        Scanner action = new Scanner(System.in);
        System.out.println(
                "Quelle action voulez vous effectuer ?\n1 - Placer votre pièce\n2 - Tourner votre pièce\n3 - Passer votre tour");
        int actionAEffectuer = action.nextInt();
        if (actionAEffectuer == 1) {
            placement(joueur);
        } else if (actionAEffectuer == 2) {
            tournerPiece(joueur);
            affichePiece(joueur.getPiece());
            quelleAction(joueur);
        } else if (actionAEffectuer == 3) {
            System.out.println("Vous passez votre tour\n");
        }
    }

    // creation de la partie
    public Partie configurer() {

        // nombre de joueurs
        Scanner nbj = new Scanner(System.in);
        System.out.println("Combien y'a t'il de joueurs ? ");
        int nombreJoueursTmp = nbj.nextInt();
        Joueur joueurs[] = new Joueur[nombreJoueursTmp];

        // création des joueurs
        for (int i = 0; i < nombreJoueursTmp; i++) {
            Scanner nom = new Scanner(System.in);
            System.out.println("Entrer le nom du joueur n" + (i + 1) + " :");
            joueurs[i] = new Joueur(nom.nextLine());
        }

        // création des classes necessaires pour jouer
        // ArrayList<ArrayList<Piece>> piecesSurPlateau = new
        // ArrayList<ArrayList<Piece>>(128);
        // Piece[][] piecesSurPlateau = new Piece[128][128];
        Plateau plateau = new Plateau();
        Sac sac = new Sac(20);
        return new Partie(joueurs, plateau, sac);
    }

    // déroulement de la partie
    public void jouer() {
        int i = 0;
        while (p.getSac().getPiecesRestantes() != 0) {
            int tourDe = i % p.getJoueurs().length;
            System.out.println("--------------------------\n"
                    + "C'est au tour de " + p.getJoueurs()[tourDe].getName() + " !");
            affichePiece(piocherPiece(p.getJoueurs()[tourDe]));
            quelleAction(p.getJoueurs()[tourDe]);
            i++;
        }

        System.out.println("Il n'y a plus de pièces dans le sac. Fin de la partie.");
    }

    public static void main(String[] args) {
        Terminal t = new Terminal();
        t.p = t.configurer();
        t.jouer();
    }

    // faire fonction qui compte les points
    // OK : changer array en arraylist pour le plateau
    // OK : changer la fonction qui verifie que c'est valide
    // afficher le tableau
    // nettoyer le code
    // pas pressé : gerer les mauvaises entrees
}
