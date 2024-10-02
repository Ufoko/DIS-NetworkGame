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
import java.util.stream.Collectors;

public class ConnectionHandler extends Thread {

    Player threadPlayer;
    BufferedReader inFromClient;
    DataOutputStream outToClient;
    Socket socket;

    public ConnectionHandler(Socket newSocket) {
        try {

            this.socket = newSocket;
            System.out.println(socket.getInetAddress());
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
            System.out.println(playerName);
            // TODO Check om tidligere connection
            threadPlayer = Storage.getPlayers().stream().
                    filter(player -> player.getIpAdress().equals(socket.getInetAddress())).
                    collect(Collectors.toList()).getFirst();
            if(threadPlayer != null){
                //TODO Spiller er reconnected, threadPlayer skal sættes til den fundne spiller,
                //TODO samt tjekke om han kan spawnes ind
                //TODO Der skal nok også være et check om spillet er i gang
                //TODO fx hvis man ikke må joine midtvejs
            }else{
                threadPlayer = GameLogic.newPlayer(playerName, socket.getInetAddress());
                Storage.add(threadPlayer);
            }


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

    private synchronized void informClients() throws IOException {
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
        System.out.println("Movement");
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
            if ((thread instanceof ConnectionHandler)) {
                ConnectionHandler tempHandler = (ConnectionHandler) thread;
                tempHandler.update();
            }
        }

    }


}
