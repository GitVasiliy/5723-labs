import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
public class udpServer extends Thread {

    private String myName = "Server";
    private String chatName = "Client";
    private byte[] receiveData;
    private byte[] sendData;
    private DatagramSocket socket;
    private InetAddress ipAdress;
    int port;

    udpServer(int p) throws Exception{
        this.socket = new DatagramSocket(9876);
        this.port = p;
    }

    public String getMessege() throws Exception {

        DatagramPacket  receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
        this.port = receivePacket.getPort();
        String res = new String(receivePacket.getData());
        System.out.println(res);
        return res;
    }

    public void sendMessege() throws  Exception{
        System.out.println("Your sentence");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        String form = (myName +": " + str);
        this.sendData = form.getBytes();
        ipAdress = InetAddress.getByName("localhost");

        DatagramPacket packet = new DatagramPacket(sendData, sendData.length, ipAdress,port);
        socket.send(packet);
    }
    public void sendMessege(String name) throws  Exception{
        this.myName = name;
        ipAdress = InetAddress.getByName("localhost");
        String form = new String("Your opponent has changed name to "+ name);
        //System.out.println("Your opponent has changed name to "+ name);

        //System.out.println(this.myName.length());
        DatagramPacket packet = new DatagramPacket(form.getBytes(), form.length(), ipAdress,port);
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
        this.port = receivePacket.getPort();
        String res = new String(receivePacket.getData());

            System.out.println(res);


    }
}
