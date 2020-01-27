package chat;
import java.io.*;
import java.net.*;

public class Server //на TCP
{
	public static void main(String args[]) throws Exception
	{
		DatagramSocket serverSocket = new DatagramSocket(Integer.parseInt(args[0], 10));
		byte[] receiveMessage = new byte[1024];
		DatagramPacket receivePacket;
		receivePacket = new DatagramPacket(receiveMessage, receiveMessage.length);
		serverSocket.receive(receivePacket);
		InetAddress ip = receivePacket.getAddress();
		int port = receivePacket.getPort();
		ServerThread t = new ServerThread(serverSocket);
		t.start();

		BufferedReader in = null;
		while(true)
		{
			//if (t.isQuit == true) break;
			try
			{
				in = new BufferedReader(new InputStreamReader(System.in));
			}
			catch(Exception e)
			{
				System.out.println("Error: " + e);
			}
			String str = in.readLine();
			byte[] sendMessage = new byte[1024];
			sendMessage = str.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendMessage, sendMessage.length, ip, port);
			serverSocket.send(sendPacket);
			//if (str.startsWith("@quit")) break; // добавить что порт = 0 аналог клиент
		}
	}
}