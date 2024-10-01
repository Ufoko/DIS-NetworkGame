package Client;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class LoginScreen extends Application {
    TextField txfName, txfIP;
    Button btnOk;
    Label lblErr = new Label("enter name and valid ip!");

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane grid = new GridPane();
        primaryStage.setTitle("LOGIN TIL TREASURE HUNTER MED LARA CROFT (TM)");
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));
        Scene scene = new Scene(grid, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        txfName = new TextField();
        txfIP = new TextField();
        btnOk = new Button("OK");
        grid.add(new Label("name: "), 0, 1);
        grid.add(txfName, 1, 1);
        grid.add(new Label("ip: "), 0, 2);
        grid.add(txfIP, 1, 2);
        grid.add(btnOk, 1, 3);
        btnOk.setOnAction(event -> {
            if (!txfIP.getText().equals("") && !txfName.equals("")) {
                if (!txfName.getText().toLowerCase().equals("slut")) {
                    try {
                        Socket clientSocket = new Socket(txfIP.getText(), 6788);
                        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        outToServer.writeBytes(txfName.getText() + '\n');
                        Thread.sleep(3000);
                        try {
                            openGui(outToServer, inFromServer);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } catch (IOException e) {
                        grid.add(lblErr, 2, 2);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void openGui(DataOutputStream outToServer, BufferedReader inFromServer) throws Exception {
        Gui gui = new Gui(outToServer, inFromServer);
    }
}