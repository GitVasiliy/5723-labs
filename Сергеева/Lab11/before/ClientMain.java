
import java.net.*;
import java.io.*;


public class ClientMain {
	public static void main(String[] args) throws IOException {
		if (args.length < 2)
			throw new RuntimeException("Error! Too few arguments.");
		else if (args.length > 2)
			throw new RuntimeException("Error! Too many arguments.");
		DatagramSocket socket = new DatagramSocket();
		InetSocketAddress address = new InetSocketAddress(args[0], Integer.valueOf(args[1]));
		Client client = new Client("Client", socket, address);
		client.messaging();
	}
}