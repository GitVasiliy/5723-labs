import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.*;

public class OutputThread extends Thread {
    private Socket client;
    private String name;
    private int password;

    public OutputThread(Socket client) throws IOException {
        this.client = client;
        System.out.print("Input your name: ");
        name = new Scanner(System.in).nextLine();
        new DataOutputStream(client.getOutputStream()).writeUTF(name);
    }

    public void run() {
        try {
            DataOutputStream toServer = new DataOutputStream(client.getOutputStream());
            InputThread input = new InputThread(client, name);
            input.start();
            while(true) {
                System.out.println("Input your message:  ");
                toServer.writeUTF(name + ": " + new Scanner(System.in).nextLine());
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}