package server;

import server.Connection.ConnectionHandler;
import server.Connection.AiThread;

import java.net.*;

public class Server {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

        boolean gameStartet = false;
        ServerSocket welcomeSocket = new ServerSocket(6788);
        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            ConnectionHandler cHandler = new ConnectionHandler(connectionSocket);
            cHandler.start();
            if (!gameStartet) {
                new AiThread().start();
                gameStartet = true;
            }
        }
    }


}
