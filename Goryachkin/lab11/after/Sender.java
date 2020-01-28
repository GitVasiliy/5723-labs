import java.io.*;
import java.net.*;


public class Sender extends Thread {
	private InputOutput inputOutput;
	private Client client;
	private Receiver clientName;

	public Sender(InputOutput inputOutput, Client client, Receiver r) {
		this.inputOutput = inputOutput;
		this.client = client;
		this.clientName = r;

	}

	public void send(String message) throws IOException {
		DatagramSocket socket = this.client.getSocket();
		message = "\nFrom: " + this.client.getName() + "\n" + message + "\n";
		byte[] bytes = message.getBytes();
		DatagramPacket packet = new DatagramPacket(bytes, 0, bytes.length, client.getAddress());
		socket.send(packet);
	}

	public void checkCommand(String command) throws IOException {
		if (command.startsWith("@name") && command.charAt(5) == ' '){
			if(clientName.getClientName().equals(command.substring(6))){
				System.out.println("Wrong");
			}
			else{
				this.client.setName(command.substring(6));
			}
		}
		else if(command.startsWith("@quit") && command.length() == 5) {
			this.client.getReceiver().interrupt();
            interrupt();
		}
		else if(command.startsWith("@game") && command.length() == 5) {
			send("@game");
		}
		else
			System.out.println("Error! Wrong command");
	}



	public void run() {
		try {
			while(true) {
				String message = this.inputOutput.read();
				if(message.startsWith("@")) {
					this.checkCommand(message);
					if(isInterrupted())
						throw new InterruptedException();
				}
				else
					send(message);
			}
		}
		catch (InterruptedException e) {
			System.out.println("Chat is finished.");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}