import vueGraphique.MenuPrincipal;

public class JeuGraphique {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                new MenuPrincipal();
            }
        });
    }
    
}
