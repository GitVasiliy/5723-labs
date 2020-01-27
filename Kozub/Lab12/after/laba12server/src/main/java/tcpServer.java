
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class tcpServer {

    public static void main(String[] args) throws IOException {

            int portNumber = 9876;//Integer.parseInt(args[0]);
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Users users = new Users();

            while (true) {
                try {

                    Socket clientSocket = serverSocket.accept();
                    users.add("Client"+ users.getNumOfUsers(), clientSocket);
                    ClientThread clientThread = new ClientThread(clientSocket, users);
                    clientThread.start();

                } catch (Exception e) {
                }
            }
        }
}

