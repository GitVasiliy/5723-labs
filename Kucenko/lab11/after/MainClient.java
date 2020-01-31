import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;

import java.io.IOException;



public class MainClient {

	public static void sendConnect(DatagramSocket sourceSocket, SocketAddress destinationSocket) {
		try {
			byte[] data = "@connect".getBytes();
			DatagramPacket packet = new DatagramPacket(data, data.length, destinationSocket);

			sourceSocket.send(packet);
		}
		catch(IOException exception) {
			System.out.println("SYSTEM: Error connection.");
		}
	}


	public static void main(String[] args) {
		if(args.length < 2) {
			System.out.println("SYSTEM: Incorrect argument.");
		}
		else {
			DatagramSocket sourceSocket = null;
			InetSocketAddress destinationSocket = null;
			
			try {
				sourceSocket = new DatagramSocket();

				int port = Integer.valueOf(args[1]);
				destinationSocket = new InetSocketAddress(args[0], port);
			}
			catch(IOException | RuntimeException exception) {
				System.out.println("SYSTEM: Incorrect argument\n" + exception + ".");
				return;
			}

			Client client = new Client("Client", sourceSocket, destinationSocket);
			sendConnect(sourceSocket, destinationSocket);
			client.messaging();
		}
	}

}
