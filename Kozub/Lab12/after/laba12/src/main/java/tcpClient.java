import java.io.*;
import java.net.*;
public class tcpClient {
    public static void main(String[] args) throws IOException {
        String hostName = "192.168.31.86";//args[0];
        int portNumber = 9876;//Integer.parseInt(args[1]);

        try {
            Socket kkSocket = new Socket(hostName, portNumber);
            BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
            String fromServer;
            Input input = new Input(kkSocket);
            input.start();
            while (true){
                if((fromServer = in.readLine()) != null) {
                    System.out.println(fromServer);
            /*if (fromServer.equals("Bye.")) {
                System.exit(0);

            }*/
                }
                else {
                    System.out.println("Server is closed");
                }
            }





        }
        catch (UnknownHostException e) {  }
        catch (IOException e) {  }
    }
}
