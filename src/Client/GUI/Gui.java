package Client.GUI;


import Client.Objects.ClientObject;
import Client.Objects.ClientPlayer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Gui extends Stage {

    public static final int size = 30;
    public static final int scene_height = size * 20 + 50;
    public static final int scene_width = size * 20 + 200;

    public static Image image_floor;
    public static Image image_wall;
    public static Image hero_right, hero_left, hero_up, hero_down;
    public static Image[] chestImages = new Image[6];
    public static Image key1;


    private static Label[][] fields;
    private TextArea scoreList;
    private DataOutputStream outToServer;
    private BufferedReader inFromServer;
    private String[] eligibleKeypresses = {"a", "s", "d", "w", "x"};

    // -------------------------------------------
    // | Maze: (0,0)              | Score: (1,0) |
    // |-----------------------------------------|
    // | boardGrid (0,1)          | scorelist    |
    // |                          | (1,1)        |
    // -------------------------------------------

    public Gui(DataOutputStream outToServer, BufferedReader inFromServer) throws Exception {
        this.outToServer = outToServer;
        this.inFromServer = inFromServer;
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(0, 10, 0, 10));
        this.setTitle("TREASURE HUNTER MED LARA CROFT (TM)");

        initContent(pane);
    }

    public void initContent(GridPane grid) throws Exception {
        Text mazeLabel = new Text("Maze:");
        mazeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Text scoreLabel = new Text("Score:");
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        scoreList = new TextArea();

        GridPane boardGrid = new GridPane();

        image_wall = new Image(getClass().getResourceAsStream("Image/wall4.png"), size, size, false, false);
        image_floor = new Image(getClass().getResourceAsStream("Image/floor1.png"), size, size, false, false);

        hero_right = new Image(getClass().getResourceAsStream("Image/heroRight.png"), size, size, false, false);
        hero_left = new Image(getClass().getResourceAsStream("Image/heroLeft.png"), size, size, false, false);
        hero_up = new Image(getClass().getResourceAsStream("Image/heroUp.png"), size, size, false, false);
        hero_down = new Image(getClass().getResourceAsStream("Image/heroDown.png"), size, size, false, false);

        key1 = new Image(getClass().getResourceAsStream("Image/key.png"), size, size, false, false);

        for (int i = 1; i < 7; i++) {
            chestImages[i - 1] = new Image(getClass().getResourceAsStream("Image/chest" + i + ".png"), size, size, false, false);
        }

        fields = new Label[20][20];
        for (int j = 0; j < 20; j++) {
            for (int i = 0; i < 20; i++) {
                switch (Generel.board[j].charAt(i)) {
                    case 'w':
                        fields[i][j] = new Label("", new ImageView(image_wall));
                        break;
                    case ' ':
                        fields[i][j] = new Label("", new ImageView(image_floor));
                        break;
                    default:
                        throw new Exception("Illegal field value: " + Generel.board[j].charAt(i));
                }
                boardGrid.add(fields[i][j], i, j);
            }
        }
        scoreList.setEditable(false);

        grid.add(mazeLabel, 0, 0);
        grid.add(scoreLabel, 1, 0);
        grid.add(boardGrid, 0, 1);
        grid.add(scoreList, 1, 1);

        Scene scene = new Scene(grid, scene_width, scene_height);
        this.setScene(scene);
        addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            try {
                String keypress = event.getText();
                if (Arrays.asList(eligibleKeypresses).contains(keypress)) outToServer.writeBytes(keypress + "\n");
            } catch (IOException e) {
            }
        });
    }

    public void ShowWinner(String name, int points) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Spillet er ovre");
            alert.setHeaderText("Vinder er fundet");
            alert.setContentText("Vinder er " + name + " med " + points + " point");
            alert.showAndWait();
        });
    }

    //afslut med "slut"
    // "navn,xpos,ypos,direction,point"
    public void updateGui(List<ClientPlayer> playerList, List<ClientPlayer> oldPlayerList,
                          List<ClientObject> oldChests, List<ClientObject> chests,
                          List<ClientObject> oldKeys, List<ClientObject> keys, List<Integer> chestPoints) {
        Platform.runLater(() -> {
            updateObjects(oldChests, chests, oldKeys, keys, chestPoints);
            updatePlayerLocation(playerList, oldPlayerList);
            updateScoreboard(playerList);
        });
    }

    private void updateObjects(List<ClientObject> oldChests, List<ClientObject> chests, List<ClientObject> oldKeys, List<ClientObject> keys, List<Integer> chestPoints) {
        for (ClientObject oldChest : oldChests) {
            fields[oldChest.getX()][oldChest.getY()].setGraphic((new ImageView(image_floor)));
        }
        for (ClientObject oldKey : oldKeys) {
            fields[oldKey.getX()][oldKey.getY()].setGraphic((new ImageView(image_floor)));
        }
       int index = 0;
        for (ClientObject chest : chests) {
            fields[chest.getX()][chest.getY()].setGraphic((new ImageView(chestImages[chestPoints.get(index)-1])));
            index++;
        }
        for (ClientObject key : keys) {
            fields[key.getX()][key.getY()].setGraphic((new ImageView(key1)));
        }
    }

    private void updateScoreboard(List<ClientPlayer> playerList) {
        //Platform.runLater(() -> {
        scoreList.clear();
        for (ClientPlayer clientPlayer : playerList) {
            scoreList.appendText(clientPlayer.getName() + ":\t" + clientPlayer.getPoint() + " points\n");
        }
        //});
    }

    private void updatePlayerLocation(List<ClientPlayer> playerList, List<ClientPlayer> oldPlayerList) {
        //Platform.runLater(() -> {
        for (ClientPlayer old : oldPlayerList) {
            removePlayerOnScreen(old);
        }
        for (ClientPlayer newP : playerList) {
            placePlayerOnScreen(newP);
        }
        //});
    }
    /*
    public void start(Stage primaryStage) {
        try {GridPane grid = new GridPane();
                    // Putting default players on screen
            for (int i = 0; i < GameLogic.players.size(); i++) {
                fields[GameLogic.players.get(i).getXpos()][GameLogic.players.get(i).getYpos()].setGraphic(new ImageView(hero_up));
            }
            scoreList.setText(getScoreList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    public void removePlayerOnScreen(ClientPlayer player) {
        fields[player.getX()][player.getY()].setGraphic((new ImageView(image_floor)));

        /*Platform.runLater(() -> {
            fields[oldpos.getX()][oldpos.getY()].setGraphic(new ImageView(image_floor));
        });
        */
    }

    public void placePlayerOnScreen(ClientPlayer player) {
        String dir = player.getDirection();
        ImageView dirImage;
        switch (dir) {
            case "up":
                dirImage = new ImageView(hero_up);
                break;
            case "right":
                dirImage = new ImageView(hero_right);
                break;
            case "left":
                dirImage = new ImageView(hero_left);
                break;
            default:
                dirImage = new ImageView(hero_down);
                break;
        }
        fields[player.getX()][player.getY()].setGraphic(dirImage);
        /*
        Platform.runLater(() -> {
            int newx = newpos.getX();
            int newy = newpos.getY();
            if (direction.equals("right")) {
                fields[newx][newy].setGraphic(new ImageView(hero_right));
            }
            ;
            if (direction.equals("left")) {
                fields[newx][newy].setGraphic(new ImageView(hero_left));
            }
            ;
            if (direction.equals("up")) {
                fields[newx][newy].setGraphic(new ImageView(hero_up));
            }
            ;
            if (direction.equals("down")) {
                fields[newx][newy].setGraphic(new ImageView(hero_down));
            }
            ;
        });

         */
    }



    /*
    public static void movePlayerOnScreen(pair oldpos, pair newpos, String direction) {
        removePlayerOnScreen(oldpos);
        placePlayerOnScreen(newpos, direction);
    }
*/

    /*
    public String getScoreList() {
        StringBuffer b = new StringBuffer(100);
        for (Player p : GameLogic.players) {
            b.append(p + "\r\n");
        }
        return b.toString();
    }
*/

}

