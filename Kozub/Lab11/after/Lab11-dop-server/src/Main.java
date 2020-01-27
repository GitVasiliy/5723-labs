import java.io.*;

public class Main {
    public static void main (String[] args) {
        Integer port = Integer.parseInt(args[0]);
        try {
            udpServer server = new udpServer(port);

             /*System.out.println(*/server.start()/*)*/;
            while (true){
                System.out.println("1. Change name \n2. Send a messege \n3. Leave chat");
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
                }
            }

        }
        catch (Throwable t) {}

    }
}