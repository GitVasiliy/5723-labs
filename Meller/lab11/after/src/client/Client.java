package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args) throws IOException {
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress ipAddress = InetAddress.getByName("localhost");

        String connectMessage = "Set connection";
        DatagramPacket connectPack = new DatagramPacket(connectMessage.getBytes(), connectMessage.getBytes().length, ipAddress, 8080);
        clientSocket.send(connectPack);


        ClientSender clientSender = new ClientSender(ipAddress, clientSocket);
        clientSender.start();

        ClientReceiver clientReceiver = new ClientReceiver(clientSocket);
        clientReceiver.start();
    }
}
