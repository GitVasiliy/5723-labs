package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP extends Thread {
    private Socket tcpClientSocket;
    private ServerSocket tcpServerSocket;

    public ServerTCP(ServerSocket tcpServerSocket){
        this.tcpServerSocket = tcpServerSocket;
    }

    //@cat C:\Users\sashu\OneDrive\Документы\12\test.txt

    public void run(){
        while(true){
            try{
                tcpClientSocket = tcpServerSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(tcpClientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(tcpClientSocket.getOutputStream());
                String message = in.readLine();
                if (message.length() >= 4 && message.substring(0, 4).equals("@cat")) {
                    out.write(cat(message.substring(5)));
                    out.flush();
                    out.close();
                }
            } catch(IOException e){
            }
        }
    }

    private String cat(String filePath) throws IOException {
        if(!(new File(filePath).isFile())) { return "File not found"; }
        FileInputStream in = new FileInputStream(filePath);
        byte[] res = new byte[in.available()];
        while(in.read() != -1) {
            in.read(res);
        }
        return new String(res);
    }
}
