package chat;
import java.io.*;
import java.net.*;

public class Client
{
	public static void main(String args[]) throws Exception
	{
		InetAddress ip = InetAddress.getByName("127.0.0.1");
		try
		{
			Socket fromServer = new Socket(ip, Integer.parseInt(args[0], 10));          // подключились к серверу с ip -> читаем и отправляем информацию на этот сервер
			PrintWriter toServer = new PrintWriter(fromServer.getOutputStream(), true); // поток чтобы отправлять на сервер информацию
            BufferedReader cmd = new BufferedReader(new InputStreamReader(System.in));  // поток чтобы считывать из cmd
            String messageFromUser;

            ClientThread t = new ClientThread(fromServer);
            t.start();

            while (true)
            {
            	System.out.print("My message: ");
                messageFromUser = cmd.readLine();
                if (messageFromUser != null)
                {
                	toServer.println(messageFromUser);
                }

            }

		}
		catch(Exception exp)
		{

		}

	}
	public static class ClientThread extends Thread
	{
		BufferedReader inputFromServer;
		Socket fromServer;

		public ClientThread(Socket fromServer) throws Exception
		{
			this.fromServer = fromServer;
			inputFromServer = new BufferedReader(new InputStreamReader(fromServer.getInputStream())); // принимаем входящую информацию от сервера
		}
		@Override
		public void run()
		{
			while (true)
			{
				try
				{
					String message = new String(inputFromServer.readLine());
					if (message.startsWith("@quit")) fromServer.close();
					System.out.println(/*"Message from Server: " + */message); // выводим на экран входящую информацию
					System.out.print("My message: ");
				}
				catch(Exception exp)
				{

				}
			}
		}

	}
}