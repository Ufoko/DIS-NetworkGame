package server.Controller;

import server.Model.GameLogic;
import server.Model.Objects.Player;

import java.net.InetAddress;

public class Controller {

    public static Player newPlayer(String playerName, InetAddress ipadress) {
        Player newPlayer = GameLogic.newPlayer(playerName, ipadress);
        GameLogic.spawnKey();
        GameLogic.spawnChest();
        return newPlayer;
    }


}
