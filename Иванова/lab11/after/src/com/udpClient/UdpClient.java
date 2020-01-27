package com.udpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
public class UdpClient extends Thread {

    private String myName = "Client";
    private DatagramSocket socket;

    UdpClient() throws IOException {
        this.socket = new DatagramSocket();

        String s = "In chat " + myName;
        DatagramPacket packet = new DatagramPacket(s.getBytes(), s.length(),InetAddress.getLocalHost(),9876);
        socket.send(packet);
    }

    public void sendMessege() throws  Exception{
        System.out.print("Your message: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        String form = (myName +": " + str);
        byte[] sendData = form.getBytes();
        DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getLocalHost(),9876);
        socket.send(packet);
    }

    public void sendMessege(String name) throws  Exception{
        this.myName = name;
        String form = ("Your opponent has changed name to "+ name);

        DatagramPacket packet = new DatagramPacket(form.getBytes(), form.length(),InetAddress.getLocalHost(),9876);
        socket.send(packet);
    }

    public void run() {
        try {

            while (true)
                act();
        }
        catch (Throwable t) {}
    }
    public void act() throws Exception {
        byte[] receiveData = new byte[1024];
        DatagramPacket  receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
        String res = new String(receivePacket.getData());
        System.out.println(res);
    }
}
