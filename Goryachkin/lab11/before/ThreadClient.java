import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ThreadClient extends Thread{
	private DatagramSocket socket;

	public ThreadClient(DatagramSocket s){
		socket = s;
	}

	public void run(){
		try{
			while(true){
				byte[] buffer = new byte[1024];
				DatagramPacket pack = new DatagramPacket(buffer, buffer.length);
				socket.receive(pack);
				byte[] data = pack.getData();
				String s = new String(data);
				System.out.println(s.trim());
			}
		}
		catch(Exception e){}
	}
}