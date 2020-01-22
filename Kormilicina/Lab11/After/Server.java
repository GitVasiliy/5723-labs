import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Scanner;

class Server {
    private static byte[] receiveData = new byte[1024];
    private static byte[] sendData = new byte[1024];
    private static DatagramSocket serverSocket;
    private static boolean isExit = false;
    private static InetAddress clientIPAddress;
    private static int clientPort;
    private static String name = "";
    private static int port;
    private static final ArrayList<String> ChatHistory = new ArrayList<>();

    public static void main(String args[]) throws Exception {
        Scanner in = new Scanner (System.in);
        System.out.println("Input port: ");
        int port = in.nextInt();
        serverSocket = new DatagramSocket(port);
        WriterThread writerThread = new WriterThread();
        writerThread.start();
        while (!isExit) {
            try {
                while (true) {
                    receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    serverSocket.receive(receivePacket);
                    String message = new String(receivePacket.getData());
                    for(int i = 0; i < message.length(); ++i) {
                        char tmp = message.charAt(i);
                        if((int)tmp == 0) {
                            message = message.substring(0, i);
                            break;
                        }
                    }
                    int begin = 0;
                    for (int i = 0; i < message.length(); ++i) {
                        if(message.charAt(i) == ':' && message.charAt(i + 1) == ' ') {
                            begin = i + 2;
                            break;
                        }
                    }
                    if (message.charAt(begin) == '@') {
                        String command = message.substring(begin + 1, begin + 5);
                        if (command.equals("exit")) {
                            System.out.println("Client disconnected.");
                            break;
                        }
                        if(command.equals("conn")) {
                            clientIPAddress = receivePacket.getAddress();
                            clientPort = receivePacket.getPort();
                            System.out.println("Client connected.");
                            continue;
                        }
                    }
                    synchronized (ChatHistory) {
                        ChatHistory.add(message);
                    }
                    System.out.println(message);


                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static class WriterThread extends Thread {
        public void run()  {
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            DatagramPacket sendPacket;
            try {
                while (!isInterrupted()) {
                    sendData = new byte[1024];
                    String message = inFromUser.readLine();
                    if(message.length() == 0) {
                        continue;
                    }
                    if (message.charAt(0) == '@') {
                        String command = message.substring(1, 5);
                        if (command.equals("exit")) {
                            isExit = true;
                            System.exit(0);
                            break;
                        }
                        if(command.equals("name")) {                                      //меняем имя
                            String tmp = message.substring(5, message.length());
                            sendData = ("User " + name + " change the name to " + tmp).getBytes();
                            synchronized (ChatHistory) {
                                ChatHistory.add("User " + name + " change the name to " + tmp);
                            }
                            name = tmp;
                            sendPacket = new DatagramPacket(sendData, sendData.length, clientIPAddress, clientPort);
                            serverSocket.send(sendPacket);
                            continue;
                        }
                        if(command.equals("dump")) {                                        // сохраняем историю чата
                            String filename = message.substring(6, message.length());
                            PrintWriter writer = new PrintWriter(filename);
                            synchronized (ChatHistory) {
                                for (String m : ChatHistory) {
                                    writer.println(m);
                                }
                            }
                            writer.close();
                            continue;
                        }
                    }
                    sendData = (name + ": " + message).getBytes();
                    synchronized (ChatHistory) {
                        ChatHistory.add(name + ": " + message);
                    }
                    sendPacket = new DatagramPacket(sendData, sendData.length, clientIPAddress, clientPort);
                    serverSocket.send(sendPacket);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}