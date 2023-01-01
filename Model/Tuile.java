package Model;

public abstract class Tuile {

    public abstract void tourner(int nbTours);

    abstract boolean sidesMatch(Tuile p, int side);
}
