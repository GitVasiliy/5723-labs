import java.io.*;
import java.net.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServerThread extends Thread{
	private DatagramSocket socket;
	private DatagramPacket client;
	private int port;
	private InetAddress ip;

	public ServerThread(int port, InetAddress ip, DatagramSocket d){
		socket = d;
		this.ip = ip;
		this.port = port;
	}

	public void run(){
		try{
			FileWriter w = new FileWriter("name.txt", false); 
			FileReader r = new FileReader("name.txt");
			BufferedReader in = null;
			try{
				in = new BufferedReader(new InputStreamReader(System.in));
			}
			catch(Exception e){
			 	System.out.println("Error: " + e);
			}
			Path currentRelativePath = Paths.get("");
        	String currKatalog = currentRelativePath.toAbsolutePath().toString();
			System.out.print("Your name: ");
			String name = in.readLine();
			char []name1 = new char[100];
			r.read(name1);
			String name2 = "";
			for (int i = 0; i < 100; i++){
				name2 += name1[i];
			}
			System.out.println(name2);
			if (name.charAt(1) == name2.charAt(1)){
				System.out.println("1");
				name += "fchgvj";
			}
			System.out.println(name);
			r.close();
			w.write(name);
			w.flush();
			while(true){
				byte[] sendMessage = new byte[1024];
				System.out.print("Your message: ");
				String str = in.readLine();
				String tmp = str;
				str = "" + name + ": " + tmp;
				sendMessage = str.getBytes();
				DatagramPacket client = new DatagramPacket(sendMessage, sendMessage.length, ip, port);
				socket.send(client);
				if (tmp.equals("@pwd")){
					int len = currKatalog.length();
                	if(currKatalog.charAt(len-1) != (char)92) {
                    	currKatalog += (char)92;
                	}
                System.out.println(currKatalog);
                continue;
				}
				if (tmp.equals("@quit")){
					socket.close();
					return;
				}
			}
		}
		catch(Exception e){
		}
	}
}