import java.io.*;
import java.net.*;

public class UDPClient {
	public static void main(String args[]) throws IOException, InterruptedException {
		int port = 9000;
		byte[] sendMessage = new byte[1024];
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress ip = InetAddress.getByName("localhost");
		try{
			DatagramPacket sendPacket = new DatagramPacket(sendMessage, sendMessage.length, ip, port);
        	clientSocket.send(sendPacket);
        	ServerThread server = new ServerThread(port, ip, clientSocket);
			server.start();
			String name = "Server";
        	ClientThread client = new ClientThread(clientSocket, name);
			client.start();
			server.interrupt();
			client.interrupt();
		}
		catch(Exception ex){}
	}
}