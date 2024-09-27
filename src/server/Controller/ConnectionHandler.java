package server.Controller;

import server.Model.GameLogic;
import server.Storage.playerStorage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import server.Model.Player;

public class ConnectionHandler extends Thread {

    public ConnectionHandler(Socket newSocket) {
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(newSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(newSocket.getOutputStream());

            String playerName = inFromClient.readLine();

            // TODO Check om tidligere connection
            playerStorage.add(GameLogic.newPlayer(playerName));


        } catch (IOException e) {
            //gem data
            e.printStackTrace();
        }
    }




}
