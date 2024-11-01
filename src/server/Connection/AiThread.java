package server.Connection;

import server.Controller.Controller;
import server.Model.GameLogic;
import server.Model.Objects.Player;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AiThread extends Thread {

    private final double turnTimerSeconds = 0.7;
    private Player threadPlayer;

    public void run() {

        try {

            //setup
            threadPlayer = Controller.newConnection("bo", null);


            //play logic
            while (true) {
                TimeUnit.MILLISECONDS.sleep((long) (turnTimerSeconds * 1000));
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

    private void weightedMovement() {
        Random rand = new Random();
        String[] dirs = {"up", "down", "left", "right"};

        int topWeight = 0;
        int rightWeight = 0;

        for (String dir : dirs) {
            if (Controller.checkDirection(threadPlayer, dir)) {

            }
        }

    }


}
