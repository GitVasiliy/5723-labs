package chat;
import java.io.*;
import java.net.*;

public class ClientTCP // готово
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

            ClientThreadTCP t = new ClientThreadTCP(fromServer);
            t.start();

            while (true)
            {
            	System.out.print("My message: ");
                messageFromUser = cmd.readLine();
                if (messageFromUser != null)
                {
                	if (messageFromUser.startsWith("@quit"))
                	{
                		fromServer.close();
                		break;
                	}
                	else if (messageFromUser.startsWith("@send"))
	                {
	                    String filename = messageFromUser.substring(6);
	                    filename = filename.trim();
	                    File myFile = new File(filename);
	                    if (myFile.isFile() == false) throw new Exception("file txt not found");
	                    FileReader reader = new FileReader(filename);
	                    int c = 0;
	                    String fileres = "C:\\javalab\\lab11\\files\\outClient.txt";
	                    File outFile = new File(fileres);
	                    FileWriter out = new FileWriter(fileres, false);
	                    if (outFile.isFile() == false) throw new Exception("file txt not found");
	                    String textFromFile = "";
	                    while ((c = reader.read()) != -1)
	                    {
	                        textFromFile = textFromFile + (char)c;
	                    } 
	                    out.write(textFromFile);
	                    out.close();    
	                    toServer.println(textFromFile);   

	                }
                		else if (messageFromUser.startsWith("@name")) toServer.println(messageFromUser);
                			else toServer.println(messageFromUser); // отправили на сервер сообщение

                }

            }

		}
		catch(Exception exp)
		{

		}

	}
	public static class ClientThreadTCP extends Thread
	{
		BufferedReader inputFromServer;
		Socket fromServer;

		public ClientThreadTCP(Socket fromServer) throws Exception
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
					System.out.println("Message from Server: " + message); // выводим на экран входящую информацию
					System.out.print("My message: ");
				}
				catch(Exception exp)
				{

				}
			}
		}
	}
}