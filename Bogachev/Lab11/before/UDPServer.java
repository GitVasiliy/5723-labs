import java.io.*;
import java.net.*;

public class UDPServer {
	public static void main(String args[]) throws IOException, InterruptedException {
		DatagramSocket serverSocket = new DatagramSocket(9000);
		byte[] receiveData = new byte[1024];
		DatagramPacket receive = new DatagramPacket(receiveData, receiveData.length);
		serverSocket.receive(receive);
		int port = receive.getPort();
		InetAddress ip = receive.getAddress();
		String name = "Client";
		ClientThread client = new ClientThread(serverSocket, name);
		client.start();
		ServerThread server = new ServerThread(port, ip, serverSocket);
		server.start();
	}
}
