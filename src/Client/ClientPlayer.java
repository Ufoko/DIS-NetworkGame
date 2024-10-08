package Client;

public class ClientPlayer {
    String name;
    int x, y;
    String direction;
    int point;

    public ClientPlayer(String name, int x, int y, String direction, int point) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.point = point;
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getDirection() {
        return direction;
    }

    public int getPoint() {
        return point;
    }
    public String toString() {
        return this.name +" "+ this.getX() +" "+ this.getY() +" " + this.direction + " " +this.point;
    }
}