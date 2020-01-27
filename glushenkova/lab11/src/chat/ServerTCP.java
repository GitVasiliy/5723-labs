package chat;
import java.io.*;
import java.net.*;

public class ServerTCP // готово
{
	public static void main(String args[]) throws Exception
	{
		try
		{
			ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0], 10)); // создали сервер на этом ip 
			System.out.println("Server is waiting a connection...");
			Socket clientSocket = serverSocket.accept(); // поймали клиента
			ServerThreadTCP serverThreadTCP = new ServerThreadTCP(clientSocket);
			System.out.println("Client is connected. Client's host is " + clientSocket.getPort());
			serverThreadTCP.start();	

			PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true); 
			BufferedReader cmd = new BufferedReader(new InputStreamReader(System.in));
			String messageFromServer;
			while (true)
	        {
	        	System.out.print("Server's message: ");
	            messageFromServer = cmd.readLine();
	            if (messageFromServer != null)
	            {
	            	if (messageFromServer.startsWith("@quit")) 
            		{
	            		serverSocket.close();
	            		break;
                	}
                	else if (messageFromServer.startsWith("@send"))
	                {
	                    String filename = messageFromServer.substring(6);
	                    filename = filename.trim();
	                    File myFile = new File(filename);
	                    if (myFile.isFile() == false) throw new Exception("file txt not found");
	                    FileReader reader = new FileReader(filename);
	                    int c = 0;
	                    String fileres = "C:\\javalab\\lab11\\files\\outServer.txt";
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
	                    toClient.println(textFromFile);   
	                }
	            		else toClient.println(messageFromServer); // отправили на сервер сообщение // отправили клиенту сообщение
	            }
	        }
	    }
	    catch(Exception exp)
	    {

	    }

	}

	public static class ServerThreadTCP extends Thread
	{
		Socket clientSocket;
		BufferedReader inputFromClient;
		String clientName = "Client";

		public ServerThreadTCP(Socket clientSocket) throws Exception
		{
			this.clientSocket = clientSocket;
			inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // принимаем данные от клиента (сокета)
			
		}
		@Override
		public void run()
		{
			try
			{
				while (true)
				{
					String message = inputFromClient.readLine();
					if (message.startsWith("@quit")) clientSocket.close();
					if (message.startsWith("@name")) clientName = message.substring(6).trim();
					else System.out.println(clientName + ": " + message); // выводим данные от клиента;
					System.out.print("Server's message: ");
				}
				
			}
			catch(Exception exp)
			{

			}
			
		}
	}
}