import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketAddress;

import java.io.File;
import java.io.IOException;

import java.lang.Thread;



public class Sender extends Thread {

	private Client client;

	private ConsoleManager console;

	private FileListener fileListener = null;


	public Sender(Client client, ConsoleManager console) {
		this.client = client;

		this.console = console;
	}


	private void fileReceive(String path) {
		File file = new File(path);

		try {
			file.createNewFile();
		}
		catch(IOException exception) {
			this.console.printSystemMessage("Incorrect path to file.", exception);
			return;
		}

		int port = this.client.getPort();
					
		try {
			this.fileListener = new FileListener(port, file, this.console);
			this.fileListener.start();
			send("@ready");
		}
		catch(IOException exception) {
			this.console.printSystemMessage("Error receiving file.", exception);
		}

		this.client.setInputPath(false);
	}


	public void send(String message) throws IOException {
		if(this.client.getDestinationSocket() != null) {
			DatagramSocket sourceSocket = this.client.getSourceSocket();
			SocketAddress destinationSocket = this.client.getDestinationSocket();

			byte[] data = message.getBytes();
			DatagramPacket packet = new DatagramPacket(data, data.length, destinationSocket);

			sourceSocket.send(packet);
		}
		else {
			this.console.printSystemMessage("No connections.", null);
		}
	}


	public void sendMessage(String message) throws IOException {
		message = this.client.getName() + ": " + message;
		send(message);

		this.console.write(message);
	}


	private void handlerCommands(String command) throws IOException {
		if(command.startsWith("@connect")) {
			if(command.length() == 8) {
				send(command);
			}
			else {
				this.console.printSystemMessage("Incorrect command. Command must be \"@connect\".", null);
			}
		}
		else if(command.startsWith("@send")) {
			if((command.length() > 6) && (command.charAt(5) == ' ')) {
				File file = new File(command.substring(6));

				if(file.exists()) {
					this.client.setFile(file);
					send("@send");
				}
				else {
					this.console.printSystemMessage("File not exists.", null);
				}
			}
			else {
				this.console.printSystemMessage("Incorrect command. Must be \"@send filename\".", null);
			}
		}
		else if(command.startsWith("@name")) {
			if((command.length() > 6) && (command.charAt(5) == ' ')) {
				String name = command.substring(6);
				if(!name.startsWith("SYSTEM")) {
					this.client.setName(name);
				}
				else {
					this.console.printSystemMessage("Name cant be starts with \"SYSTEM\".", null);
				}
			}
			else {
				this.console.printSystemMessage("Incorrect command. Command must be \"@name Vasya\". "
					+ "Length of the name cant be zero.", null);
			}
		}
		else if(command.startsWith("@help")) {
			if(command.length() == 5) {
				this.console.writeListCommands();
			}
			else {
				this.console.printSystemMessage("Incorrect command. Command must be \"@help\".", null);
			}
		}
		else if(command.startsWith("@cancel")) {
			if((command.length() > 8) && (command.charAt(7) == ' ')) {
				String cancel = command.substring(8);

				if(cancel.equals("send")) {
					if(this.fileListener != null) {
						this.fileListener.interrupt();
					}
				}
				else {
					this.console.printSystemMessage("Unknown command to cancel", null);
				}
			}
			else {
				this.console.printSystemMessage("Incorrect command. Command must be \"@cancel command\".", null);
			}
		}
		else if(command.startsWith("@quit")) {
			if(command.length() == 5) {
				if(this.fileListener != null) {
					this.fileListener.interrupt();
				}

				this.client.getListener().interrupt();
				interrupt();
			}
			else {
				this.console.printSystemMessage("Incorrect command. Command must be \"@quit\".", null);
			}
		}
		else {
			this.console.printSystemMessage("Unknown command.", null);
		}
	}


	@Override
	public void run() {
		try {
			handlerCommands("@help");
			
			while(true) {
				String message = this.console.read();
				message = message.trim();

				if(this.client.isInputPath()) {
					fileReceive(message);
				}
				else if(message.startsWith("@")) {
					handlerCommands(message);
				}
				else {
					if(this.client.getDestinationSocket() != null) {
						sendMessage(message);
					}
					else {
						this.console.printSystemMessage("No connections.", null);
					}
				}
			}
		}
		catch(IOException exception) {
			if(!this.client.isClosed()) {
				this.console.printSystemMessage("Error in sender.", exception);
			}
		}
	}

}
