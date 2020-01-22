package b;

import java.io.*;
import java.net.*;

public class Player
{
	
	public static void main(String args[]) throws Exception
	{
		InetAddress ip = InetAddress.getByName("127.0.0.1");
		try
		{
			Socket fromServer = new Socket(ip, Integer.parseInt("209", 10));          // подключились к серверу с ip -> читаем и отправляем информацию на этот сервер
			PrintWriter toServer = new PrintWriter(fromServer.getOutputStream(), true); // поток чтобы отправлять на сервер информацию
            BufferedReader cmd = new BufferedReader(new InputStreamReader(System.in));  // поток чтобы считывать из cmd
            String messageFromUser;

            PlayerThread t = new PlayerThread(fromServer);
            t.start();

            while (true)
            {
                messageFromUser = cmd.readLine();
                if (messageFromUser != null)
                {
                	toServer.println(messageFromUser); // отправляем
                }

            }

		}
		catch(Exception exp)
		{

		}

	}
	public static class PlayerThread extends Thread
	{
		BufferedReader inputFromServer;
		Socket fromServer;

		public PlayerThread(Socket fromServer) throws Exception
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
					
					if (message.equals("cls") == true) // очистка консули ?
					{
					}
					else System.out.println(message); // выводим на экран входящую информацию
				}
				catch(Exception exp)
				{

				}
			}
		}

	}
}