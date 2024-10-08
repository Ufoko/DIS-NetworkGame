package server.Storage;

import server.Model.Objects.Chest;
import server.Model.Objects.Key;
import server.Model.Objects.Player;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static List<Player> playerList = new ArrayList<Player>();

    private static List<Key> keyList = new ArrayList<>();

    private static List<Chest> chestList = new ArrayList<>();

    public static void add(Chest c) {
        chestList.add(c);
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
        return playerList;
    }

    public static List<Chest> getChests() {
        return chestList;
    }

    public static List<Key> getKeys() {
        return keyList;
    }
}