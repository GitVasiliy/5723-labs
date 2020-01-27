import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket(args[0], Integer.parseInt(args[1]));
        OutputThread out = new OutputThread(client);
        out.start();
    }
}