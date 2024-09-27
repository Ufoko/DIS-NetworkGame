package server.Model;

import server.Model.Player;
import server.Model.pair;
import server.Model.Generel;
import server.Storage.playerStorage;

import java.util.Random;

public class GameLogic {


	public GameLogic(String tekst) {
		super();
	}


	public static Player newPlayer(String playerName){
		Player newPlayer = new Player(playerName,getRandomFreePosition(),"up");
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


	public static pair getRandomFreePosition()
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
				for (Player p: playerStorage.getListe()) {
					if (p.getXpos()==x && p.getYpos()==y) //pladsen optaget af en anden
						foundfreepos = false;
				}

			}
		}
		pair p = new pair(x,y);
		return p;
	}





}
