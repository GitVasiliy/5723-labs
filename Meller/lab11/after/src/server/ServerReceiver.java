package server;

import java.io.IOException;
import java.net.*;
import names.Names;

public class ServerReceiver extends Thread{
    private DatagramSocket serverSocket;
    private Names names;

    public ServerReceiver(DatagramSocket serverSocket, Names names){
        this.serverSocket = serverSocket;
        this.names = names;
    }

    public void run(){
        while(!serverSocket.isClosed()) {
            byte[] receiveData = new byte[1024];

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                serverSocket.receive(receivePacket);
            } catch (IOException e) {
            }

            String message = new String(receivePacket.getData());
//            if(message.length() >= 5 && message.substring(0, 5).equals("@name")){
//                name = message.substring(6, message.length() - 1);
//            }
//            else if(message.length() >= 4 && message.substring(0, 4).equals("@cat")){
//                ServerSender sendFile = new ServerSender(IPAddress, port, serverSocket, tcpClientSocket);
//
//                try {
//                    sendFile.sendMessage(cat(message.substring(5, message.length() - 1).trim()));
//                    sendFile.sendFile(cat(message.substring(5, message.length() - 1).trim()));
//                } catch (IOException e) {
//                }
//            }

            if(message.length() >= 5 && message.substring(0, 5).equals("@name")){
                names.setClientName(message.substring(6));
            }
            else if(message.substring(0, 5).equals("@quit")){
                System.out.println("FROM " + names.getClientName() + ": " + message.substring(6).trim());
                interrupt();
            }
            else {
                System.out.println("FROM " + names.getClientName() + ": " + message.trim());
            }
        }
    }

//    private byte[] cat(String filePath) throws IOException {
//            FileInputStream in = new FileInputStream(filePath);
//            byte[] res = new byte[in.available()];
//            in.read(res);
//            return res;
//    }
}
