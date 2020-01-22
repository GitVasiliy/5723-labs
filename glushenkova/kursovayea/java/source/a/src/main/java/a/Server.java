package a;

import java.io.*;
import java.net.*;
import java.util.*;
//import model.*;
// ход назад
// javaFX
// чат с картинками смайликами и их фразами 

public class Server
{
	static ArrayList<WorkWithPlayerThread> players = new ArrayList<WorkWithPlayerThread>(); // добавить массив с игроками в поиски
	static ArrayList<Board> boards = new ArrayList<Board>();
	static TreeMap<Integer, String> raiting = new TreeMap<Integer, String>();
	static HashMap<String, String> users = new HashMap<String, String>();
	static HashSet<String> onlineUsers = new HashSet<String>();
	public static void main(String args[]) throws Exception 
	{
		try
		{
			openAccounts();
			openRaiting();
		}
		catch(Exception exp)
		{}

		ServerSocket server = new ServerSocket(Integer.parseInt("209", 10)); // создали сервер на ip
		AcceptThread acceptingThread = new AcceptThread(server);
		acceptingThread.start(); // поток который всегда слушает и ловит клиентов
		System.out.println("Server is waiting a connection...");

		/*Thread.currentThread().join();
		System.out.println("qwe");*/
	}

	public static void saveAccounts() throws Exception
	{
		synchronized(users)
		{
			File myFile = new File("users.txt");
	        FileWriter writer = new FileWriter("users.txt", false);
	        if (myFile.isFile() == false) throw new Exception("file txt not found");
	        for (Map.Entry it : users.entrySet())
	    	{
	    		String res = "";
	    		res = it.getKey() + ";" + it.getValue() + "\n";
	    		writer.write(res);

	    	}
	        writer.close();
	    }
	}
	public static void saveRaiting() throws Exception
	{
		synchronized(raiting)
		{
			File myFile = new File("raiting.txt");
	        FileWriter writer = new FileWriter("raiting.txt", false);
	        if (myFile.isFile() == false) throw new Exception("file txt not found");
	        for (Map.Entry it : raiting.entrySet())
	    	{
	    		String res = "";
	    		System.out.println(it.getValue()+ "+" + it.getKey().toString());
	    		res = it.getValue() + ";" + it.getKey().toString() + "\n";
	    		writer.write(res);

	    	}
	        writer.close();
	    }
	}
	public static void openAccounts() throws Exception
	{
		BufferedReader in = new BufferedReader(new FileReader("users.txt"));
        String str;
        String[] info;
        try
        {
            while((str = in.readLine()) != null)
            {
                info = str.split(";");
                users.put(info[0], info[1]);
            }
        }
        catch(Exception e)
        {
            
        }
        in.close();
	}
	public static void openRaiting() throws Exception
	{
		BufferedReader in = new BufferedReader(new FileReader("raiting.txt"));
        String str;
        String[] info;
        try
        {
            while((str = in.readLine()) != null)
            {
                info = str.split(";");
                //info[0]=name - Value ; info[1]=points - Key
                raiting.put(Integer.parseInt(info[1], 10), info[0]);
            }
        }
        catch(Exception e)
        {
            
        }
        in.close();
	}

	public static void deleteDeadThread()
	{
		synchronized(players)
		{
			for (int i = 0; i < players.size(); i++)
			{
				if (players.get(i).isAlive() == false)
				{
					players.remove(i);
					onlineUsers.remove(players.get(i).playerName);
				}
			}
		}
	}
	public static class AcceptThread extends Thread // только для прослушки --- готов
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
					saveAccounts();
					saveRaiting();

