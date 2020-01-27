import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.*;
public class udpServer extends Thread {

    private String myName = "Server";
    private String chatName = "Client";
    private byte[] receiveData;
    private byte[] sendData;
    private DatagramSocket socket;
    private InetAddress ipAdress;
    int port;
    private String rootDir = System.getProperty("user.dir");

    udpServer(int p) throws Exception {
        this.socket = new DatagramSocket(9876);
        this.port = p;
    }


    public void sendMessege() throws Exception {
        System.out.println("Your sentence");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        String form = (myName + ": " + str);
        this.sendData = form.getBytes();
        ipAdress = InetAddress.getByName("localhost");

        DatagramPacket packet = new DatagramPacket(sendData, sendData.length, ipAdress, port);
        socket.send(packet);
    }

    public void sendMessege(String name) throws Exception {
        this.myName = name;
        ipAdress = InetAddress.getByName("localhost");
        String form = new String(name);
        //System.out.println("Your opponent has changed name to "+ name);

        //System.out.println(this.myName.length());
        DatagramPacket packet = new DatagramPacket(form.getBytes(), form.length(), ipAdress, port);
        socket.send(packet);
    }

    public void run() {
        try {

            while (true) act();
        } catch (Throwable t) {
        }
    }

    public void act() throws Exception {
        receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
        this.port = receivePacket.getPort();
        ipAdress = InetAddress.getByName("localhost");
        String res = new String(receivePacket.getData());
        res = res.trim();
        //System.out.println(res);
        String tmp = res.substring(0, 3);
        //System.out.println(tmp);
        if (tmp.equals("@cd")) {
            if(res.length() == 3){
                sendData = System.getProperty("user.dir").getBytes();
                DatagramPacket otvet = new DatagramPacket(sendData, sendData.length, ipAdress, port);
                socket.send(otvet);
            }
            else {
                String save = System.getProperty("user.dir");
                String k = res.substring(4);
                //k = k.trim();

                System.out.println(k + " 2");
                System.setProperty("user.dir",save + "\\" + k);
                //System.out.println(save);
                File dir = new File(System.getProperty("user.dir"));
                //String answer = new String(save);
                String resAnswer = new String(System.getProperty("user.dir"));
                sendData = resAnswer.getBytes();




                if (!dir.isDirectory()) // РµСЃР»Рё РѕР±СЉРµРєС‚ РїСЂРµРґСЃС‚Р°РІР»СЏРµС‚ РєР°С‚Р°Р»РѕРі
                {
                    String answer1 = new String("Error: No directory");
                    System.setProperty("user.dir", save);
                    DatagramPacket otvet1 = new DatagramPacket(answer1.getBytes(), answer1.length(), ipAdress, port);
                    socket.send(otvet1);
                }
                else {
                    DatagramPacket otvet = new DatagramPacket(sendData, sendData.length, ipAdress, port);
                    socket.send(otvet);
                    System.out.println(tmp + " 1");
                    System.out.println(resAnswer);
                }
            }
        }
        else {
            if (res.equals("@pwd")) {

                    String answer = (System.getProperty("user.dir"));
                    sendData = answer.getBytes();
                    DatagramPacket otvet = new DatagramPacket(sendData, sendData.length, ipAdress, port);
                    socket.send(otvet);
                }

            }

            if (res.equals("@ls")) {
                File dir = new File(System.getProperty("user.dir"));
                StringBuffer str = new StringBuffer();
                if (dir.isDirectory()) // РµСЃР»Рё РѕР±СЉРµРєС‚ РїСЂРµРґСЃС‚Р°РІР»СЏРµС‚ РєР°С‚Р°Р»РѕРі
                {

                    for (File item : dir.listFiles()) {  // РїРѕР»СѓС‡Р°РµРј РІСЃРµ РІР»РѕР¶РµРЅРЅС‹Рµ РѕР±СЉРµРєС‚С‹ РІ РєР°С‚Р°Р»РѕРіРµ
                        if (item.isDirectory()) {
                            str.append(item.getName() + "  \t folder\n");
                        } else {
                            str.append(item.getName() + "\t file\n");
                        }
                    }
                    String answer = new String(str);
                    DatagramPacket otvet = new DatagramPacket(answer.getBytes(), answer.length(), ipAdress, port);
                    socket.send(otvet);
                }
            }



        System.out.println(res);


    }
}