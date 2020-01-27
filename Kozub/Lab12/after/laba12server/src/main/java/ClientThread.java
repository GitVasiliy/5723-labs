import java.io.*;
import java.net.*;
import java.util.*;


public class ClientThread extends Thread {
    private Socket socket;
    public Users u;


    ClientThread(Socket s, Users user){
        socket = s;
        u = user;
    }

    public void run() {
        try {
            act();
        }
        catch (Throwable t) {

        }

    }

    public void act() throws Exception {
        //boolean personalMessage = false;
        PrintWriter[] outs;
        HashMap<String, Socket> users;
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String inputLine, outputLine;
        KnockKnockProtocol kkp = new KnockKnockProtocol();
        outputLine = kkp.processInput();

        out.println(outputLine);
        while ((inputLine = in.readLine()) != null) {
            users = u.getUsers();
            outs = new PrintWriter[users.size()];
            Iterator it = users.entrySet().iterator();
            outputLine = kkp.processInput(inputLine);
            int mode = kkp.analyse(inputLine);
            /*switch (mode){
                case (0):
                    personalMessage = false;
                    break;
                case (1):
                    personalMessage = true;
                    break;
                case (2):
                    break;// add changeName method
            }*/
            if(mode == 1) {
                String sendName = kkp.argOfFunc(mode, inputLine);
                for(int i = 0; it.hasNext()&& i < users.size(); i++) {
                    Map.Entry mapElement = (Map.Entry)it.next();
                    if(sendName.equals(mapElement.getKey())){
                        Socket tmp = (Socket) mapElement.getValue();
                        PrintWriter pers = new PrintWriter(tmp.getOutputStream(), true);
                        pers.println(u.getName(socket) + " (PERSONAL) " + kkp.argOfFunc(3, outputLine));
                    }
                }
            }
            if(mode == 0) {
                for(int i = 0; it.hasNext()&& i < users.size(); i++) {
                    Map.Entry mapElement = (Map.Entry)it.next();
                    Socket tmp = (Socket) mapElement.getValue();

                    outs[i] = new PrintWriter(tmp.getOutputStream(), true);
                    outs[i].println(u.getName(socket) + ": " +outputLine);
                    //out.println(tmp);
                    //System.out.println(tmp);
                }
            }
            if(mode == 2){
                String curName = u.getName(socket);
                String newName = kkp.argOfFunc(mode, inputLine);
                u.setName(socket, newName);
                System.out.println(curName + " has changed his name to "+ newName);

            }



            System.out.println(u.getName(socket) + ": " + outputLine);

            //out.println(inputLine);
            if (outputLine.equals("Bye."))
                break;
        }

    }



}
