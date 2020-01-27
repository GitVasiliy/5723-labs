package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(9999);
        byte[] receive = new byte[1024];
        DatagramPacket pack = new DatagramPacket(receive, receive.length);
//        System.out.println("1");
        socket.receive(pack);
//        System.out.println("2");
        String tmp = new String(pack.getData());
//        System.out.println("3");
        int port = Integer.parseInt(tmp.trim());
        socket.close();

        Socket client = new Socket("192.168.43.173", port);
        OutputThread out = new OutputThread(client);
        out.start();
    }
}
