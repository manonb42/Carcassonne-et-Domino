import java.util.Arrays;
import java.util.Scanner;

public class Terminal {
    Partie p;
    // Scanner scanner = new Scanner(System.in);

    private static void affichePiece(Piece p) {
        System.out.println("Votre pièce est :\n");
        System.out.println(Arrays.toString(p.getNumeros()[0]));
        for (int i = 0; i < 3; i++) {
            System.out.println(p.getNumeros()[3][i] + "       " + p.getNumeros()[0][1]);
        }
        System.out.println(Arrays.toString(p.getNumeros()[2]) + "\n\n\n");
    }

    private Piece piocherPiece(Joueur joueur) {
        joueur.setPiece(p.getSac().piocher());
        return joueur.getPiece();
    }

    // a changer
    private Coordonnees lectureCoordonnee(String coordonnee) {
        Coordonnees c = new Coordonnees();
        c.setX(coordonnee.charAt(1));
        c.setY(coordonnee.charAt(3));
        return c;
    }

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

    private void placement(Joueur j) {
        Scanner coord = new Scanner(System.in);
        System.out.println("Où voulez vous placer votre pièce ? Sous la forme (1,1) ");
        if (p.getPlateau().placer(j.getPiece(), lectureCoordonnee(coord.nextLine()))) {
            System.out.println("Succès ! La pièce a bien été placée");
        } else {
            System.out.println("Vous ne pouvez pas placer la pièce à cet endroit");
            quelleAction(j);
        }
    }

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
        Piece[][] piecesSurPlateau = new Piece[128][128];
        Plateau plateau = new Plateau(piecesSurPlateau);
        Sac sac = new Sac(20);
        return new Partie(joueurs, plateau, sac);
    }

    public static void main(String[] args) {
        Terminal t = new Terminal();
        t.p = t.configurer();
        t.jouer();
    }

    public void jouer() {

        // déroulement de la partie
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

    // faire fonction qui compte les points
    // changer array en arraylist pour le plateau
    // afficher le tableau
    // changer fonction qui lis les coord
}
