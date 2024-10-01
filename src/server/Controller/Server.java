package server.Controller;

import java.net.*;
public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {


		ServerSocket welcomeSocket = new ServerSocket(6788);
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			ConnectionHandler cHandler = new ConnectionHandler(connectionSocket);
			cHandler.start();
		}
	}







}
