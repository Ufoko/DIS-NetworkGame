package server.Storage;

import server.Model.Objects.Chest;
import server.Model.Objects.Key;
import server.Model.Objects.Player;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static List<Player> playerList = new ArrayList<Player>();
    private static List<Player> inactivePlayerList = new ArrayList<>();

    private static List<Key> keyList = new ArrayList<>();

    private static List<Chest> chestList = new ArrayList<>();

    public static void add(Chest c) {
        chestList.add(c);
    }

    public static void addInactive(Player p) {
        System.out.println("ADD before: " + inactivePlayerList.size());
        System.out.println("plist: " + playerList.size());
        remove(p);
        inactivePlayerList.add(p);
    }

    public static void reAddFormerInactive(Player p) {
        System.out.println("READD before: " + inactivePlayerList.size());
        System.out.println("plist: " + playerList.size());
        inactivePlayerList.remove(p);
        add(p);
    }

    public static void add(Player p) {
        playerList.add(p);
    }

    public static void add(Key k) {
        keyList.add(k);
    }

    public static void remove(Player p) {
        playerList.remove(p);
    }

    public static void remove(Key k) {
        keyList.remove(k);
    }

    public static void remove(Chest c) {
        chestList.remove(c);
    }

    public static int playerSize() {
        return playerList.size();
    }

    public static int keySize() {
        return keyList.size();
    }

    public static int chestSize() {
        return chestList.size();
    }

    public static List<Player> getPlayers() {
        return new ArrayList<>(playerList);
    }

    public static List<Chest> getChests() {
        return new ArrayList<>(chestList);
    }

    public static List<Key> getKeys() {
        return new ArrayList<>(keyList);
    }

    public static List<Player> getInactivePlayerList() {
        return new ArrayList<>(inactivePlayerList);
    }

    public static void clearStorage() {
        playerList = new ArrayList<Player>();
        keyList = new ArrayList<Key>();
        chestList = new ArrayList<Chest>();
    }

}