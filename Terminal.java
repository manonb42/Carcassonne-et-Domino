import java.util.ArrayList;
import java.util.Scanner;

public class Terminal {
    Partie p;

    // affichage de la piece
    private static void affichePiece(TuileDomino piece) {

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
        System.out.print("\n");
    }

    public void afficheLigne1(TuileDomino p) {
        if (p != null) {
            System.out.print(" ");
            for (int i = 0; i < 3; i++) {
                System.out.print(p.getNumeros()[0][i] + " ");
            }
        } else {
            System.out.print("       ");
        }
    }

    public void afficheLigne2(TuileDomino p) {
        if (p != null) {
            System.out.print(p.getNumeros()[3][2] + "     " + p.getNumeros()[1][0]);
        } else {
            System.out.print("       ");
        }
    }

    public void afficheLigne3(TuileDomino p) {
        if (p != null) {
            System.out.print(p.getNumeros()[3][1] + "     " + p.getNumeros()[1][1]);
        } else {
            System.out.print("       ");
        }
    }

    public void afficheLigne4(TuileDomino p) {
        if (p != null) {
            System.out.print(p.getNumeros()[3][0] + "     " + p.getNumeros()[1][1]);
        } else {
            System.out.print("       ");
        }
    }

    public void afficheLigne5(TuileDomino p) {
        if (p != null) {
            System.out.print(" ");
            for (int i = 2; i >= 0; i--) {
                System.out.print(p.getNumeros()[2][i] + " ");
            }
        } else {
            System.out.print("       ");
        }
    }

    // est beugé
    public void afficheGrille() {
        for (int i = p.getPlateau().getG().getListPieces().size() - 1; i >= 0; i--) {
            if (!estVide((ArrayList) p.getPlateau().getG().getListPieces().get(i))) {
                for (int j = 0; j < p.getPlateau().getG().getListPieces().get(i).size(); j++) {
                    afficheLigne1(p.getPlateau().getG().getListPieces().get(i).get(j));
                }
                System.out.println("");
                for (int j = 0; j < p.getPlateau().getG().getListPieces().get(i).size(); j++) {
                    afficheLigne2(p.getPlateau().getG().getListPieces().get(i).get(j));
                }
                System.out.println("");
                for (int j = 0; j < p.getPlateau().getG().getListPieces().get(i).size(); j++) {
                    afficheLigne3(p.getPlateau().getG().getListPieces().get(i).get(j));
                }
                System.out.println("");
                for (int j = 0; j < p.getPlateau().getG().getListPieces().get(i).size(); j++) {
                    afficheLigne4(p.getPlateau().getG().getListPieces().get(i).get(j));
                }
                System.out.println("");
                for (int j = 0; j < p.getPlateau().getG().getListPieces().get(i).size(); j++) {
                    afficheLigne5(p.getPlateau().getG().getListPieces().get(i).get(j));
                }
                System.out.println("");
            }
        }
    }

    public boolean estVide(ArrayList<TuileDomino> l) {
        for (TuileDomino p : l) {
            if (p != null) {
                return false;
            }
        }
        return true;

    }

    // piocher une piece
    private TuileDomino piocherPiece(Joueur joueur) {
        joueur.setPiece(p.getSac().piocher());
        return joueur.getPiece();
    }

    // lire les coordonnees
    private Coordonnees lectureCoordonnee() {
        Scanner coord = new Scanner(System.in);
        System.out.println("Où voulez vous placer votre pièce ? Sous la forme \"1,1\" ");
        String st = coord.nextLine();
        try {
            String chaine[] = st.split(",");
            return new Coordonnees(Integer.parseInt(chaine[0]), Integer.parseInt(chaine[1]));
        } catch (Exception e) {
            System.out.println("Mauvaise entrée. Veuillez saisir les coordonnees sous la forme 0,0");
            return lectureCoordonnee();
        }

    }

    private int nbTours() {
        Scanner tour = new Scanner(System.in);
        System.out.println("Combien de quart de tour voulez vous effectuer ?");
        try {
            return tour.nextInt();
        } catch (Exception e) {
            System.out.println("Mauvaise entrée. Veuillez saisir un nombre.");
            return nbTours();
        }

    }

    private String quelSens() {
        Scanner droitegauche = new Scanner(System.in);
        System.out.println("Voulez vous tourner votre pièce vers la droite ou vers la gauche ?");
        String sens = droitegauche.nextLine();
        if (!(sens.toLowerCase().equals("droite") || sens.toLowerCase().equals("gauche"))) {
            System.out.println("Mauvaise entrée.");
            return quelSens();
        }
        return sens;
    }

    // tourner une piece
    private void tournerPiece(Joueur joueur) {
        String sens = quelSens();
        int nbTours = nbTours();
        if (sens.equals("droite")) {
            joueur.getPiece().tourner(nbTours);
        } else if (sens.equals("gauche")) {
            joueur.getPiece().tourner(-nbTours);
        }
    }

    private boolean plateauvide() {
        boolean plateauvide = true;
        for (int i = 0; i < p.getPlateau().getG().getListPieces().size(); i++) {
            for (int z = 0; z < p.getPlateau().getG().getListPieces().get(i).size(); z++) {
                if (p.getPlateau().getG().getListPieces().get(i).get(z) != null) {
                    plateauvide = false;
                }
            }
        }
        return plateauvide;
    }

