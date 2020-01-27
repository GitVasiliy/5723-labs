package server;

import java.io.IOException;
import java.net.*;

public class BroadcastSend extends Thread {
    private InetAddress address;
    private DatagramSocket socket;

    public BroadcastSend(DatagramSocket socket) throws UnknownHostException {
        address = InetAddress.getByName("192.168.43.255");
        this.socket = socket;
    }
    public void run(){
        String port = "12332";
        while(true){
            DatagramPacket sendPacket = new DatagramPacket(port.getBytes(), port.getBytes().length, address, 9999);
            try {
                socket.send(sendPacket);
            } catch (IOException e) {
            }
//            System.out.println(port);
        }
    }
}
