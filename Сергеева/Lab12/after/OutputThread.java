/*import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;*/
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
        System.out.print("Input your name to connect: ");
        name = new Scanner(System.in).nextLine();
        System.out.print("Input your password to connect: ");
        password = Integer.parseInt(new Scanner(System.in).nextLine());
        new DataOutputStream(client.getOutputStream()).writeUTF(name);
    }

    public void run() {
        try {
            BufferedReader r = new BufferedReader (new InputStreamReader(new FileInputStream("loginPassword.txt")));
            HashMap<String,Integer> loginPassword = new HashMap<>();
            String[] subStr;
            String tmp, delimeter = " ";
            while(r.ready()) {
                tmp = r.readLine();
                subStr = tmp.split(delimeter);
                loginPassword.put(subStr[0], Integer.parseInt(subStr[1]));
            }
            if (loginPassword.containsKey(name) && loginPassword.get(name) == password) {
                System.out.println("Correct name or password of user");
                DataOutputStream toServer = new DataOutputStream(client.getOutputStream());
                InputThread input = new InputThread(client, name);
                input.start();
                while(true) {
                    System.out.println("Input your message:  ");
                    toServer.writeUTF(name + ": " + new Scanner(System.in).nextLine());
                }
            } else {
                System.out.println("Incorrect name or password of user");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}