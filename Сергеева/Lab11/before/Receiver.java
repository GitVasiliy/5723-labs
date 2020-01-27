

import java.io.IOException;
import java.net.*;


public class Receiver extends Thread {
	private InputOutput inputOutput;
	public static Client client;

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
				if (message.startsWith("@check")) {
					if (message.substring("@check".length() + 1).equals(this.client.getName())) {
						this.client.getSender().send("Wrong name");
					}
				}
				else {
	            	String str = new String();
	            	for(int i = 0; i < message.length(); i++) {
	            		if(message.charAt(i) == 0)
	            			break;
	            		str += message.charAt(i);
	            	}
	            	message = message.trim();
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
}