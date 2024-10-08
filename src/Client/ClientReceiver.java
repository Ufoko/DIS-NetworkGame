package Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientReceiver extends Thread {
    private Gui gui;
    private BufferedReader inFromServer;
    private DataOutputStream outToServer;

    public ClientReceiver(Gui gui, BufferedReader inFromServer, DataOutputStream outToServer) {
        this.gui = gui;
        this.inFromServer = inFromServer;
        this.outToServer = outToServer;
    }

    //"navn,xpos,ypos,direction,point,chestx,chesty,keyx,keyy"
    public void run() {
        List<ClientPlayer> playerList = new ArrayList<>();
        List<ClientPlayer> oldPlayerList = new ArrayList<>();
        List<ClientObject> oldChests = new ArrayList<>();
        List<ClientObject> oldKeys = new ArrayList<>();
        List<ClientObject> chests = new ArrayList<>();
        List<ClientObject> keys = new ArrayList<>();
        while (true) {
            String input = null;
            try {
                input = inFromServer.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (input.equals("info")) {
                int x = 0, y = 0, points = 0, chestx = 0, chesty = 0, keyx = 0, keyy = 0;
                String name = "", dir = "", hasKey = "";
                String[] in = null;
                while (!input.equals("slutP")) {
                    in = input.split(",");
                    name = in[0];
                    x = Integer.parseInt(in[1]);
                    y = Integer.parseInt(in[2]);
                    dir = in[3];
                    points = Integer.parseInt(in[4]);
                    hasKey = in[5];
                    playerList.add(new ClientPlayer(name, x, y, dir, points));
                    try {
                        input = inFromServer.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    input = inFromServer.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                while (!input.equals("slutC")) {
                    in = input.split(",");
                    chestx = Integer.parseInt(in[0]);
                    chesty = Integer.parseInt(in[1]);
                    chests.add(new ClientObject(chestx, chesty));
                    try {
                        input = inFromServer.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    input = inFromServer.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                while (!input.equals("slutK")) {
                    in = input.split(",");
                    keyx = Integer.parseInt(in[0]);
                    keyy = Integer.parseInt(in[1]);
                    keys.add(new ClientObject(keyx, keyy));
                    try {
                        input = inFromServer.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                gui.updateGui(new ArrayList<>(playerList), new ArrayList<>(oldPlayerList), new ArrayList<>(oldChests),
                        new ArrayList<>(chests), new ArrayList<>(oldKeys), new ArrayList<>(keys));
                oldChests = new ArrayList<>(chests);
                oldKeys = new ArrayList<>(keys);
                oldPlayerList = new ArrayList<>(playerList);
                chests.clear();
                keys.clear();
                playerList.clear();
            }
            if (input.equals("vinder")) {

            }
        }
    }
}
