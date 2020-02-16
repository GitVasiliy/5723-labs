import java.io.*;

public class Main {
    public static void main (String[] args) {
        String adress = args[0];
        try {

            udpClient client = new udpClient(adress);
            synchronized (client){/*System.out.println(*/client.start();/*)*/;}
            while (true) {
                System.out.println("1. Change name \n2. Send a messege \n3. Leave chat");
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