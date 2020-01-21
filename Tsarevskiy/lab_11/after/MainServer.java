import java.net.DatagramSocket;

import java.io.IOException;

import org.suai.server.Server;


public class MainServer {

	public static void main(String[] args) {
		if(args.length < 1) {
			System.out.println("SYSTEM: Incorrect argument.");
		}
		else {
			int port = 0;
			DatagramSocket socket = null;

			try {
				port = Integer.valueOf(args[0]);
				socket = new DatagramSocket(port);
			}
			catch(IOException | RuntimeException exception) {
				System.out.println("SYSTEM: Incorrect argument\n" + exception + ".");
				return;
			}

			Server server = new Server("Server", socket);
			server.messaging();
		}
	}

}