    // placer une piece sur le plateau
    private void placement(Joueur j) {
        boolean plateauvide = plateauvide();
        if (plateauvide) {
            if (p.getPlateau().placer(j.getPiece(), new Coordonnees(0, 0))) {
                System.out.println("Succès ! La pièce a bien été placée");
            } else {
                System.out.println("Vous ne pouvez pas placer la pièce à cet endroit");
                quelleAction(j);
            }
        } else {
            Coordonnees coord = lectureCoordonnee();
            if (p.getPlateau().placer(j.getPiece(), coord)) {
                int m = p.getPlateau().newPoints(j.getPiece(), coord);
                j.setNbPoints(j.getNbPoints() + m);
                System.out.println("Succès ! La pièce a bien été placée");
            } else {
                System.out.println("Vous ne pouvez pas placer la pièce à cet endroit");
                quelleAction(j);
            }
        }
    }

    private int demanderAction() {
        Scanner action = new Scanner(System.in);
        System.out.println(
                "Quelle action voulez vous effectuer ?\n1 - Placer votre pièce\n2 - Tourner votre pièce\n3 - Passer votre tour");
        try {
            return action.nextInt();
        } catch (Exception e) {
            System.out.println("Mauvaise entrée. Veuillez saisir un nombre");
            return demanderAction();
        }

    }

    // choisir l'action a effectuer
    private void quelleAction(Joueur joueur) {
        int actionAEffectuer = demanderAction();
        if (actionAEffectuer == 1) {
            placement(joueur);
        } else if (actionAEffectuer == 2) {
            tournerPiece(joueur);
            affichePiece(joueur.getPiece());
            quelleAction(joueur);
        } else if (actionAEffectuer == 3) {
            System.out.println("Vous passez votre tour\n");
        } else {
            System.out.println("Mauvaise entrée. Veuillez saisir un nombre entre 1 et 3.");
            quelleAction(joueur);
        }
    }

    private Joueur gagnant() {
        Joueur gagnant = null;
        for (int i = p.getJoueurs().length - 1; i > 0; i--) {
            if (p.getJoueurs()[i].getNbPoints() > p.getJoueurs()[i - 1].getNbPoints()) {
                gagnant = p.getJoueurs()[i];
            }
        }
        p.setGagnant(gagnant);
        return gagnant;
    }

    private int nbJoueurs() {
        Scanner nbj = new Scanner(System.in);
        System.out.println("Combien y'a t'il de joueurs ? ");
        try {
            return nbj.nextInt();
        } catch (Exception e) {
            System.out.println("Mauvaise entrée. Veuillez saisir un nombre.");
            return nbJoueurs();
        }
    }

    private int nbJoueurspositif() {
        int n = nbJoueurs();
        if (n <= 0) {
            System.out.println("Mauvaise entrée. Veuillez entrer un chiffre superieur à 0.");
            return nbJoueurspositif();
        }
        return n;
    }

    private Joueur nomJoueur(int pos) {
        Scanner nom = new Scanner(System.in);
        System.out.println("Entrer le nom du joueur n" + (pos + 1) + " :");
        try {
            return new Joueur(nom.nextLine());
        } catch (Exception e) {
            System.out.println("Mauvaise entrée.");
            return nomJoueur(pos);
        }
    }

    // creation de la partie
    public Partie configurer() {

        // liste des joueurs
        Joueur joueurs[] = new Joueur[nbJoueurspositif()];

        // création des joueurs
        for (int i = 0; i < joueurs.length; i++) {
            joueurs[i] = nomJoueur(i);
        }
        Grille g = new Grille();
        Plateau plateau = new Plateau(g);
        SacDomino sac = new SacDomino(20);
        return new Partie(joueurs, plateau, sac);
    }

    // déroulement de la partie
    public void jouer() {
        int i = 0;
        while (p.getSac().getPiecesRestantes() != 0) {
            int tourDe = i % p.getJoueurs().length;
            System.out.println("--------------------------\n"
                    + "C'est au tour de " + p.getJoueurs()[tourDe].getName() + " !");
            System.out.println("Vous avez : " + p.getJoueurs()[tourDe].getNbPoints() + " Points !");
            affichePiece(piocherPiece(p.getJoueurs()[tourDe]));
            quelleAction(p.getJoueurs()[tourDe]);
            afficheGrille();
            i++;
        }

        System.out.println("Il n'y a plus de pièces dans le sac. Fin de la partie.");
        System.out.println("Le gagnant est " + gagnant());
    }

    public static void main(String[] args) {
        Terminal t = new Terminal();
        t.p = t.configurer();
        t.jouer();
    }

    // faire intelligence artificielle : où ?

    // afficher le tableau a corriger : beugs + mettre static + afficher les
    // coordonnees

    // pb fin de partie trop tot : même quand on passe son tour ça pioche, a priori
    // c'est le comportement normal pcq on doit defausser la piece des qu'elle est
    // piochée (pas de remise dans le sac en gros) et les joueurs ne gardent pas les
    // pieces qu'ils ne jouent pas mais du coup vu que bcp de pieces sautent il peut
    // y avoir des parties a 4 pieces

    // verifier que la fct gagnant marche
}
