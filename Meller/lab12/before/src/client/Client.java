package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("192.168.43.173", 8081);
        OutputThread out = new OutputThread(client);
        out.start();
    }
}
