package com.udpClient;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.io.File;

public class ClientMain {
    public static void main (String[] args) {

        try {

            UdpClient client = new UdpClient();
            synchronized (client){client.start();}
            while (true) {
                System.out.println("1. Change name \n2. Send a messege \n3. Leave chat \n4.Enter command");
                BufferedReader userChoise = new BufferedReader(new InputStreamReader(System.in));
                String choise = userChoise.readLine();
                switch (choise) {
                    case ("1"):
                        System.out.println("Write your name ");
                        BufferedReader userName = new BufferedReader(new InputStreamReader(System.in));
                        String name = userName.readLine();
                        client.sendMessege(name);
                        break;
                    case ("2"):
                        client.sendMessege();
                        break;
                    case ("3"):
                        System.out.println("Bye");
                        System.exit(0);
                }
            }
        }

        catch (Throwable t) {}

    }
}