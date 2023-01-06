import vueTerminal.ControleurIATerminal;
import vueTerminal.ControleurJoueurTerminal;
import vueTerminal.Terminal;

public class Main {

    public static void main(String[] args) {
        Terminal t = new Terminal();
        t.p = t.configurer();

        ControleurJoueurTerminal controleur_Terminal = new ControleurJoueurTerminal(t);
        t.controleurj = controleur_Terminal;
        ControleurIATerminal controleur_IA = new ControleurIATerminal(t);
        t.controleuria = controleur_IA;

        t.jouer();
    }
    
}
