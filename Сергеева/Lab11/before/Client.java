

import java.net.*;
import java.io.IOException;

public class Client {
	private String name;
	private DatagramSocket socket;
	private SocketAddress address;
	private Sender sender;
	private Receiver receiver;

	public Client(String name, DatagramSocket socket, SocketAddress address) {
		this.name = name;
		this.socket = socket;
		this.address = address;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(SocketAddress address) {
		this.address = address;
	}

	public DatagramSocket getSocket() {
		return this.socket;
	}

	public SocketAddress getAddress() {
		return this.address;
	}

	public Sender getSender() {
		return this.sender;
	}

	public Receiver getReceiver() {
		return this.receiver;
	}

	public String getName() {
		return this.name;
	}

	public void close() throws IOException {
		socket.close();
	}

	public void messaging() {
		InputOutput inputOutput = new InputOutput();
		sender = new Sender(inputOutput, this);
		receiver = new Receiver(inputOutput, this);
		sender.start();
		receiver.start();
	}
}