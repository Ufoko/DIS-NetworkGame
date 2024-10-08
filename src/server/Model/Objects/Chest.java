package server.Model.Objects;

public class Chest {
    private Pair location;
    private int point;

    public Chest(Pair location, int point) {
        this.location = location;
        this.point = point;
    }


    public int getXpos() {
        return location.getX();
    }

    public int getYPos() {
        return location.getY();
    }


    public int getPoint() {
        return point;
    }
}
