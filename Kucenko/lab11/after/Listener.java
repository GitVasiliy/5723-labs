import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

import java.io.IOException;

import java.lang.Thread;



public class Listener extends Thread {

	private Client client;

	private ConsoleManager console;
	private final int bufferSize = 1024;


	public Listener(Client client, ConsoleManager console) {
		this.client = client;

		this.console = console;
	}


	@Override
	public void interrupt() {
		super.interrupt();

		this.client.close();

		try {
			this.console.close();
		}
		catch(IOException exception) {}
	}


	private DatagramPacket receivePacket() throws IOException {
		DatagramSocket sourceSocket = this.client.getSourceSocket();

		byte[] data = new byte[this.bufferSize];
		DatagramPacket packet = new DatagramPacket(data, data.length);

		sourceSocket.receive(packet);

		return packet;
	}


	private void handlerCommands(String command, DatagramPacket packet) throws IOException {
		if(command.equals("@connect")) {
			this.client.setDestinationSocket(packet.getSocketAddress());	
			this.console.printSystemMessage(packet.getAddress() + " connected.", null);
		}
		else if(command.startsWith("@send")) {
			this.console.printSystemMessage("Enter the directory to save:", null);
			this.client.setInputPath(true);
		}
		else if(command.equals("@ready")) {
			InetAddress address = this.client.getDestinationAddress();
			int port = this.client.getDestinationPort();

			try(
				FileSender fileSender = new FileSender(address, port, this.client.getFile(), this.console); 
			) {
				fileSender.sendFile();
			}
			catch(IOException exception) {
				this.console.printSystemMessage("Error sending file.", exception);
			}
		}
	}	


	@Override
	public void run() {
		try {
			while(true) {
				DatagramPacket packet = receivePacket();
				String message = this.console.getMessage(packet);

				if(message.startsWith("@")) {
					handlerCommands(message, packet);
				}
				else {
					this.console.write(message);
				}

			}
		}
		catch(IOException exception) {
			if(!this.client.isClosed()) {
				this.console.printSystemMessage("Error in listener.", exception);
			}
		}
	}
	
}
