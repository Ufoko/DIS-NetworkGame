package server.Model;

import server.Storage.Storage;

import java.net.InetAddress;
import java.util.Random;

public class GameLogic {


    public GameLogic(String tekst) {
        super();
    }


    public static Player newPlayer(String playerName, InetAddress ipAdress) {
        Player newPlayer = new Player(playerName, getRandomFreePosition(), "up", ipAdress);
        Pair playerPos = GameLogic.getRandomFreePosition();
        newPlayer.setLocation(playerPos);
        newPlayer.setDirection("up");
        return newPlayer;
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
        player.direction = direction;
        int x = player.getXpos(), y = player.getYpos();
        Player colPlayer = getPlayerAt(x + delta_x, y + delta_y);
        if (Generel.board[y + delta_y].charAt(x + delta_x) == 'w' || colPlayer != null) {
            //player.addPoints(-1);
        } else {
            Pair oldpos = player.getLocation();
            Pair newpos = new Pair(x + delta_x, y + delta_y);
            player.setLocation(newpos);
        }


    }

    public static Player getPlayerAt(int x, int y) {
        for (Player p : Storage.getPlayers()) {
            if (p.getXpos() == x && p.getYpos() == y) {
                return p;
            }
        }
        return null;
    }


}
