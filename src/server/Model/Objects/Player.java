package server.Model.Objects;

import java.net.InetAddress;

public class Player {
  private  String name;
    private  Pair location;
    private   int point;
    private   String direction;
    private   InetAddress ipAdress;
    private    boolean hasKey = false;

    public Player(String name, Pair loc, String direction, InetAddress ipAdress) {
        this.name = name;
        this.location = loc;
        this.direction = direction;
        this.point = 0;
        this.ipAdress = ipAdress;
    }



    public Pair getLocation() {
        return this.location;
    }

    public void setLocation(Pair p) {
        this.location = p;
    }

    public int getXpos() {
        return location.getX();
    }

    public void setKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    public boolean hasKey() {
        return hasKey;
    }

    public void setXpos(int xpos) {
        this.location.setX(xpos);
    }

    public int getYpos() {
        return location.getY();
    }

    public void setYpos(int ypos) {
        this.location.setY(ypos);
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void addPoints(int p) {
        point += p;
    }

    public String getName() {
        return name;
    }

    public int getPoint() {
        return point;
    }

    public InetAddress getIpAdress() {
        return ipAdress;
    }

    public String toString() {
        return name + ":   " + point;
    }
}
