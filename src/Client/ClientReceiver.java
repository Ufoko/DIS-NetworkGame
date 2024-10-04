package Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientReceiver extends Thread{
    private Gui gui;
    private BufferedReader inFromServer;
    private DataOutputStream outToServer;
    public ClientReceiver(Gui gui, BufferedReader inFromServer, DataOutputStream outToServer) {
        this.gui = gui;
        this.inFromServer = inFromServer;
        this.outToServer = outToServer;
    }
    public void run() {
        List<ClientPlayer> playerList = new ArrayList<>();
        List<ClientPlayer> oldPlayerList = new ArrayList<>();
        while (true) {
            String input = null;
            try {
                input = inFromServer.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            int x = 0, y = 0, points = 0;
            String name = "", dir = "";

            while (!input.equals("slut")) {
                String[] in = input.split(",");
                name = in[0];
                x = Integer.parseInt(in[1]);
                y = Integer.parseInt(in[2]);
                dir = in[3];
                points = Integer.parseInt(in[4]);
                playerList.add(new ClientPlayer(name, x, y, dir, points));
                try {
                    input = inFromServer.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            gui.updateGui(new ArrayList<>(playerList), new ArrayList<>(oldPlayerList));
            oldPlayerList = new ArrayList<>(playerList);
            playerList.clear();
        }
    }
}
