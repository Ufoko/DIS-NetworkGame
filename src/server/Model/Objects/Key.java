package server.Model.Objects;

public class Key {

    private Pair location;

    public Key(Pair location) {
        this.location = location;
    }

    public int getYPos() {
        return location.getY();
    }

    public int getXPos() {
        return location.getX();
    }
}
