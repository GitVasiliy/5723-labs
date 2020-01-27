import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class InputThread extends Thread {
    private Socket client;
    private String name;
    public InputThread(Socket client, String name){
        this.client = client;
        this.name = name;
    }
    public void run() {
        try {
            DataInputStream toClient = new DataInputStream(client.getInputStream());
            while(true){
                String inString = toClient.readUTF();
                System.out.print(inString + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}