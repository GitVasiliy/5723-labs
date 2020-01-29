import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.lang.Math;


public class Receiver extends Thread {
	private InputOutput inputOutput;
	private Client client;
	private String clientName = "Client";

	public Receiver(InputOutput inputOutput, Client client) {
		this.inputOutput = inputOutput;
		this.client = client;
	}

	public DatagramPacket receive() throws IOException {
		DatagramSocket socket = this.client.getSocket();
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        socket.receive(packet);
        return packet;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void run() {
		try {
			while(true) {
				if (isInterrupted()) {
					throw new InterruptedException();
				}
				DatagramPacket packet = this.receive();
				if (this.client.getAddress() == null) {
					this.client.setAddress(packet.getSocketAddress());
				}
            	String message =  new String (packet.getData(), "UTF-8");
            	String str = new String();
            	String g = new String();
            	int j = 7;
            	char tmp = '\n';
            	this.clientName = new String();
            	while(message.charAt(j) != tmp){
            		this.clientName += message.charAt(j);
            		j++;
            	}
            	for(int k = j + 1; k < message.length(); k++) {
            		if(message.charAt(k) == 0)
            			break;
            		g += message.charAt(k);
            	}
            	for(int i = 0; i < message.length(); i++) {
            		if(message.charAt(i) == 0)
            			break;
            		str += message.charAt(i);
            	}
            	message = message.trim();
            	g = g.trim();
            	String game = "@game";
            	if(g.equals(game)){
            		game();
            	}
            	else{
            		inputOutput.write(message);
            	}
			}
		}
		catch(InterruptedException e) {
			System.out.println("Chat is finished.");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(String message) throws IOException {
		DatagramSocket socket = this.client.getSocket();
		message = message + "\n";
		byte[] bytes = message.getBytes();
		DatagramPacket packet = new DatagramPacket(bytes, 0, bytes.length, client.getAddress());
		socket.send(packet);
	}

	public void game(){
		try{
			int UnknownNumber, UserNumber, TrysCount = 0;
    	    send("Hello. Let's play. Guess a number from 0 to 100");
    	    send("Input your number: ");
        	UnknownNumber = (int)Math.floor(Math.random() * 100);
        	System.out.println("My number " + UnknownNumber);
	        DatagramPacket packet = this.receive();
			if (this.client.getAddress() == null) {
				this.client.setAddress(packet.getSocketAddress());
			}
        	do {
				TrysCount++;
				String message =  new String (packet.getData(), "UTF-8");
       			String str = new String();
       			int j = 7;
            	char tmp = '\n';
            	this.clientName = new String();
            	while(message.charAt(j) != tmp){
            		this.clientName += message.charAt(j);
            		j++;
            	}
        		for(int i = j + 1; i < message.length(); i++) {
           			if(message.charAt(i) == 0)
           				break;
	           		str += message.charAt(i);
    	    	}
        		message = message.trim();
        		UserNumber = Integer.parseInt(str.trim());	
				if (UserNumber > UnknownNumber){
					send("My number is less.");
					send("Input your number: ");
					packet = this.receive();
				}
				else if (UserNumber < UnknownNumber){            	
					send("My number is greater.");
					send("Input your number: ");	
					packet = this.receive();
				} 		 		
				else 
					send("Congratulations! You guessed!");
        	} while (UserNumber != UnknownNumber);
        	send("The number of your attempts: " + TrysCount);
        }
        catch(NumberFormatException e) {
        	try{
				send("There is a game. You cannot write.");
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
    }
}