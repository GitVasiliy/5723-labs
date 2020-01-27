import java.io.*;
import java.io.InputStreamReader;
import java.net.Socket;

public class Output extends Thread {
    private Socket socket;
    public Output(Socket s) {
        socket = s;
    }
    public void run(){
        try {
            while (true){
                act();
            }

        }
        catch (Throwable t) {

        }

    }
    public void act() throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String fromServer;
        if((fromServer = in.readLine()) != null) {
            System.out.println("Server: " + fromServer);
            /*if (fromServer.equals("Bye.")) {
                System.exit(0);

            }*/
        }
        else {
            System.out.println("Server is closed");
        }

    }
}
