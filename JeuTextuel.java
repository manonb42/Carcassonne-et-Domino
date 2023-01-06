import vueTerminal.ControleurIATerminal;
import vueTerminal.ControleurJoueurTerminal;
import vueTerminal.Terminal;

public class JeuTextuel {

    public static void main(String[] args) {
        Terminal t = new Terminal();
        t.setPartie(t.configurer());

        t.setControleurJoueur(new ControleurJoueurTerminal(t));
        t.setControleurIA(new ControleurIATerminal(t));

        t.jouer();
    }
    
}
