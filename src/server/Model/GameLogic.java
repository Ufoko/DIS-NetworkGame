package server.Model;

import game.Gui;
import server.Controller.Server;
import server.Storage.Storage;

import java.net.InetAddress;
import java.util.Random;

public class GameLogic {


	public GameLogic(String tekst) {
		super();
	}


	public static Player newPlayer(String playerName, InetAddress ipAdress){
		Player newPlayer = new Player(playerName,getRandomFreePosition(),"up",ipAdress);
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
		while  (!foundfreepos) {
			Random r = new Random();
			x = Math.abs(r.nextInt()%18) +1;
			y = Math.abs(r.nextInt()%18) +1;
			if (Generel.board[y].charAt(x)==' ') // er det gulv ?
			{
				foundfreepos = true;
				for (Player p: Storage.getListe()) {
					if (p.getXpos()==x && p.getYpos()==y) //pladsen optaget af en anden
						foundfreepos = false;
				}

			}
		}
		Pair p = new Pair(x,y);
		return p;
	}
	public static void updatePlayer(int delta_x, int delta_y, String direction, Player player)
	{
		player.direction = direction;
		int x = player.getXpos(),y = player.getYpos();

		if (game.Generel.board[y+delta_y].charAt(x+delta_x)=='w') {
			player.addPoints(-1);
		}
		else {
			// collision detection
			server.Model.Player colPlayer = getPlayerAt(x+delta_x,y+delta_y);
			if (colPlayer!=null) {
				player.addPoints(10);
				//update the other player
				colPlayer.addPoints(-10);
				Pair pa = getRandomFreePosition();
				colPlayer.setLocation(pa);
				Pair oldpos = new Pair(x+delta_x,y+delta_y);
				Gui.movePlayerOnScreen(oldpos,pa,colPlayer.direction);
			} else
				player.addPoints(1);
			Pair oldpos = player.getLocation();
			Pair newpos = new Pair(x+delta_x,y+delta_y);
			Gui.movePlayerOnScreen(oldpos,newpos,direction);
			player.setLocation(newpos);
		}


	}
	public static Server.Player getPlayerAt(int x, int y) {
		for (Server.Player p : players) {
			if (p.getXpos()==x && p.getYpos()==y) {
				return p;
			}
		}
		return null;
	}



}
