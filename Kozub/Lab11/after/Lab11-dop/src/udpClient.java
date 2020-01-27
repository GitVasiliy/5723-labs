import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
public class udpClient extends Thread {

    private String chatName = "Server";
    private String myName = "Client";
    private byte[] receiveData;
    private byte[] sendData;
    private DatagramSocket socket;
    private String adress;

    udpClient(String ad) throws Exception{
        this.socket = new DatagramSocket();
        this.adress = ad;
    }



    public void sendMessege() throws  Exception{
        System.out.println("Your sentence");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        String form = (str);
        this.sendData = form.getBytes();
        DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getLocalHost(),9876);
        socket.send(packet);
    }

    public void sendMessege(String name) throws  Exception{
        this.myName = name;
        String form = (/*"Your opponent has changed name to "+*/ name);

        DatagramPacket packet = new DatagramPacket(form.getBytes(), form.length(),InetAddress.getLocalHost(),9876);
        socket.send(packet);
    }

    public void run() {
        try {

            while (true)act();
        }
        catch (Throwable t) {}
    }
    public void act() throws Exception {
        receiveData = new byte[1024];
        DatagramPacket  receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
        String res = new String(receivePacket.getData());

        System.out.println(res);
    }
}
