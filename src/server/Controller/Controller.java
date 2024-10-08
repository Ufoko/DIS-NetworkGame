package server.Controller;

import server.Connection.ConnectionHandler;
import server.Model.GameLogic;
import server.Model.Objects.Player;
import server.Storage.Storage;

import java.io.IOException;
import java.net.InetAddress;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Controller {

    public synchronized static Player newConnection(String playerName, InetAddress ipadress) {
        Player newPlayer;
        try {
            newPlayer = Storage.getInactivePlayerList().stream().
                    filter(player -> player.getIpAdress().equals(ipadress)).
                    collect(Collectors.toList()).getFirst();
        } catch (NoSuchElementException e) {
            //No elements in inActivePlayer
            newPlayer = null;
        }

        if (newPlayer != null) {
            Storage.reAddFormerInactive(newPlayer);
            newPlayer.setLocation(GameLogic.getRandomFreePosition());
        } else {
            newPlayer = GameLogic.newPlayer(playerName, ipadress);
        }

        GameLogic.spawnKey();
        GameLogic.spawnChest();
        return newPlayer;
    }

    public synchronized static void playerLeft(Player player){
        Storage.addInactive(player);
    }


}
