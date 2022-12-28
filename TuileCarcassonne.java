public class TuileCarcassonne extends Tuile {
    public Paysage[] paysages = new Paysage[4];

    TuileCarcassonne(Paysage[] p) {
        paysages = p;
    }

    @Override
    void tourner(int nbTours) {
        if (nbTours > 0) {
            for (int i = 0; i < nbTours; i++) {
                Paysage tmp = paysages[3];
                paysages[3] = paysages[2];
                paysages[2] = paysages[1];
                paysages[1] = paysages[0];
                paysages[0] = tmp;
            }
        } else {
            for (int i = 0; i < -nbTours; i++) {
                Paysage tmp = paysages[0];
                paysages[0] = paysages[1];
                paysages[1] = paysages[2];
                paysages[2] = paysages[3];
                paysages[3] = tmp;
            }
        }
    }

    @Override
    boolean sidesMatch(Tuile p, int side) {
        return (this.paysages[side].getName().equals(((TuileCarcassonne) p).paysages[(side + 2) % 4].getName()));
    }

    @Override
    public String toString() {
        String m = "";
        for(Paysage e : paysages){
            m+= e.getName() + " " ;
        }
        return m;
    }

}
