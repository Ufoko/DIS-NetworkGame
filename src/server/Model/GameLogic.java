package server.Model;

import server.Model.Objects.Chest;
import server.Model.Objects.Key;
import server.Model.Objects.Pair;
import server.Model.Objects.Player;
import server.Storage.Storage;

import java.net.InetAddress;
import java.util.Random;

public class GameLogic {

    private final static int MAXPOINT = 6;
    private final static int MINPOINT = 1;

    public static Player newPlayer(String playerName, InetAddress ipAdress) {
        Player newPlayer = new Player(playerName, getRandomFreePosition(), "up", ipAdress);
        Storage.add(newPlayer);
        return newPlayer;
    }

    public static Key spawnKey() {
        Key newKey = new Key(getRandomFreePosition());
        Storage.add(newKey);
        return newKey;
    }

    public static Chest spawnChest() {
        //Generates random int, min is inclusive, max is exclusive
        int randomPoint = new Random().nextInt(MINPOINT, MAXPOINT + 1);
        Chest newChest = new Chest(getRandomFreePosition(), randomPoint);
        Storage.add(newChest);
        return newChest;
    }

/*	public static void makePlayers(String name) {
		pair p=getRandomFreePosition();
		me = new game.Player(name,p,"up");
		players.add(me);
		p=getRandomFreePosition();
		game.Player harry = new game.Player("Kaj",p,"up");
		players.add(harry);
	}*/


    public static Pair getRandomFreePosition()
    // finds a random new position which is not wall
    // and not occupied by other players
    {
        int x = 1;
        int y = 1;
        boolean foundfreepos = false;
        while (!foundfreepos) {
            Random r = new Random();
            x = Math.abs(r.nextInt() % 18) + 1;
            y = Math.abs(r.nextInt() % 18) + 1;
            if (Generel.board[y].charAt(x) == ' ') // er det gulv ?
            {
                foundfreepos = true;
                for (Player p : Storage.getPlayers()) {
                    if (p.getXpos() == x && p.getYpos() == y) //pladsen optaget af en anden
                        foundfreepos = false;
                }

            }
        }
        Pair p = new Pair(x, y);
        return p;
    }

    public static void updatePlayer(int delta_x, int delta_y, String direction, Player player) {
        player.setDirection(direction);
        int x = player.getXpos(), y = player.getYpos();
        Player colPlayer = getPlayerAt(x + delta_x, y + delta_y);
        Chest colChest = getChestAt(x + delta_x, y + delta_y);
        Key colKey = getKeyAt(x + delta_x, y + delta_y);


        if (Generel.board[y + delta_y].charAt(x + delta_x) == 'w' || colPlayer != null) {

        } else {
            boolean move = true;  // Can the player move
            if (colChest != null) { // Is the player trying to move to a chest
                if (player.hasKey()) { // Does the player have a key
                    player.addPoints(colChest.getPoint()); // Add points to player
                    Storage.remove(colChest); // Remove the chest from game
                    spawnChest(); // Spawns a new chest
                } else move = false; // Player doesn't have a key, so can't move to tile
            } else if (colKey != null) { //Player trying to move to a key
                if (!player.hasKey()) { // Player doesn't have a key
                    player.setKey(true); // Player gets a key
                    Storage.remove(colKey); // Removes the key from game
                    spawnKey(); // Spawn a key
                } else move = false; // In the case player HAS a key, can't move
            }
            if (move) {
                Pair newpos = new Pair(x + delta_x, y + delta_y);
                player.setLocation(newpos);
            }
        }


    }

    private static Chest getChestAt(int x, int y) {
        for (Chest chest : Storage.getChests()) {
            if (chest.getXpos() == x && chest.getYPos() == y) {
                return chest;
            }
        }
        return null;
    }

    private static Key getKeyAt(int x, int y) {
        for (Key key : Storage.getKeys()) {
            if (key.getXPos() == x && key.getYPos() == y) {
                return key;
            }
        }
        return null;
    }


    private static Player getPlayerAt(int x, int y) {
        for (Player p : Storage.getPlayers()) {
            if (p.getXpos() == x && p.getYpos() == y) {
                return p;
            }
        }
        return null;
    }


}
