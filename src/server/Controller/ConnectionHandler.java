package server.Controller;

import javafx.application.Platform;
import server.Model.GameLogic;
import server.Model.Pair;
import server.Storage.Storage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ConnectionHandler extends Thread {

    public ConnectionHandler(Socket newSocket) {
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(newSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(newSocket.getOutputStream());

            String playerName = inFromClient.readLine();

            // TODO Check om tidligere connection
            Storage.add(GameLogic.newPlayer(playerName,newSocket.getInetAddress()));
            Pair playerPos = GameLogic.getRandomFreePosition();



            //Concurrent time
            while (true){


                switch (inFromClient.readLine().toLowerCase()) {
            case "w": playerMoved(0,-1,"up"); break;
            case "s": playerMoved(0,+1,"down"); break;
            case "a": playerMoved(-1,0,"left"); break;
            case "d": playerMoved(+1,0,"right"); break;
            case "x": System.exit(0);






                }

        }
        } catch (IOException e) {
            //gem data
            e.printStackTrace();
        }
    }

    public void updateScoreTable()
    {
        Platform.runLater(() -> {
            scoreList.setText(getScoreList());
        });
    }
    public void playerMoved(int delta_x, int delta_y, String direction) {
        server.GameLogic.updatePlayer(delta_x,delta_y,direction);
        updateScoreTable();
    }


}
