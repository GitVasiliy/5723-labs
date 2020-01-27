import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Scanner;


public class Client {
    private static byte[] sendData = new byte[1024];
    private static byte[] receiveData = new byte[1024];
    private static DatagramSocket clientSocket;
    private static final ArrayList<String> ChatHistory = new ArrayList<>();


    public static void main(String []args) throws IOException {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        InetAddress ipAddress = InetAddress.getByName("localhost");
        Scanner in = new Scanner (System.in);
        System.out.println("Input port: ");
        int port = in.nextInt();
        String name = " ";
        clientSocket = new DatagramSocket();
        ReaderThread readerThread = new ReaderThread();
        readerThread.start();
        boolean isExit = false;
        sendData = ("@conn").getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
        sendPacket.setAddress(ipAddress);
        clientSocket.send(sendPacket);
        while(true) {
            sendData = new byte[1024];
            String message = inFromUser.readLine();
            if(message.length() == 0) {
                continue;
            }
            if (message.charAt(0) == '@') {
                String command = message.substring(1, 5);
                if (command.equals("exit")) {
                    isExit = true;
                }
                if(command.equals("name")) {
                    String tmp = message.substring(5);
                    sendData = ("User " + name + " change the name to " + tmp).getBytes();
                    synchronized (ChatHistory) {
                        ChatHistory.add("User " + name + " change the name to " + tmp);
                    }
                    name = tmp;
                    sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
                    clientSocket.send(sendPacket);
                    continue;
                }
            }
            sendData = (name + ": " + message).getBytes();
            synchronized (ChatHistory) {
                ChatHistory.add(name + ": " + message);
            }
            sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
            clientSocket.send(sendPacket);
            if(isExit) {
                ReaderThread.interrupted();
                System.exit(0);
                break;
            }
        }
    }

    public static class ReaderThread extends Thread {
        public void run()  {
            try {
                while (!isInterrupted()) {
                    receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    clientSocket.receive(receivePacket);
                    String message = new String(receivePacket.getData());
                    for(int i = 0; i < message.length(); ++i) {
                        char tmp = message.charAt(i);
                        if((int)tmp == 0) {
                            message = message.substring(0, i);
                            break;
                        }
                    }
                    synchronized (ChatHistory) {
                        ChatHistory.add(message);
                    }
                    System.out.println(message);
                }
                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

    }
	
}
