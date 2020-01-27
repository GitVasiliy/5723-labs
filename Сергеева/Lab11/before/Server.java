
import java.net.*;
import java.io.*;

public class Server {
	public static Client client;

	public Server(String name, DatagramSocket sourceSocket) {
		this.client = new Client(name, sourceSocket, null);
	}

	public void messaging() {
		this.client.messaging();
	}
} 