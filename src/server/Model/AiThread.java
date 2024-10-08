package server.Model;

import server.Controller.ConnectionHandler;
import server.Controller.Controller;
import server.Model.Objects.Player;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AiThread extends Thread {

    private final long turnTimerSeconds = 2;
    private Player threadPlayer;

    public void run() {

        try {

            //setup
            threadPlayer = Controller.newPlayer("Bo", null);


            //play logic
            while (true) {
                TimeUnit.SECONDS.sleep(turnTimerSeconds);
                ConnectionHandler.updateOtherThreads();
                moveRandomly();
            }

        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void moveRandomly() {
        Random rand = new Random();
        // random number between 0-3
        int move = rand.nextInt(4);

        switch (move) {
            case 0:
                GameLogic.updatePlayer(0, -1, "up", threadPlayer);
                break;
            case 1:
                GameLogic.updatePlayer(0, +1, "down", threadPlayer);
                break;
            case 2:
                GameLogic.updatePlayer(-1, 0, "left", threadPlayer);
                break;
            case 3:
                GameLogic.updatePlayer(+1, 0, "right", threadPlayer);
                break;
        }


    }


}
