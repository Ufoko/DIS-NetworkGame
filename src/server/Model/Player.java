package server.Model;

import java.net.InetAddress;

public class Player {
    String name;
    Pair location;
    int point;
    String direction;
    InetAddress ipAdress;

    public Player(String name, Pair loc, String direction, InetAddress ipAdress) {
        this.name = name;
        this.location = loc;
        this.direction = direction;
        this.point = 0;
        this.ipAdress = ipAdress;
    }

    ;

    public Pair getLocation() {
        return this.location;
    }

    public void setLocation(Pair p) {
        this.location = p;
    }

    public int getXpos() {
        return location.x;
    }

    public void setXpos(int xpos) {
        this.location.x = xpos;
    }

    public int getYpos() {
        return location.y;
    }

    public void setYpos(int ypos) {
        this.location.y = ypos;
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
