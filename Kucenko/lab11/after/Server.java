import java.net.DatagramSocket;

public class Server {

	private Client client;


	public Server(String name, DatagramSocket sourceSocket) {
		this.client = new Client(name, sourceSocket, null);
	}


	public void messaging() {
		this.client.messaging();
	}

}