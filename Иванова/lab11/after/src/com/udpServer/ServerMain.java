package com.udpServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ServerMain {
    public static void main(String[] args) {
        Integer port = Integer.parseInt(args[0]);
        try {
            System.out.println(args[0]);
            UdpServer server = new UdpServer(port);

            synchronized (server){
                server.start();
            }

            while (true){
                System.out.println("1. Change name \n2. Send a messege \n3. Leave chat \n4. Make log file");
                BufferedReader userChoise = new BufferedReader(new InputStreamReader(System.in));
                String choise = userChoise.readLine();
                switch(choise) {
                    case ("1"):
                        System.out.println("Write your name ");
                        BufferedReader userName = new BufferedReader(new InputStreamReader(System.in));
                        String name = userName.readLine();
                        server.sendMessege(name);
                        break;
                    case ("2"):
                        server.sendMessege();
                        break;
                    case("3"):
                        System.out.println("Bye");
                        System.exit(0);
                        break;
                    case("4"):
                        System.out.println("Write file name ");
                        BufferedReader fileName = new BufferedReader(new InputStreamReader(System.in));
                        String named = fileName.readLine();
                        server.makeLog("named");
                        break;


                }
            }

        }
        catch (Exception t) {
            System.out.println(t.getStackTrace());
        }
    }
}
