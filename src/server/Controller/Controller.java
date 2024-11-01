package server.Controller;

import server.Connection.ConnectionHandler;
import server.Model.GameLogic;
import server.Model.Objects.Chest;
import server.Model.Objects.Key;
import server.Model.Objects.Player;
import server.Storage.Storage;

import java.io.IOException;
import java.net.InetAddress;
import java.util.NoSuchElementException;
import java.util.Objects;
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

    public synchronized static void playerLeft(Player player) {
        Storage.addInactive(player);
    }


    public static Object checkDirection(Player player, String dir) {
        int topDir = 0;
        int rightDir = 0;
        if (dir.equals("up")) topDir = 1;
        if (dir.equals("down")) topDir = -1;
        if (dir.equals("right")) rightDir = 1;
        if (dir.equals("left")) rightDir = -1;
        for (Key key : Storage.getKeys()) {
            if (key.getXPos() == player.getXpos() + rightDir) {
                return key;
            } else if (key.getYPos() == player.getYpos() + topDir) {
                return key;
            }
        }
        for (Chest chest : Storage.getChests()) {
            if(chest.getXpos() == player.getXpos() + rightDir)return chest;
            if(chest.getYPos() == player.getYpos() + topDir) return chest;
        }



    }

}
