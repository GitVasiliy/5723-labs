package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientReceiver extends Thread {
    private DatagramSocket clientSocket;

    public ClientReceiver(DatagramSocket clientSocket){
        this.clientSocket = clientSocket;
    }

    public void run(){
        while (!clientSocket.isClosed()){
            byte[] receiveData = new byte[1024];

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                clientSocket.receive(receivePacket);
            } catch (IOException e){
            }

            String message = new String(receivePacket.getData());

            try {
                int nameLen = Integer.parseInt(message.substring(0, 1));
                System.out.println("FROM " + message.substring(1, nameLen + 1) + ": " + message.substring(nameLen + 1));
            }
            catch (NumberFormatException e){}
        }
    }
}
