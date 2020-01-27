import java.io.BufferedReader;
import java.io.InputStreamReader;

public class main {
    public static void main(String[] args) throws Exception {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome!");
        System.out.println("1 - name (@name Vasya)");
        System.out.println("2 -  (@quit)");
        System.out.print("input ip: ");
        String str = input.readLine();
        System.out.print("input port: ");
        int port = Integer.parseInt(input.readLine());
        Client client = new Client(str, port);
        client.client();
    }
}