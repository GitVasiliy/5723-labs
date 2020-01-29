import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.lang.Math;
import java.util.*;

public class Qwe extends Thread {
	private InputOutput inputOutput;
	private Client client;
	private Receiver clientName;


	public Qwe(InputOutput inputOutput, Client client) {
		this.inputOutput = inputOutput;
		this.client = client;
		this.clientName = r;
	}

	public void run() {
		try{
			while(true){
				Thread.sleep(5000);
				send("qwe");
				DatagramSocket socket = this.client.getSocket();
				byte[] b = new byte[1024];
				String s = "Hello";
				b = s.getBytes();
				DatagramPacket packet = new DatagramPacket(b, 0, b.length, client.getAddress());
				socket.send(packet);
			}
		}
		catch(Exception e){}
	}

}