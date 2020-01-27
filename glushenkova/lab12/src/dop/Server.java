package dop;
import java.io.*;
import java.net.*;
import java.util.*;

// Написать эхо-сервер с прикольными ответами ==== осталось ЭХО

public class Server
{
	static ArrayList<ClientThread> clients = new ArrayList<ClientThread>(); 
	static ArrayList<String> jokes = new ArrayList<String>();
	static ArrayList<String> citation = new ArrayList<String>();

	public static void main(String args[]) throws Exception 
	{
		ServerSocket server = new ServerSocket(Integer.parseInt(args[0], 10)); // создали сервер на ip
		Server.openJokes("C:\\javalab\\lab12\\src\\dop\\files\\jokes.txt");
		Server.openCitation("C:\\javalab\\lab12\\src\\dop\\files\\citation.txt");
		AcceptThread acceptingThread = new AcceptThread(server);
		acceptingThread.start(); // поток который всегда слушает и ловит клиентов
		System.out.println("Server is waiting a connection...");
	}

	private static void openJokes(String fileName)
	{
       try
       { 
       		Scanner reader = new Scanner(new File(fileName));
       		String line = "";
       	    while (reader.hasNext())
       	    {
       	       	jokes.add(reader.nextLine());
       		}
       		//System.out.println(jokes);
       	    reader.close();
       	}
       	catch (Exception exp)
       	{

       	}
	}
	private static void openCitation(String fileName)
	{
       	try
        {
        	Scanner reader = new Scanner(new File(fileName));
    		String line = "";
    	    while (reader.hasNext())
    	    {
    	       	citation.add(reader.nextLine());
    		}
    		//System.out.println(citation);
    	    reader.close();
        }
        catch (Exception exp)
        {

        }
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
					try
					{
						if (TimePhrase.isWorks == false) new TimePhrase("C:\\javalab\\lab12\\src\\dop\\files\\phrases.txt").start();
						TimePhrase.isWorks = true;
					}
					catch(Exception exp)
					{
								
					}
				}
				catch(Exception exp)
				{
					System.out.println(exp);
				}
			}
		}
	}
	public static class TimePhrase extends Thread // готово
	{
		ArrayList<String> phrases;
		static boolean isWorks = false;
		TimePhrase(String fileName) throws Exception
		{
			this.phrases = new ArrayList<String>();
			Scanner reader = new Scanner(new File(fileName));
			String line = "";
		    while (reader.hasNext())
		    {
		       	phrases.add(reader.nextLine());
			}
		    reader.close();
		}

		@Override
		public void run()
		{
			try
			{
				while (true)
				{
					int rand = (int)(Math.random() * phrases.size());
					for (int i = 0; i < clients.size(); i++)
					{
						Socket curClient = clients.get(i).getSocket();
						PrintWriter outOtherClient = new PrintWriter(curClient.getOutputStream(), true);
						outOtherClient.println(new Date().toString() + " Message from Server: " + phrases.get(rand));
						
					}
					Thread.currentThread().sleep(60000);	
				}
			}
			catch (Exception exp)
			{

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

		private void messageToBot(String message)
		{
			if (message.startsWith("@bot Hello!")) toThisClient.println("Message from Server: " + "Hello, My Friend!");
			if (message.startsWith("@bot joke")) toThisClient.println("Message from Server: " + Server.jokes.get((int)(Math.random() * Server.jokes.size())));
			if (message.startsWith("@bot citation")) toThisClient.println("Message from Server: " + Server.citation.get((int)(Math.random() * Server.citation.size())));
		}

		public void run()
		{
			try
			{
				while (true)
				{
					Server.deleteDeadThread();
					String message = inputFromClient.readLine();
					if (message.startsWith("@bot")) this.messageToBot(message);
					else if (message.startsWith("@quit")) clientSocket.close();
						else if (message.startsWith("@name")) // работает
							{
								clientName = message.substring(6).trim();
								toThisClient.println("\nServer: Name is successful");
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