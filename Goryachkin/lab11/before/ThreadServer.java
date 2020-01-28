import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ThreadServer extends Thread{
	private DatagramSocket socket;
	private InetAddress ip;
	private int port;

	public ThreadServer(DatagramSocket s, InetAddress ip, int port){
		socket = s;
		this.ip = ip;
		this.port = port;
	}

	public void run(){
		try{
			BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Input name: ");
			String name = r.readLine();
			while(true){
				byte[] b = new byte[1024];
		        String s = r.readLine();
		        String tmp = s;
		        s = "" + name + ": " + tmp;
		        if(s.length() > 256){
		        	System.out.println("Incorrect size");
		        }
		        else{
		        	b = s.getBytes();
			        DatagramPacket  dp = new DatagramPacket(b, b.length, ip, port);
			        socket.send(dp);
			        if(tmp.equals("@quit")){
			        	socket.close();
			        	return;
		        	}
		        }
	    	}
	    }
	    catch(Exception e){}
	}
}