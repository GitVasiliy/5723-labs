package chat;
import java.io.*;
import java.net.*;

public class Client
{
	public static void main(String args[]) throws Exception
	{
		byte[] sendMessage = new byte[1024];
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress ip = InetAddress.getByName("127.0.0.1");
		DatagramPacket sendPacket = new DatagramPacket(sendMessage, sendMessage.length, ip, Integer.parseInt(args[0], 10));
        clientSocket.send(sendPacket);
        ClientThread t = new ClientThread(clientSocket);
		t.start();

		BufferedReader in = null;
		while (true)
		{
			if (t.isQuit == true) break;
			try
			{
				in = new BufferedReader(new InputStreamReader(System.in));
			}
			catch(Exception e)
			{
				System.out.println("Error: " + e);
			}
			System.out.print("Your message: ");
			String str = in.readLine();
			sendMessage = new byte[1024];
			sendMessage = str.getBytes();
			sendPacket = new DatagramPacket(sendMessage, sendMessage.length, ip, Integer.parseInt(args[0], 10));
			clientSocket.send(sendPacket);
			if (str.startsWith("@quit")) 
			{
				t.isQuit = true;
			}
		}	
	}
}