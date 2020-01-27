import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class InputOutputForServer extends Thread {
    private String name;
    private Socket client;
    private  HashMap<String,Socket> arrayOfSockets;
    public InputOutputForServer(Socket client, HashMap<String,Socket> a){
        this.client = client;
        this.arrayOfSockets = a;
    }

    public boolean command(String in) throws IOException {
        char[] mas = in.toCharArray();
        int i = 0;
        for(i = 0; mas[i] != '@'; i++);
        StringBuilder sb = new StringBuilder();
        for(;mas[i] != ' '; i++){
            sb.append(mas[i]);
            if(i+1 == in.length())break;
        }

        i++;
        System.out.println(sb.toString());
            if(sb.toString().equals("@senduser")){
                sb.delete(0,sb.length());
            for(;mas[i] !=' '; i++){
                sb.append(mas[i]);
                if(i+1 == in.length())break;
            }
            i++;
            if(i < in.length()){
                String receiver = sb.toString();
                sb.delete(0,sb.length());
                for(;i<in.length(); i++){
                    sb.append(mas[i]);
                }
                String massage = sb.toString();
                for(i = 0; mas[i] != ':';i++)
                    sb.append(mas[i]);
                String sender = sb.toString();
                for (Map.Entry<String, Socket> tmp : arrayOfSockets.entrySet()) {
                    String key = tmp.getKey();
                    if(key.equals(receiver)){
                        Socket curClient = tmp.getValue();
                    DataOutputStream toUser = new DataOutputStream(curClient.getOutputStream());
                    toUser.writeUTF("Private message from "+name+": "+massage);
                    return true;
                }
                }
            }
        }
        return false;
    }



    public void run(){
        try {
            DataInputStream fromUser = new DataInputStream(client.getInputStream());
            this.name = fromUser.readUTF();
            arrayOfSockets.put(name,client);
            while(true){
                String line = fromUser.readUTF();
                char[] str = line.toCharArray();
                boolean haveCommand = false;
                int i;
                for(i = 0; i<line.length(); i++){
                    if(str[i] == '@'){
                        haveCommand = true;
                    }
                    if(haveCommand)break;
                }
                if(haveCommand){
                    if(this.command(line))continue;
                }
                char[] lettersOfName = new char[i+1];
                for(int j = 0; j< i; j++){
                    lettersOfName[j] = str[j];
                }
                System.out.println(line);
                for (Map.Entry<String, Socket> tmp : arrayOfSockets.entrySet()) {
                    String key = tmp.getKey();
                    if(!key.equals(name)){
                        Socket curClient = tmp.getValue();
                        DataOutputStream toUser = new DataOutputStream(curClient.getOutputStream());
                        toUser.writeUTF(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}