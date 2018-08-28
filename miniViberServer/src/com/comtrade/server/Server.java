package com.comtrade.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.comtrade.threads.ThreadRequirementsProcessing;

public class Server extends Thread {

	public void run() {
		try {
			startServer();
		} catch (Exception e) {
			System.out.println("Error at server starting! "+e.getMessage());
		}
	}

	@SuppressWarnings("resource")
	private void startServer() {
		try {
			ServerSocket ss = new ServerSocket(9000);
			System.out.println("Server started and ready to accept clients");
			while(true) {
				Socket s = ss.accept();
				ThreadRequirementsProcessing trp = new ThreadRequirementsProcessing();
				trp.setSocket(s);
				trp.start();
			}
		} catch (IOException e) {
			System.out.println("Server already started");
			System.exit(0);
//			e.printStackTrace();
		}
	}
	
}
