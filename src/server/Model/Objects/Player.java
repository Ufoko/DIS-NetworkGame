package server.Model.Objects;

import java.net.InetAddress;

public class Player {
    private static final int WINSCORE = 6;
    private String name;
    private Pair location;
    private int point;
    private String direction;
    private InetAddress ipAdress;
    private boolean hasKey = false;

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


    /***
     * add points to the player
     * @param p amount of points given to player
     * @return true if the player has won
     */
    public void addPoints(int p) {
        point += p;
    }

    public boolean hasWon() {
        return point >= WINSCORE;
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