					Socket newPlayer = server.accept(); // поймали нового игрока
					WorkWithPlayerThread playerThread = new WorkWithPlayerThread(newPlayer);
					synchronized (players)
					{
						players.add(playerThread); // добавляем поток с игроком в массив
					}
					playerThread.start(); // запустили поток, который работает с игроком
				}
				catch(Exception exp)
				{
					System.out.println(exp);
				}
			}
		}
	}
	public static void launchBoard(Board b)
	{
		b.start();
	}
	public static boolean checkUser(String login, String pass)
	{
		synchronized(users)
		{
			for (Map.Entry it : users.entrySet())
	    	{
	    		System.out.println(it.getKey()+ "+" + it.getValue());
	    		if (it.getKey().equals(login) == true && it.getValue().equals(pass) == true 
	    			&& !onlineUsers.contains(login))
	    		{
	    			onlineUsers.add(login);
	    			return true;
	    		}
	    	}
		}

		return false;
	}
	public static boolean checkFormatLInfo(String info)
	{
		for (int i = 0; i < info.length(); i++)
		{
			if (info.charAt(i) < 48 && info.charAt(i) > 57 && info.charAt(i) < 65 && info.charAt(i) > 90
				&& info.charAt(i) < 97 && info.charAt(i) > 122)
				return false;
		}
		return true;
	}
	public static boolean isNewLogin(String login)
	{
		synchronized(users)
		{
			for (Map.Entry it : users.entrySet())
			{
				if (it.getKey().equals(login) == true)
					return false;
			}
		}
		return true;
	}
	public static int registr(WorkWithPlayerThread p)
	{
		try
		{
			p.toThisPlayer.println("Enter login and password (format (only english alphabet and numbers): \nlogin password");
			boolean format = false;
			while (!format)
			{
				String message = p.inputFromPlayer.readLine();
				if (message.equals("quit") == true) return -1;
				String[] info = message.split(" ");
				if (info.length == 2)
				{
					String login = info[0];
					String pass = info[1];
					if (checkFormatLInfo(login))
					{
						if (isNewLogin(login))
						{
							format = true;
							synchronized(users) 
							{
								users.put(login, pass);
								//onlineUsers.add(login);
								saveAccounts();
							}
							p.playerName = login;
						}
					}
				}
			}
		}
		catch(Exception exp)
		{

		}
		return 1;
	}
	public static void deleteFullBoards()
		{
			synchronized (boards)
			{
				for (int i = 0; i < boards.size(); i++)
				{
					if (boards.get(i).full)
					{
						boards.remove(i);
						i--;
					}
				}
			}
		}
	public static int findPoints(String login)
	{
		synchronized (raiting)
		{
			for (Map.Entry it : raiting.entrySet())
	    	{
	    		if (it.getValue().equals(login) == true) return (int)it.getKey();
	    	}
		}
    	return 0;
	}
	public static void addPoints(WorkWithPlayerThread p, int point)
	{
		synchronized (raiting)
		{
			for (Map.Entry it : raiting.entrySet())
	    	{
	    		if (it.getValue().equals(p.playerName) == true)
	    		{
	    			int sum = (int)it.getKey() + point;
	    			raiting.put(sum, p.playerName);
	    			System.out.println(p.playerName + " " + sum);
	    		}
	    	}
		}
	}
	public static class WorkWithPlayerThread extends Thread 
	// поток, который работает с отдельным игроком
	{
		//для игры
		String playerName;
		Socket socketPlayer;
		BufferedReader inputFromPlayer; // считывает инф от игрока
		PrintWriter toThisPlayer; // сервер отправит этому же игроку

		boolean turn = false;
		boolean inGame = false;


		WorkWithPlayerThread(Socket s) throws Exception
		{
			this.socketPlayer = s;
			System.out.println("New Player is connected " + socketPlayer.getPort());
            toThisPlayer = new PrintWriter(socketPlayer.getOutputStream(), true);
            inputFromPlayer = new BufferedReader(new InputStreamReader(socketPlayer.getInputStream()));
		}
		public void run()
		{//поменять логику принятия сообщения, пока не quit 17.12.19
			try
			{
				System.out.println("EXIT-|");
				String[] info;
				boolean enter = false;
				boolean aut = false;
				while (!enter)
				{
					System.out.println("ENTER-|");
					boolean ans = false;
					//boolean aut = true;
					while (!ans)
					{
						System.out.println("ANS-|");
						toThisPlayer.println("Do you have an account? (y/n)");
						String message = inputFromPlayer.readLine();
						if (message.equals("n") == true)
						{
							ans = true;
							int flag = registr(this);
							if (flag == -1) socketPlayer.close();
						}
						if (message.equals("y") == true)
						{
							ans = true;
						}
					}
					enter = false;
					while (!enter)
					{
						System.out.println("AUT-|");
						toThisPlayer.println("Enter your login and password\n9 - Go back");
						String message = inputFromPlayer.readLine();
						if (message.equals("9") == true) break;
						if (message.contains(" ") == true)
						{
							info = message.split(" ");
							if (checkUser(info[0], info[1]) && info.length == 2)
							{
								System.out.println("AUT");
								enter = true;
								playerName = info[0];
							}
						}
					}
				}
				//if (exit) break;
				boolean ans = false;
				int points = findPoints(playerName);
				toThisPlayer.println("Hello, " + playerName + "!\nYour have " + points +  " points");
				boolean exit = false;
				while (!exit)
				{
					System.out.println("ING-|");
					toThisPlayer.println("1 - Create a new Board");
					toThisPlayer.println("2 - Join to existing Board");
					toThisPlayer.println("3 - Exit");
					String message = inputFromPlayer.readLine();
					if (message.equals("3") == true) exit = true;
					if (message.equals("1") == true) 
					{
						System.out.println("YES 1");
						Board b = new Board();
						b.addFirstPlayer(this);
						inGame = true;
						ans = true;
					}
					if (message.equals("2") == true) 
					{
						System.out.println("YES 2");
						Board b = joinBoard();
						//deleteFullBoards();
						inGame = true;
						if (!b.isAlive()) b.start();
						ans = true;
					}
					int t = 0;
					while (inGame)
					{
						//System.out.println("GAAAAME" + playerName);
						t++;
					}
					//saveRaiting();
					toThisPlayer.println("Do you want to play again ?");
					toThisPlayer.println("1 - Yes");
					toThisPlayer.println("2 - No (Exit)");
					message = inputFromPlayer.readLine();
					/*if (message.equals("1") == true) 
					{
						
					}*/
					if (message.equals("2") == true) 
					{
						exit = true;
					}
					//while (!b.full){}


				}
			}
			catch(Exception exp)
			{

			}
		}
		public void waitingGame(Board b)
		{

		}
		public Board joinBoard()
		{
			boolean join = false;
			int i = 0;
			while (!join)
			{
				for (i = 0; i < boards.size(); i++)
				{
					if (!boards.get(i).full) 
					{
						join = true;
						boards.get(i).addSecondPlayer(this);
					}
					if (join) break;
				}
			}
			return boards.get(i);
		}
	}

	public static class Board extends Thread
	{
		public int[][] board;
		WorkWithPlayerThread player1;
		WorkWithPlayerThread player2;
		boolean full = false;
		boolean isEndGame = false;

		Board()
		{
			board = new int[10][10];
			System.out.println("Board is created!");
		}
		public boolean getEndGame()
		{
			return isEndGame;
		}
		public void run()
		{
			player1.inGame = true;
			player2.inGame = true;
			try
			{
				int numMove = 0;
				while (!isEndGame)
				{
					boolean turn1 = true;
					boolean turn2 = true;
					player1.toThisPlayer.println("Server: glhf");
					player2.toThisPlayer.println("Server: glhf");
					while (turn1)
					{
						boolean format = false;
						String[] coord = new String[2];
						while (!format)
						{
							player1.toThisPlayer.println("Server: Enter correct coord");
							if (player1.socketPlayer.isClosed())
							{
								full = false;
								boards.add(this);
								while (!full){}
							}
							String message = player1.inputFromPlayer.readLine();
							if (message.contains(" ") == true) 
							{
								coord = message.split(" ");
								int x = Integer.parseInt(coord[0], 10);
								int y = Integer.parseInt(coord[1], 10);
								if (x >= 1 && x <= 10 && y >= 1 && y <= 10
									&& board[x - 1][y - 1] == 0 && coord.length == 2)
								{
									board[x - 1][y - 1] = 1;
									format = true;
								}
							}
						}
						turn1 = false;
					}
					numMove++;
					player1.toThisPlayer.println(player1.playerName + ": move " + numMove);
					player2.toThisPlayer.println(player1.playerName + ": move " + numMove);
					printBoard();
					int w = checkWinner();
					//System.out.println(w);
					if (w == 1)
					{
						player1.toThisPlayer.println("You win!");
						player2.toThisPlayer.println("You lose.");
						isEndGame = true;
						addPoints(player1, 3);
						addPoints(player2, 1);
						player1.inGame = false;
						player2.inGame = false;
						if (isEndGame) System.out.println("1THE END");
					}
					if (w == 2)
					{
						player2.toThisPlayer.println("You win!");
						player1.toThisPlayer.println("You lose.");
						isEndGame = true;
						addPoints(player1, 1);
						addPoints(player2, 3);
						player1.inGame = false;
						player2.inGame = false;
						if (isEndGame) System.out.println("2THE END");
					}
					if (isEndGame) break;
					while (turn2)
					{
						boolean format = false;
						String[] coord = new String[2];
						while (!format)
						{
							player2.toThisPlayer.println("Server: Enter correct coord");
							if (player2.socketPlayer.isClosed())
							{
								full = false;
								boards.add(this);
								while (!full){}
							}
							String message = player2.inputFromPlayer.readLine();
							if (message.contains(" ") == true) 
							{
								coord = message.split(" ");
								int x = Integer.parseInt(coord[0], 10);
								int y = Integer.parseInt(coord[1], 10);
								if (x >= 1 && x <= 10 && y >= 1 && y <= 10
									&& board[x - 1][y - 1] == 0 && coord.length == 2)
								{
									board[x - 1][y - 1] = 2;
									format = true;
								}
							}
						}
						turn2 = false;
					}
					numMove++;
					player1.toThisPlayer.println(player2.playerName + ": move " + numMove);
					player2.toThisPlayer.println(player2.playerName + ": move " + numMove);
					printBoard();
					w = checkWinner();
					//System.out.println(w);
					if (w == 1)
					{
						player1.toThisPlayer.println("You win!");
						player2.toThisPlayer.println("You lose.");
						addPoints(player1, 3);
						addPoints(player2, 1);
						player1.inGame = false;
						player2.inGame = false;
						isEndGame = true;
					}
					if (w == 2)
					{
						player2.toThisPlayer.println("You win!");
						player1.toThisPlayer.println("You lose.");
						addPoints(player1, 1);
						addPoints(player2, 3);
						player1.inGame = false;
						player2.inGame = false;
						isEndGame = true;
					}
					if (numMove == 100)
					{
						player2.toThisPlayer.println("Draw");
						player1.toThisPlayer.println("Draw");
						addPoints(player1, 2);
						addPoints(player2, 2);
						player1.inGame = false;
						player2.inGame = false;
						isEndGame = true;
					}
				}
				//saveRaiting();
				System.out.println("THE END.............");				
			}
			catch(Exception exp)
			{

			}
		}
		public void printBoard()
		{
			player1.toThisPlayer.println("----------");
			player2.toThisPlayer.println("----------");
			for (int i = 0; i < 10; i++)
			{
				player1.toThisPlayer.print("| ");
				player2.toThisPlayer.print("| ");
				for (int j = 0; j < 10; j++)
				{
					if (board[i][j] == 0) player1.toThisPlayer.print(" ");
					if (board[i][j] == 1) player1.toThisPlayer.print("x");
					if (board[i][j] == 2) player1.toThisPlayer.print("o");
					if (board[i][j] == 0) player2.toThisPlayer.print(" ");
					if (board[i][j] == 1) player2.toThisPlayer.print("x");
					if (board[i][j] == 2) player2.toThisPlayer.print("o");
				}
				player1.toThisPlayer.print("|\n");
				player2.toThisPlayer.print("|\n");
			}
			player1.toThisPlayer.println("----------");
			player2.toThisPlayer.println("----------");
		}
		public int checkWinner()
		{
			int len = 0;
			for (int i = 0; i < 10; i++)
			{
				for (int j = 0; j < 10; j++)
				{
					int sign = board[i][j];
					len = 0;
					if (sign != 0)
					{
						//1
						if (i - 4 >= 0)
						{
							System.out.println("YES 1 " + i + " " + j);
							for (int k = 1; k < 5; k++)
							{
								if (board[i - k][j] == sign) len++;
							}
							if (len == 4) return sign;
						}
						len = 0;
						//2
						if (i - 4 >= 0 && j + 4 <= 9)
						{
							for (int k = 1; k < 5; k++)
							{
								if (board[i - k][j + k] == sign) len++;
							}
							if (len == 4) return sign;
						}
						len = 0;
						//3 *
						if (j + 4 <= 9)
						{
							for (int k = 1; k < 5; k++)
							{
								if (board[i][j + k] == sign) len++;
							}
							if (len == 4) return sign;
						}
						len = 0;
						//4 *
						if (i + 4 <= 9 && j + 4 <= 9)
						{
							for (int k = 1; k < 5; k++)
							{
								if (board[i + k][j + k] == sign) len++;
							}
							if (len == 4) return sign;
						}
						len = 0;
						//5
						if (i + 4 <= 9)
						{
							for (int k = 1; k < 5; k++)
							{
								if (board[i + k][j] == sign) len++;
							}
							if (len == 4) return sign;
						}
						len = 0;
						//6
						if (i + 4 <= 9 && j - 4 >= 0)
						{
							for (int k = 1; k < 5; k++)
							{
								if (board[i + k][j - k] == sign) len++;
							}
							if (len == 4) return sign;
						}
						len = 0;
						//7
						if (j - 4 >= 0)
						{
							for (int k = 1; k < 5; k++)
							{
								if (board[i][j - k] == sign) len++;
							}
							if (len == 4) return sign;
						}
						len = 0;
						//8
						if (i - 4 >= 0 && j - 4 >= 0)
						{
							for (int k = 1; k < 5; k++)
							{
								if (board[i - k][j - k] == sign) len++;
							}
							if (len == 4) return sign;
						}
					}
				}
			}
			return 0;
		}
		public int checkWinnerForTest(int[][] b)
		{
			board = b;
			int len = 0;
			for (int i = 0; i < 10; i++)
			{
				for (int j = 0; j < 10; j++)
				{
					int sign = board[i][j];
					len = 0;
					if (sign != 0)
					{
						//1
						if (i - 4 >= 0)
						{
							for (int k = 1; k < 5; k++)
							{
								if (board[i - k][j] == sign) len++;
							}
							if (len == 4 ) return sign;
						}
						len = 0;
						//2
						if (i - 4 >= 0 && j + 4 <= 9)
						{
							for (int k = 1; k < 5; k++)
							{
								if (board[i - k][j + k] == sign) len++;
							}
							if (len == 4 ) return sign;
						}
						len = 0;
						//3 *
						if (j + 4 <= 9)
						{
							for (int k = 1; k < 5; k++)
							{
								if (board[i][j + k] == sign) len++;
							}
							if (len == 4) return sign;
						}
						len = 0;
						//4 *
						if (i + 4 <= 9 && j + 4 <= 9)
						{
							for (int k = 1; k < 5; k++)
							{
								if (board[i + k][j + k] == sign) len++;
							}
							if (len == 4) return sign;
						}
						len = 0;
						//5
						if (i + 4 <= 9)
						{
							for (int k = 1; k < 5; k++)
							{
								if (board[i + k][j] == sign) len++;
							}
							if (len == 4) return sign;
						}
						len = 0;
						//6
						if (i + 4 <= 9 && j - 4 >= 0)
						{
							for (int k = 1; k < 5; k++)
							{
								if (board[i + k][j - k] == sign) len++;
							}
							if (len == 4) return sign;
						}
						len = 0;
						//7
						if (j - 4 >= 0)
						{
							for (int k = 1; k < 5; k++)
							{
								if (board[i][j - k] == sign) len++;
							}
							if (len == 4) return sign;
						}
						len = 0;
						//8
						if (i - 4 >= 0 && j - 4 >= 0)
						{
							for (int k = 1; k < 5; k++)
							{
								if (board[i - k][j - k] == sign) len++;
							}
							if (len == 4) return sign;
						}
					}
				}
			}
			return 0;
		}
		public boolean checkMoveForTest(int[][] b, int x, int y)
		{
			board = b;
			if (x > 9 || y > 9) return false;
			if (board[x][y] != 0) return false;
			return true;
		}
		public void addFirstPlayer(WorkWithPlayerThread p)
		{
			player1 = p;
			System.out.println("Player 1");
			boards.add(this);
		}
		public void addSecondPlayer(WorkWithPlayerThread p)
		{
			player2 = p;
			full = true;
			System.out.println("Player 2");
		}

	}


}