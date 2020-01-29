import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class Client{

	public static void main(String args[]) throws IOException{
		DatagramSocket sock = new DatagramSocket();
		byte[] b = new byte[1024];
		InetAddress ip = InetAddress.getByName("localhost");
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		DatagramPacket  dp = new DatagramPacket(b , b.length , ip , 8000);
	    sock.send(dp);
		ThreadClient tc = new ThreadClient(sock);
		tc.start();
		ThreadServer ts = new ThreadServer(sock, ip, 8000);
		ts.start();
	}
}