import java.io.*;
import java.net.*;

public class ClientThread extends Thread{
	private DatagramSocket socket;
	private String name;

	public ClientThread(DatagramSocket d, String name){
		socket = d;
		this.name = name;
	}

	public void run(){
		if (!socket.isClosed())
		{
			try{
				while (true)
				{
					byte[] receiveMessage = new byte[1024];
        			DatagramPacket receive = new DatagramPacket(receiveMessage, receiveMessage.length);
        			socket.receive(receive);
        			String message = new String(receive.getData());
        			System.out.println("");
        			System.out.println(name + ": " + message.trim());	
        		}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
	}
}