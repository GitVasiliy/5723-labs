package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

//    cd C:\java\Lab12\out\production\Lab12
//    java client.Client

public class InputOutputForServer extends Thread {
    private String name;
    private Socket client;
    private HashMap<String, Socket> arrayOfSockets;

    public InputOutputForServer(Socket client, HashMap<String, Socket> arrayOfSockets){
        this.client = client;
        this.arrayOfSockets = arrayOfSockets;
    }

    public boolean command(String input) throws IOException {
        if(input.substring(0, 5).equals("@name")) {
            String newName = input.substring(6);
            arrayOfSockets.remove(name, client);

            Integer repeatCount = 0;
            name = newName;
            boolean repeatName = false;
            boolean allRrepeatNameNotFound = true;

            while(allRrepeatNameNotFound) {
                for (Map.Entry<String, Socket> tmp : arrayOfSockets.entrySet()) {
                    if (tmp.getKey().equals(name)) {
                        repeatName = true;
                        repeatCount++;
                        name = newName + repeatCount.toString();
                        break;
                    }
                    repeatName = false;// выполняем фор пока не будет так, что весь массив пройден, а совпадений не найдено
                }

                if(!repeatName){
                    if(repeatCount == 0){
                        name = newName;
                    }

                    else {
                        name = newName + repeatCount.toString();
                    }

                    arrayOfSockets.put(name, client);
                    allRrepeatNameNotFound = false;//всё, свободное имя подобрали, можно выходить с вайла
                }
            }
            return true;
        }

        else if(input.substring(0, 5).equals("@quit")){
            arrayOfSockets.remove(name, client);
            client.close();
            interrupt();
        }

        else if(input.substring(0, 9).equals("@senduser")){
            StringBuilder receiver = new StringBuilder();
            for(int i = 10; !input.substring(i, i+1).equals(" "); i++){ // читаем имя получателя
                receiver.append(input.charAt(i));
            }

            for (Map.Entry<String, Socket> tmp : arrayOfSockets.entrySet()) {
                String receiverName = tmp.getKey();

                if(receiverName.equals(receiver.toString())){
                    Socket curClient = tmp.getValue();
                    DataOutputStream toUser = new DataOutputStream(curClient.getOutputStream());
                    toUser.writeUTF("Private message from " + name + ": " + input.substring(10 + receiver.length()));
                    return true;
                }
            }
        }
        return false;
    }

    public void run(){
        try {
            DataInputStream fromUser = new DataInputStream(client.getInputStream());
            this.name = fromUser.readUTF();
            command("@name " + name);

            while(true){
                String message = fromUser.readUTF();
                boolean haveCommand = false;

                if(message.substring(0, 1).equals("@")){
                    this.command(message);
                    haveCommand = true;
                }

                if(haveCommand){
                    continue;
                }
                for (Map.Entry<String, Socket> tmp : arrayOfSockets.entrySet()) {
                    if(!tmp.getKey().equals(name) && !tmp.getValue().equals(client)){
                        Socket curClient = tmp.getValue();
                        DataOutputStream toUser = new DataOutputStream(curClient.getOutputStream());
                        toUser.writeUTF("from " + name + ": " + message);
                    }
                }
            }
        } catch (IOException e) {
        }
    }
}
