package chat;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server
{
	static ArrayList<ClientThread> clients = new ArrayList<ClientThread>(); 

	public static void main(String args[]) throws Exception 
	{
		ServerSocket server = new ServerSocket(Integer.parseInt(args[0], 10)); // создали сервер на ip
		AcceptThread acceptingThread = new AcceptThread(server);
		acceptingThread.start(); // поток который всегда слушает и ловит клиентов
		System.out.println("Server is waiting a connection...");

	}
	private static void deleteDeadThread()
	{
		for (int i = 0; i < clients.size(); i++)
		{
			if (clients.get(i).isAlive() == false)
			{
				clients.remove(i);
			}
		}
	}
	private static class AcceptThread extends Thread // только для прослушки
	{
		ServerSocket server;
		public AcceptThread(ServerSocket s)
		{
			this.server = s;
		}
		@Override
		public void run()
		{
			while (true)
			{
				try
				{
					Socket newClient = server.accept(); // поймали нового клиента
					ClientThread clientThread = new ClientThread(newClient);
					synchronized (clients)
					{
						clients.add(clientThread); // добавляем поток с клиентом в массив
					}
					clientThread.start(); // запустили поток, который работает с клиентом
				}
				catch(Exception exp)
				{

				}
			}
		}
	}

	private static class ClientThread extends Thread
	{
		Socket clientSocket;
		String clientName = "Client";
		BufferedReader inputFromClient; // считывает инф от клиента
		PrintWriter toThisClient; // сервер отправит этому же клиенту
							  	  // для @senduser нужно пройтись по клиентПотокам и найти нужного

		ClientThread(Socket s) throws Exception
		{
			this.clientSocket = s;
			System.out.println("New client is connected " + clientSocket.getPort());
            toThisClient = new PrintWriter(clientSocket.getOutputStream(), true);
            inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		}

		public void run()
		{
			try
			{
				while (true)
				{
					Server.deleteDeadThread();
					String message = inputFromClient.readLine();
					if (message.startsWith("@quit")) clientSocket.close();
					else if (message.startsWith("@name")) // работает
						{
							clientName = message.substring(6).trim();
							toThisClient.println("Server: Name is successful");
						}
						else if (message.startsWith("@senduser"))
							{
								System.out.println("size: " + clients.size());
								message = message.substring(10).trim();
								String sender = "";
								int i = 0;
								while (message.charAt(i) != ' ')
								{
									sender += message.charAt(i);
									i++;
								}
								message = message.substring(sender.length() + 1).trim();
								for (i = 0; i < clients.size(); i++)
								{
									String curClient = clients.get(i).getClientName();
									System.out.println("current Client:" + curClient);
									if (curClient.equals(sender) == true && clients.get(i).isAlive() == true)
									{
										PrintWriter outSender = new PrintWriter(clients.get(i).getSocket().getOutputStream(), true);
										message = "Message from " + this.clientName + ":" + message;
										outSender.println(/*"Message from " + this.clientName + ":" + */message);
										break;
									}
								}
							}
							else 
							{
								for (int i = 0; i < clients.size(); i++)
								{
									Socket curClient = clients.get(i).getSocket();
									if (clientSocket != curClient)
									{
										PrintWriter outOtherClient = new PrintWriter(curClient.getOutputStream(), true);
										message = "Message from " + this.clientName + ":" + message;
										outOtherClient.println(/*"Message from " + this.clientName + ":" + */message);
									}
								}
							}
				}
				
			}
			catch(Exception exp)
			{

			}
		}
		private Socket getSocket()
		{
			return this.clientSocket;
		}
		private String getClientName()
		{
			return this.clientName;
		}
	}
}