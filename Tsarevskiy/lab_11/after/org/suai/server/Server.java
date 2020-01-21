package org.suai.server;


import java.net.DatagramSocket;

import org.suai.client.Client;


public class Server {

	private Client client;


	public Server(String name, DatagramSocket sourceSocket) {
		this.client = new Client(name, sourceSocket, null);
	}


	public void messaging() {
		this.client.messaging();
	}

}