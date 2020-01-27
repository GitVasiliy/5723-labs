package chat;
import java.io.*;
import java.net.*;

public class ServerThread extends Thread 
{
	DatagramSocket socket;
    DatagramPacket packet;
    String name;
    public boolean isQuit = false;
    
    public ServerThread(DatagramSocket s)
    {
    	this.socket = s;
    	this.name = "Unknown";
    }

    @Override
    public void run() 
    {
        try
        {
        	while (true)
        	{
                String filename;
                System.out.print("Message for " + name.trim() + ": ");
        		byte[] receiveMessage = new byte[1024];
        		packet = new DatagramPacket(receiveMessage, receiveMessage.length);
        		socket.receive(packet);
        		String message = new String(packet.getData());
                if (message.startsWith("@send"))
                {
                    filename = message.substring(6);
                    filename = filename.trim();
                    File myFile = new File(filename);
                    if (myFile.isFile() == false) throw new Exception("file txt not found");
                    FileReader reader = new FileReader(filename);
                    byte[] file = new byte[(int)myFile.length()];
                    int c = 0;
                    String fileres = "C:\\javalab\\lab11\\files\\outServer.txt";
                    File outFile = new File(fileres);
                    FileWriter out = new FileWriter(fileres, false);
                    if (outFile.isFile() == false) throw new Exception("file txt not found");
                    String textFromFile = "";
                    while ((c = reader.read()) != -1)
                    {
                        textFromFile = textFromFile + (char)c;
                    } 
                    out.write(textFromFile);
                    out.close();    
                    System.out.println("Message from " + name.trim() + "(from file): " + textFromFile);    

                }
                if (message.startsWith("@name")) name = message.substring(6);
                else if (message.startsWith("@quit")) 
                {
                    System.out.println(name.trim() + " disconnected");
                    isQuit = true;
                    break;
                }
                else if (packet != null && message.startsWith("@send") == false) System.out.println("Message from " + name.trim() + ": " + message.trim());
        		if (message.startsWith("@name")) name = message.substring(6);
        		else if (packet != null) System.out.println("Message from " + name.trim() + ": " + message.trim());
        	}
        }
        catch(Exception e)
        {
            System.out.println("Error : " + e);
        }
    }
}