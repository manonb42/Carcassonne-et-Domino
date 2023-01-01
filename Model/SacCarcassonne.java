package Model;

public class SacCarcassonne extends Sac {
    protected int nbPiecesRestantes=71;
    protected TuileCarcassonne[] pieces = new TuileCarcassonne[71];

    public SacCarcassonne(){
        for(int i = 0; i<9; i++){
            Paysage[] p = {new Pre(), new Pre(), new Route(), new Route()};
            pieces[i] = new TuileCarcassonne(p);
        }
        for(int i = 9; i<12; i++){
            Paysage[] p = {new Village(), new Route(), new Route(), new Pre()};
            pieces[i] = new TuileCarcassonne(p);
        }
        for(int i = 12; i<13; i++){
            Paysage[] p = {new Village(), new Village(), new Route(), new Village()};
            pieces[i] = new TuileCarcassonne(p);
        }
        for(int i = 13; i<14; i++){
            Paysage[] p = {new Village(), new Village(), new Pre(), new Village()};
            pieces[i] = new TuileCarcassonne(p);
        }
        for(int i = 14; i<19; i++){
            Paysage[] p = {new Village(), new Route(), new Route(), new Village()};
            pieces[i] = new TuileCarcassonne(p);
        }
        for(int i = 19; i<22; i++){
            Paysage[] p = {new Village(), new Route(), new Route(), new Route()};
            pieces[i] = new TuileCarcassonne(p);
        }
        for(int i = 22; i<30; i++){
            Paysage[] p = {new Route(), new Pre(), new Route(), new Pre()};
            pieces[i] = new TuileCarcassonne(p);
        }
        for(int i = 30; i<34; i++){
            Paysage[] p = {new Pre(), new Route(), new Route(), new  Route()};
            pieces[i] = new TuileCarcassonne(p);
        }
        for(int i = 34; i<39; i++){
            Paysage[] p = {new Village(), new Pre(), new Pre(), new  Pre()};
            pieces[i] = new TuileCarcassonne(p);
        }
        for(int i = 39; i<41; i++){
            Paysage[] p = {new Village(), new Village(), new Pre(), new  Pre()};
            pieces[i] = new TuileCarcassonne(p);
        }
        for(int i = 41; i<44; i++){
            Paysage[] p = {new Village(), new Village(), new Pre(), new  Village()};
            pieces[i] = new TuileCarcassonne(p);
        }
        for(int i = 44; i<48; i++){
            Paysage[] p = {new Pre(), new Pre(), new Pre(), new  Pre()};
            pieces[i] = new TuileCarcassonne(p);
        }
        for(int i = 48; i<50; i++){
            Paysage[] p = {new Pre(), new Pre(), new Route(), new  Pre()};
            pieces[i] = new TuileCarcassonne(p);
        }
        for(int i = 50; i<53; i++){
            Paysage[] p = {new Village(), new Pre(), new Pre(), new  Village()};
            pieces[i] = new TuileCarcassonne(p);
        }
        for(int i = 53; i<55; i++){
            Paysage[] p = {new Pre(), new Village(), new Pre(), new  Village()};
            pieces[i] = new TuileCarcassonne(p);
        }
        for(int i = 55; i<59; i++){
            Paysage[] p = {new Village(), new Route(), new Pre(), new  Route()};
            pieces[i] = new TuileCarcassonne(p);
        }
        for(int i = 59; i<62; i++){
            Paysage[] p = {new Village(), new Pre(), new Route(), new  Route()};
            pieces[i] = new TuileCarcassonne(p);
        }
        for(int i = 62; i<63; i++){
            Paysage[] p = {new Pre(), new Village(), new Pre(), new  Village()};
            pieces[i] = new TuileCarcassonne(p);
        }
        for(int i = 63; i<65; i++){
            Paysage[] p = {new Village(), new Village(), new Route(), new  Village()};
            pieces[i] = new TuileCarcassonne(p);
        }
        for(int i = 65; i<66; i++){
            Paysage[] p = {new Village(), new Village(), new Village(), new  Village()};
            pieces[i] = new TuileCarcassonne(p);
        }
        for(int i = 65; i<66; i++){
            Paysage[] p = {new Village(), new Village(), new Village(), new  Village()};
            pieces[i] = new TuileCarcassonne(p);
        }
        for(int i = 66; i<68; i++){
            Paysage[] p = {new Village(), new Pre(), new Pre(), new Village()};
            pieces[i] = new TuileCarcassonne(p);
        }
        for(int i = 68; i<71; i++){
            Paysage[] p = {new Pre(), new Village(), new Pre(), new Village()};
            pieces[i] = new TuileCarcassonne(p);
        }
        

    }

    boolean sacVide(){
        for(int i = 0; i<pieces.length; i++){
            if(pieces[i] != null){
                return false;
            }
        }
        return true;
    }

    @Override
    public int getPiecesRestantes() {
        return this.nbPiecesRestantes;
    }



    @Override
    public TuileCarcassonne piocher() {
        nbPiecesRestantes--;
        int n =(int) (Math.random()*71);
        while(pieces[n] == null){
            n = (int) (Math.random()*71);
        }
        TuileCarcassonne t = pieces[n];
        pieces[n] = null;
        System.out.println(n);
        return t;
    }

    @Override
    public String toString() {
        String m = "";
        int i = 0;
        for(TuileCarcassonne e : pieces){
            m+= i + " : " + e + " ";
            i++;
        }
        return m;
    }
    /*public static void main(String[] args) {
        SacCarcassonne s = new SacCarcassonne();
        System.out.println(s.piocher());
    }*/
}
