package com.udpServer;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class UdpServer extends Thread {

    private String myName = "Server";
    private DatagramSocket socket;
    private int port;
    private static StringBuilder logging = new StringBuilder();

    UdpServer(int p) throws Exception{
        this.socket = new DatagramSocket(9876);
        this.port = p;
    }

    public void sendMessege() throws  Exception{
        System.out.println("Your sentence");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        logging.append(myName +": " + str +"\n" /*System.currentTimeMillis()*/);
        String form = (myName +": " + str);
        byte[] sendData = form.getBytes();
        InetAddress ipAdress = InetAddress.getByName("localhost");

        DatagramPacket packet = new DatagramPacket(sendData, sendData.length, ipAdress,port);
        socket.send(packet);
    }
    public void sendMessege(String name) throws  Exception{
        this.myName = name;
        InetAddress ipAdress = InetAddress.getByName("localhost");
        String form = new String("Your opponent has changed name to "+ name);
        logging.append("Your opponent has changed name to "+ name +"\n" /*System.currentTimeMillis()*/);
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
        byte[] receiveData = new byte[1024];
        DatagramPacket  receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
        this.port = receivePacket.getPort();
        String res = new String(receivePacket.getData());
        logging.append(res+"\n");
        System.out.println(res);
    }

    public void makeLog(String fileName)
    {
        try(FileWriter writer = new FileWriter(fileName+".txt", false))
        {
            writer.write(logging.toString());
            writer.flush();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}