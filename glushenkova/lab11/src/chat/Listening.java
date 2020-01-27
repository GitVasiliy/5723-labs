package chat;
import java.io.*;
import java.net.*;

public class ServerThread extends Thread 
{
	DatagramSocket socket;
    DatagramPacket packet;
    String name;
    
    public ServerThread(DatagramSocket s)
    {
    	this.socket = s;
    	this.name = "Unknown";
    }

    @Override
    public void run() 
    {
        while (true)
        {
        	try
        	{
                System.out.print("Message for " + name.trim() + ": ");
        		byte[] receiveMessage = new byte[1024];
        		packet = new DatagramPacket(receiveMessage, receiveMessage.length);
        		socket.receive(packet);
        		String message = new String(packet.getData());
        		if (message.startsWith("@name")) name = message.substring(6);
        		else if (message.startsWith("@quit")) 
                {
                    System.out.println(name.trim() + " disconnected");
                    break;
                }
        		else if (packet != null) System.out.println("Message from " + name.trim() + ": " + message.trim());
        	}
        	catch(Exception e)
			{
				System.out.println("Error : " + e);
			}
        }
    }
}