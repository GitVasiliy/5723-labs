package server;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8081);
        HashMap<String, Socket> arrayOfSockets = new HashMap<>();
        while(true) {
            try {

                Socket client = server.accept();
                System.out.println("connected");
                InputOutputForServer inOut = new InputOutputForServer(client, arrayOfSockets);
                inOut.start();
            } catch (IOException e) {
            }
        }
    }
}
