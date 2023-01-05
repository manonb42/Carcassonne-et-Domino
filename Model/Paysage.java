package Model;

public abstract class Paysage {
    private String name;
    private boolean pion;

    public String getName() {
        return name;
    }

    public void setPion(boolean pion) {
        this.pion = pion;
    }

    public void setName(String name) {
        this.name = name;
    }
}
