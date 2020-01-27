import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class Server{
    private byte[] receiveData = new byte[256];

    void server() throws Exception {
        DatagramSocket ds = new DatagramSocket(256);
        System.out.println("Check");
        try{
            while(true){
                DatagramPacket receivePack = new DatagramPacket(receiveData, receiveData.length);
                ds.receive(receivePack);
                System.out.println(new String(receivePack.getData()));
                InetAddress addr = receivePack.getAddress();
                int port = receivePack.getPort();
                byte[] transmitData = "ok".getBytes();
                DatagramPacket transmitPack = new DatagramPacket(transmitData, transmitData.length, addr, port);
                ds.send(transmitPack);
            }
        }catch(Exception ex){
            System.err.println(ex);
        }
    }
}