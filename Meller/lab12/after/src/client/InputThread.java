package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class InputThread extends Thread {
    private Socket client;
    public InputThread(Socket client){
        this.client = client;
    }

    public void run() {
        try {
            DataInputStream toClientRec = new DataInputStream(client.getInputStream());
            while(true){
                String inString = toClientRec.readUTF();
                System.out.print(inString + "\n");
            }

        } catch (IOException e) {
        }

    }
}
