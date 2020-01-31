import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server{

	public static void main(String args[]) throws Exception{
		DatagramSocket socket = new DatagramSocket(8000);
		byte[] buffer = new byte[1024];
		DatagramPacket pack = new DatagramPacket(buffer, buffer.length);
		socket.receive(pack);
		int port = pack.getPort();
		InetAddress ip = pack.getAddress();
		ThreadServer ts = new ThreadServer(socket, ip, port);
		ts.start();
		ThreadClient tc = new ThreadClient(socket);
		tc.start();
	}
}