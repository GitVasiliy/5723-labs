
import java.net.*;
import java.io.*;


public class ServerMain {
	public static void main(String[] args) throws IOException {
		if (args.length < 1)
			throw new RuntimeException ("Error! Too few arguments.");
		else if (args.length > 1)
			throw new RuntimeException ("Error! Too many arguments.");
		DatagramSocket socket = new DatagramSocket(Integer.valueOf(args[0]));
		Server server = new Server("Server", socket);
		server.messaging();
	}
}