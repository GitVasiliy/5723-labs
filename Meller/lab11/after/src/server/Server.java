package server;

import names.Names;
import java.io.IOException;
import java.net.*;


public class Server {
   private static int port;
   private static InetAddress IPAddress;
   private static DatagramSocket serverSocket;

    public static void main(String[] args) throws IOException {
        ServerSocket tcpServerSocket = new ServerSocket(8888);
        serverSocket = new DatagramSocket(8080);
        getConnection();

        Names names = new Names();

        ServerSender sender = new ServerSender(IPAddress, port, serverSocket, names);
        sender.start();

        ServerReceiver receiver = new ServerReceiver(serverSocket, names);
        receiver.start();

        ServerTCP tcpServer = new ServerTCP(tcpServerSocket);
        tcpServer.start();

        while(true){
            if(receiver.isInterrupted()){
                sender.interrupt();
                getConnection();
                sender = new ServerSender(IPAddress, port, serverSocket, names);
                sender.start();

                receiver = new ServerReceiver(serverSocket, names);
                receiver.start();
            }
        }
    }

    public static void getConnection() throws IOException {
       byte[] receiveData = new byte[1024];
       DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
       serverSocket.receive(receivePacket);

       String message = new String(receivePacket.getData());
       System.out.println("FROM Client: " + message.trim());

       IPAddress = receivePacket.getAddress();
       port = receivePacket.getPort();
    }
}
