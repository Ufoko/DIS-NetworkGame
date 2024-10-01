package server.Controller;

import javafx.application.Platform;
import server.Model.GameLogic;
import server.Model.Pair;
import server.Model.Player;
import server.Storage.Storage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Set;

public class ConnectionHandler extends Thread {

    Player threadPlayer;
    BufferedReader inFromClient;
    DataOutputStream outToClient;
    Socket socket;

    public ConnectionHandler(Socket newSocket) {
        try {
            this.socket = newSocket;
            inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outToClient = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        String playerName = null;
        try {
            playerName = inFromClient.readLine();
            // TODO Check om tidligere connection
            Storage.add(GameLogic.newPlayer(playerName, socket.getInetAddress()));
            Pair playerPos = GameLogic.getRandomFreePosition();


            //Concurrent time
            while (true) {


                playerMovement();


                updateOtherThreads();
            }

        } catch (IOException e) {
            //Gem data
            e.printStackTrace();
        }
    }

    private synchronized void informClients() throws IOException  {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread : threadSet) {
            for (Player player : Storage.getPlayers()) {
                outToClient.writeBytes(player.getName() + ","
                        + threadPlayer.getXpos() + ","
                        + threadPlayer.getYpos() + ","
                        + threadPlayer.getDirection() + ","
                        + threadPlayer.getPoint() + "\n");
            }
            outToClient.writeBytes("slut");
        }
    }

    private void playerMovement() throws IOException {
        switch (inFromClient.readLine().toLowerCase()) {
            case "w":
                playerMoved(0, -1, "up");
                break;
            case "s":
                playerMoved(0, +1, "down");
                break;
            case "a":
                playerMoved(-1, 0, "left");
                break;
            case "d":
                playerMoved(+1, 0, "right");
                break;
            case "x":
                System.exit(0);
        }

        informClients();
    }


    private void playerMoved(int delta_x, int delta_y, String direction) {
        GameLogic.updatePlayer(delta_x, delta_y, direction, threadPlayer);
    }

    public void update() throws IOException {

    }

    private void updateOtherThreads() throws IOException {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread : threadSet) {
            ConnectionHandler tempHandler = (ConnectionHandler) thread;
            tempHandler.update();
        }

    }


}
