import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

class Client{
    private String host;
    private int port;
    
    private String name = "noName";
    private ArrayList <String> list = new ArrayList<>();

    Client(String host, int port){
        this.host = host;
        this.port = port;
    }

    void client() throws Exception {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        InetAddress addr = InetAddress.getByName(host);
        DatagramSocket ds = new DatagramSocket();
        System.out.println("Check");
        try{
            while(true){
                byte[] receiveData = new byte[256];
                DatagramPacket receivePack = new DatagramPacket(receiveData, receiveData.length);
                if (input.ready()){
                    String str = input.readLine();
                    if (str.startsWith("@name ")) name = str.substring(6);
                    else if (str.startsWith("@quit")) System.exit(-1);
                    else if (str.startsWith("@dump ")){
                        FileWriter writer = new FileWriter(str.substring(6), false);
                        for (String s : list) {
                            writer.write(s + '\n');
                            writer.flush();
                        }
                        writer.close();
                    }
                    else {
                        byte[] transmitData = (name + ": " + str).getBytes();
                        DatagramPacket pack = new DatagramPacket(transmitData, transmitData.length, addr, port);
                        ds.send(pack);
                        ds.receive(receivePack);
                        list.add(name + ": " + str);
                        String receive = new String(receivePack.getData());
                        // list.add(receive);
                        //System.out.println(receive);
                    }
                }
            }
        }catch(Exception e){
            e.getStackTrace();
        }
    }
}