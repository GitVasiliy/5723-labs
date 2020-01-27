package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import names.Names;

public class ServerSender extends Thread {
    private String message;
    private InetAddress IPAddress;
    private int port;
    private DatagramSocket serverSocket;
    private Names names;

    public ServerSender(InetAddress IPAddress, int port, DatagramSocket serverSocket, Names names){
        this.IPAddress = IPAddress;
        this.port = port;
        this.serverSocket = serverSocket;
        this.names = names;
    }

    public void run(){

        while(!serverSocket.isClosed()){
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            try {
                message = inFromUser.readLine();
            } catch (IOException e) {
            }

            if(message.length() >= 5 && message.substring(0, 5).equals("@name")){
                this.names.setServerName(message.substring(6));
                message = message.substring(6).length() + message.substring(6) + message;
            }
            else if(message.length() >= 5 && message.substring(0, 5).equals("@quit")){
                String message = "Server has left us:((";
                DatagramPacket closePack = new DatagramPacket(message.getBytes(), message.getBytes().length, IPAddress, port);
                try {
                    serverSocket.send(closePack);
                } catch (IOException e) {
                }
                serverSocket.close();
            }

            else {
                message = names.getServerName().length() + names.getServerName() + message;
                sendMessage(message.getBytes());
            }
        }
    }

    public void sendMessage(byte[] message){
        DatagramPacket sendPacket = new DatagramPacket(message, message.length, IPAddress, port);
        try {
            serverSocket.send(sendPacket);
        } catch (IOException e) {
        }
    }
}
