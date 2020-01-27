package chat;
import java.io.*;
import java.net.*;

public class Test
{
 	public static void main(String arg[]) throws Exception
 	{
		InetAddress adr = InetAddress.getLocalHost(); // берем данные моего компьютера
		String adrHost = adr.getHostAddress(); // IP моего компьютера
		System.out.println(adrHost + "\n" + adr.getHostName()); // имя моего компьютера
		//////////TEST////////////
		byte[] buf = "Hello".getBytes();
		byte[] b = "Good".getBytes();
		DatagramSocket s = new DatagramSocket(2009);
		DatagramPacket pr = new DatagramPacket(buf, buf.length);
		DatagramPacket ps = new DatagramPacket(buf, buf.length, adr, 2009);
		
		s.send(ps);
		s.receive(pr);
		String str = new String(pr.getData());
		System.out.println(str);
		s.close();
 	}
}