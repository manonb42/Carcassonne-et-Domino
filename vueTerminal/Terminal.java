package vueTerminal;

import java.util.List;
import java.util.Scanner;

import Model.*;

public class Terminal {
    public Partie p;
    public ControleurJoueurTerminal controleurj;
    public ControleurIATerminal controleuria;
    ControleurTerminal controleur;

    public Partie getPartie(){
        return this.p;
    }

    // Dessiner une Tuile 
    private static char[][] drawTuile(TuileDomino tuile) {
        char[][] tab = new char[4][3];
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                if (tuile != null) {
                    tab[i][j] = (char) (tuile.getNumeros()[i][j] + '0');
                } else {
                    tab[i][j] = ' ';
                }
            }
        }
        return tab;
    }

    // Afficher une Tuile
    void printTuile(TuileDomino tuile) {
        char tab[][] = drawTuile(tuile);
        System.out.println("La pièce du joueur est :\n");
        System.out.print(" ");
        for (int z = 0; z < 3; z++) {
            System.out.print(tab[0][z] + " ");
        }
        System.out.print("\n");
        for (int i = 2, j = 0; i >= 0; i--, j++) {
            System.out.println(tab[3][i] + "     " + tab[1][j]);
        }
        System.out.print(" ");
        for (int z = 2; z >= 0; z--) {
            System.out.print(tab[2][z] + " ");
        }
        System.out.print("\n");
    }


    // Afficher une ligne de Tuiles
    private void printLignTuiles(List<Tuile> tuiles) {
        char tab[][][] = new char[tuiles.size()][4][3];
        for (int i = 0; i < tab.length; i++) {
            tab[i] = drawTuile((TuileDomino)(tuiles.get(i)));
        }
        if (!isEmpty(tuiles)) {
            System.out.println();
            for (int i = 0; i < tab[0].length; i++) {
                if (i == 0) {
                    printFirstLign(tab);
                } 
                if (i == 1) {
                    printSides(tab);
                }
                if (i == 2) {
                    printLastLign(tab);
                } 
            }
        }
    }

    // Afficher la première rangée d'une ligne de Tuiles 
    private void printFirstLign(char tab[][][]){
        System.out.print(" ");
                    for (int k = 0; k < tab.length; k++) {
                        for (int l = 0; l < tab[k][0].length; l++) {
                                System.out.print(tab[k][0][l]+" ");
                        }
                        System.out.print(" ");
                    } 
    }

    // Afficher les cotés d'une ligne de Tuiles
    private void printSides(char tab[][][]){
        System.out.println();
        for (int m = 0, n = 2; m < 3; m++, n--) {
            for (int k = 0; k < tab.length; k++) {
                System.out.print(tab[k][3][n] + "     " + tab[k][1][m]); 
            }System.out.println();
        }
    }

    // afficher la dernière rangée d'une ligne de Tuiles
    private void printLastLign(char tab[][][]){
        System.out.print(" ");
        for (int k = 0; k < tab.length; k++) {
            for (int l = tab[k][2].length - 1; l >= 0; l--) {
                    System.out.print(tab[k][2][l]+ " ");  
            }System.out.print(" ");
        }
    }

    // Afficher le plateau de jeu
    private void printPlateau(List<List<Tuile>> plateau) {
        for (int i = plateau.size() -1; i >= 0; i--) {
            printLignTuiles(plateau.get(i));
        }
    }

    // Vérifier si une ligne est vide 
    public boolean isEmpty(List<Tuile> l) {
        for (Tuile p : l) {
            if (p != null) {
                return false;
            }
        }
        return true;

    }

    // Lecture des Coordonnées 
    Coordonnees lectureCoordonnee() {
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

    // Lecture du nombre de tours souhaités 
    int nbTours() {
        Scanner tour = new Scanner(System.in);
        System.out.println("Combien de quart de tour voulez vous effectuer ?");
        try {
            return tour.nextInt();
        } catch (Exception e) {
            System.out.println("Mauvaise entrée. Veuillez saisir un nombre.");
            return nbTours();
        }

    }

    // Lecture du sens de tour souhaité
    String quelSens() {
        Scanner droitegauche = new Scanner(System.in);
        System.out.println("Voulez vous tourner votre pièce vers la droite ou vers la gauche ?");
        String sens = droitegauche.nextLine();
        if (!(sens.toLowerCase().equals("droite") || sens.toLowerCase().equals("gauche"))) {
            System.out.println("Mauvaise entrée.");
            return quelSens();
        }
        return sens;
    }

    // Affichage 
    public void passertour(){
        System.out.println("Le joueur passe son tour\n");
    }

    public void abandon(){
        System.out.println("Le joueur abandonne");
    }

    public void mauvaiseentree(){
        System.out.println("Mauvaise entrée. Veuillez saisir un nombre entre 1 et 3.");
    }

    

    // Placer une piece sur le plateau
    public void placement(int i) {
        if (i==1) {
            System.out.println("La pièce a bien été placée");
        }else if(i==2){
            System.out.println("Vous ne pouvez pas placer la pièce à cet endroit");
        } else if(i==3){
            System.out.println("Succès ! La pièce a bien été placée");
        } else if(i==4){
            System.out.println("Vous ne pouvez pas placer la pièce à cet endroit");
            
        }
    }

    public int demanderAction() {
        Scanner action = new Scanner(System.in);
        System.out.println(
                "\nQuelle action voulez vous effectuer ?\n1 - Placer votre pièce\n2 - Tourner votre pièce\n3 - Passer votre tour\n4 - Abandonner\n");
        try {
            return action.nextInt();
        } catch (Exception e) {
            System.out.println("Mauvaise entrée. Veuillez saisir un nombre");
            return demanderAction();
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
            int i = nbj.nextInt();
            if(i>=2 && i<=4){
                return i;
            }else{
                System.out.println("Entrez un chiffre entre 2 et 4");
                return nbJoueurs();
            }
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
            return new Joueur(nom.nextLine(), false);
        } catch (Exception e) {
            System.out.println("Mauvaise entrée.");
            return nomJoueur(pos);
        }
    }

    private boolean isIA(){
        Scanner ia = new Scanner(System.in);
        System.out.println("Est-ce une intelligence artificielle ? (oui/non)");
        String vrai_faux = ia.nextLine();
        if(vrai_faux.toLowerCase().equals("oui")){
            
            return true;
        } if(vrai_faux.toLowerCase().equals("non")){
            return false;
        } else{
            System.out.println("Mauvaise entrée.");
            return isIA();
        }
    }

    public Joueur creationjoueur( int i){
        System.out.println("joueur n" + (i + 1) + " :");
        boolean is_it_IA = isIA();
        if(is_it_IA){
            return nomIA(i);
        }else{
            return nomJoueur(i);
        }
    }

    public Joueur nomIA(int i){
        return new Joueur("Ordinateur n"+(char)(i+1+'0'), true);
    }

    // creation de la partie
    public Partie configurer() {

        // liste des joueurs
        Joueur joueurs[] = new Joueur[nbJoueurspositif()];
        // création des joueurs
        for (int i = 0; i < joueurs.length; i++) {
            joueurs[i] = creationjoueur(i);
        }
        Grille g = new Grille();
        Plateau plateau = new Plateau(g);
        SacDomino sac = new SacDomino(5);
        return new Partie(joueurs, plateau, sac);
    }

    // déroulement de la partie
    public void jouer(){
        int i = 0;
        while (p.getSac().getPiecesRestantes() != 0 && !p.getFin()) {
            int tourDe = i % p.getJoueurs().length;
            if (!p.getJoueurs()[tourDe].getAbandon()) { 
                System.out.println("\n--------------------------\n"
                        + "C'est au tour de " + p.getJoueurs()[tourDe].getName() + " !");

                System.out.println("Score du joueur : " + p.getJoueurs()[tourDe].getNbPoints() + " points");

                if(p.getJoueurs()[tourDe].getisIA()){
                    printTuile(controleuria.piocherPiece(p.getJoueurs()[tourDe]));
                    controleuria.quelleAction(p.getJoueurs()[tourDe]);
                } else{
                    printTuile(controleurj.piocherPiece(p.getJoueurs()[tourDe]));
                    controleurj.quelleAction(p.getJoueurs()[tourDe]);
                }
                printPlateau(p.getPlateau().getGrille().getListPieces());
                System.out.println(p.getSac().nbPiecesRestantes);
            }
            i++;
        }

        System.out.println("\nFin de la partie.");
        if (gagnant() != null) {
            System.out.println("Le gagnant est " + gagnant());
        } else {
            System.out.println("Il n'y a pas de gagnant.");
        }

    }
}
