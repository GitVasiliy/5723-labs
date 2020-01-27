package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class OutputThread extends Thread {
    private Socket client;

    public OutputThread(Socket client) throws IOException {
        this.client = client;
        System.out.print("Input your name: ");
        String tmp = new Scanner(System.in).nextLine();
        new DataOutputStream(client.getOutputStream()).writeUTF(tmp);
    }

    public void run() {
        try {
            DataOutputStream toServer = new DataOutputStream(client.getOutputStream());
            InputThread input = new InputThread(client);
            input.start();
            while(true) {
                String tmp = new Scanner(System.in).nextLine();
                toServer.writeUTF(tmp);
                if(tmp.length() > 5 && tmp.substring(0, 5).equals("@quit")){
                    client.close();
                    input.interrupt();
                    break;
                }
            }
        } 
        catch (IOException e) {
        }
    }
}
