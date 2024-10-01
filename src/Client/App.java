package Client;

import java.net.*;
import java.io.*;
import java.rmi.ServerError;

import game.GameLogic;
import game.Gui;
import javafx.application.Application;

public class App {
	public static void main(String[] args) throws Exception{
		Application.launch(LoginScreen.class);
	}
}
