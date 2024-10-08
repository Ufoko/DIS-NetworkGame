package server.Connection;

import server.Controller.Controller;
import server.Model.GameLogic;
import server.Model.Objects.Chest;
import server.Model.Objects.Key;
import server.Model.Objects.Player;
import server.Storage.Storage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public class ConnectionHandler extends Thread {

    private Player threadPlayer;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;
    private Socket socket;

    public ConnectionHandler(Socket newSocket) {
        try {

            this.socket = newSocket;
            System.out.println("New Connection: " + socket.getInetAddress());
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
            threadPlayer = Controller.newConnection(playerName, socket.getInetAddress());

            //Concurrent time
            while (true) {
                playerMovement();
                updateOtherThreads();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //"navn,xpos,ypos,direction,point,chestx,chesty,keyx,keyy"
    private synchronized void informClients() throws IOException {
        outToClient.writeBytes("info\n");
        sendPlayerInfo();
        sendChestInfo();
        sendKeyInfo();
        outToClient.writeBytes("slutK\n");
    }

    private void sendKeyInfo() throws IOException {
        for (Key key : Storage.getKeys()) {
            outToClient.writeBytes(key.getXPos() + ","
                    + key.getYPos()
                    + "\n");
        }
    }

    private void sendChestInfo() throws IOException {
        for (Chest chest : Storage.getChests()) {
            outToClient.writeBytes(chest.getXpos() + ","
                    + chest.getYPos() + ","
                    + chest.getPoint()
                    + "\n");
        }
        outToClient.writeBytes("slutC\n");
    }

    private void sendPlayerInfo() throws IOException {
        for (Player player : Storage.getPlayers()) {
            outToClient.writeBytes(player.getName() + ","
                    + player.getXpos() + ","
                    + player.getYpos() + ","
                    + player.getDirection() + ","
                    + player.getPoint() + ","
                    + player.hasKey()
                    + "\n");
        }
        outToClient.writeBytes("slutP\n");
    }

    private void playerMovement() throws IOException {
        try {

            switch (inFromClient.readLine().toLowerCase()) {
                case "w":
                    playerMoved(0, -1, "up");
                    System.out.println("w");
                    break;
                case "s":
                    playerMoved(0, +1, "down");
                    System.out.println("s");
                    break;
                case "a":
                    playerMoved(-1, 0, "left");
                    System.out.println("a");
                    break;
                case "d":
                    playerMoved(+1, 0, "right");
                    System.out.println("d");
                    break;
                case "x":
                    System.exit(0);
            }
        } catch (java.net.SocketException exec) {
            Controller.playerLeft(threadPlayer);
            interrupt();
        }
    }


    private void playerMoved(int delta_x, int delta_y, String direction) throws IOException {
        //giver ikke mening at flytte til en static controller da den så skal være synchronized
        GameLogic.updatePlayer(delta_x, delta_y, direction, threadPlayer);
        if (GameLogic.checkIfWon(threadPlayer.getPoint())) playerWon();
    }

    public static void updateOtherThreads() throws IOException {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        Thread mainThread;
        for (Thread thread : threadSet) {
            if (thread instanceof ConnectionHandler) {
                ConnectionHandler tempHandler = (ConnectionHandler) thread;
                tempHandler.informClients();
            }
        }
    }


    public void playerWon( ) throws IOException {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread : threadSet) {
            if (thread instanceof ConnectionHandler) {
                ConnectionHandler tempHandler = (ConnectionHandler) thread;
                tempHandler.gameEnded(threadPlayer);
            }
        }

    }

    private void gameEnded(Player winnerPlayer) throws IOException {
        outToClient.writeBytes("vinder" + ","
                + winnerPlayer.getName() + ","
                + winnerPlayer.getPoint()
                + "\n");
        this.interrupt();

    }


}
