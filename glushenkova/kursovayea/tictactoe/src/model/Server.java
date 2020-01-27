package model;
import java.io.*;
import java.net.ServerSocket;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Server
{
    private static ArrayList<WorkWithPlayerThread> players = new ArrayList<WorkWithPlayerThread>();
    private static TreeMap<Integer, String> rating = new TreeMap<Integer, String>();
    private static HashMap<String, String> users = new HashMap<String, String>();

    public static void main(String args[]) throws Exception
    {
        ServerSocket server = new ServerSocket(Integer.parseInt(args[0], 10)); // создали сервер на ip
        AcceptThread acceptingThread = new AcceptThread(server);
        acceptingThread.start(); // поток который всегда слушает и ловит клиентов
        System.out.println("Server is waiting a connection...");

    }
    private static void deleteDeadThread()
    {
       for (int i = 0; i < players.size(); i++)
        {
            if (players.get(i).isAlive() == false)
            {
                players.remove(i);
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
                for (int i = 0; i < players.size(); i++)
                {
                   System.out.println("check + " + i);
                }
                try
                {
                    Socket newPlayer = server.accept(); // поймали нового игрока
                    WorkWithPlayerThread playTh = new WorkWithPlayerThread(newPlayer);
                    playTh.start();
                    synchronized (players)
                    {
                        players.add(playTh); // добавляем поток с игроком в массив +
                        System.out.println("check   size: " + players.size());
                    }
                }
                catch(Exception exp)
                {
                    System.out.println(exp);
                }
            }
        }
    }

    static ArrayList<Board> boards = new ArrayList<Board>();

    public static class WorkWithPlayerThread extends Thread // поток, который работает с отдельным игроком и хранит модель, принимает сообщения от игрока
                                                            // повторяет логику как в смд версии, только если принял
    {
        String playerName;
        Socket socketPlayer;
        BufferedReader inputFromPlayer; // считывает инф от игрока
        PrintWriter toThisPlayer; // сервер отправит этому же игроку
        WorkWithPlayerThread opponent;
        boolean inGame;

        WorkWithPlayerThread(Socket s) throws Exception {
            this.socketPlayer = s;
            System.out.println("New Player is connected + " + socketPlayer.getPort());
            toThisPlayer = new PrintWriter(socketPlayer.getOutputStream(), true);
            inputFromPlayer = new BufferedReader(new InputStreamReader(socketPlayer.getInputStream()));
            inGame = false;
        }
        @Override
        public void run()
        {
            try
            {
                //boolean exit = false;
                while (true) //!exit     ????
                {
                    //Server.deleteDeadThread();
                    String message = inputFromPlayer.readLine();
                    System.out.println(message);
                    if (message.startsWith("@create"))
                    {
                        System.out.println("i have got a message @CREATE");
                      //  toThisPlayer.println("waiting your opponent (@create)");
                        Board b = new Board();
                       // synchronized (boards)
                        //{boards.add(b);}
                        boards.add(b);
                        b.addFirstPlayer(this);
                        inGame = true;
                    }
                    if (message.startsWith("@join"))
                    {
                        System.out.println("i have got a message @JOIN");
                        //toThisPlayer.println("your game will start soon (@join)");
                        Board b = joinBoard();
                        b.addSecondPlayer(this);

                        System.out.println("ii out");
                        b.player1.toThisPlayer.println("your game starts");
                        b.player2.toThisPlayer.println("your game starts");
                        inGame = true;
                        /*if (!b.isAlive())*/ b.start();
                    }
                    int t = 0;
                   /* while (inGame)
                    { t++; } // потянуть время))))*/
                    //exit = true; //изменить на предоожелние продолжить игру


                }

            }
            catch(Exception e)
            {System.out.println(e);}
        }
        public Board joinBoard()
        {
           // synchronized ((boards)) {
                boolean join = false;
                int i = 0;
                while (!join) {
                    for (i = 0; i < boards.size(); i++) {
                        if (!boards.get(i).full) {
                            join = true;
                            boards.get(i).addSecondPlayer(this);
                        }
                        if (join) break;
                    }
                }

                //toThisPlayer.println("board is found\nGAME STARTS");
                return boards.get(i);
           // }
        }
    }
    public static class Board extends Thread {
        int[][] board;

        WorkWithPlayerThread player1; //есть сокеты, сервер отправляет ходы и контрллер ставит
        WorkWithPlayerThread player2;

        boolean full = false;
        boolean isEndGame = false;

        Board() {
            board = new int[10][10];
            System.out.println("Board is created!");
        }
        @Override
        public void run()
        {
            //player1.toThisPlayer.println("GAME STARTS");
            //player2.toThisPlayer.println("GAME STARTS");
            player1.inGame = true;
            player2.inGame = true;
            boolean isEndGame = false;
            try {
                String move;
                String[] coord;
                int x = 0;
                int y = 0;
                int win = 0;
                while (!isEndGame)
                {
                    move = player1.inputFromPlayer.readLine();
                    //player1.toThisPlayer.println("+");
                    coord = move.split(" ");
                    x = Integer.parseInt(coord[0], 10);
                    y = Integer.parseInt(coord[1], 10);
                    board[x-1][y-1] = 1;
                    printBoard();
                    player2.toThisPlayer.println(move);

                    win = checkWinner();
                    if (win == 1)
                    {
                        player1.toThisPlayer.println("@endyes");
                        player2.toThisPlayer.println("@endno");
                        isEndGame = true;
                    }
                    if (win == 2)
                    {
                        player2.toThisPlayer.println("@endyes");
                        player1.toThisPlayer.println("@endno");
                        isEndGame = true;
                    }

                    move = player2.inputFromPlayer.readLine();
                    coord = move.split(" ");
                    x = Integer.parseInt(coord[0], 10);
                    y = Integer.parseInt(coord[1], 10);
                    board[x-1][y-1] = 2;
                    printBoard();
                    player1.toThisPlayer.println(move);

                    win = checkWinner();
                    if (win == 1)
                    {
                        player1.toThisPlayer.println("@end yes");
                        player2.toThisPlayer.println("@end no");
                        isEndGame = true;
                    }
                    if (win == 2)
                    {
                        player2.toThisPlayer.println("@end yes");
                        player1.toThisPlayer.println("@end no");
                        isEndGame = true;
                    }

                }
            }
            catch(Exception e)
            {System.out.println(e);}
        }
        public void printBoard()
        {
            System.out.println("----------");
            for (int i = 0; i < 10; i++)
            {
                System.out.print("| ");
                for (int j = 0; j < 10; j++)
                {
                    if (board[i][j] == 0) System.out.print(" ");
                    if (board[i][j] == 1) System.out.print("x");
                    if (board[i][j] == 2) System.out.print("o");
                }
                System.out.print("|\n");
            }
            System.out.println("----------");
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

        public void addFirstPlayer(WorkWithPlayerThread p)
        {
           // synchronized (boards) {
                player1 = p;
                System.out.println("Player 1");
                boards.add(this);
            //}
        }
        public void addSecondPlayer(WorkWithPlayerThread p)
        {
            player2 = p;
            full = true;
            System.out.println("Player 2");
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
        {System.out.println(e);}
        in.close();
    }

    public static void openRating() throws Exception
    {
        BufferedReader in = new BufferedReader(new FileReader("rating.txt"));
        String str;
        String[] info;
        try
        {
            while((str = in.readLine()) != null)
            {
                info = str.split(";");
                rating.put(Integer.parseInt(info[1], 10), info[0]);
            }
        }
        catch(Exception e)
        {System.out.println(e);}
        in.close();
    }

    public static boolean checkUser(String login, String pass)
    {
        try
        {
            openAccounts();
            openRating();
        } catch (Exception e) {
            System.out.println(e);
        }
        synchronized(users)
        {
            for (Map.Entry it : users.entrySet())
            {/*
                if (!isReg && it.getKey().equals(login) == true && it.getValue().equals(pass) == true)
                {
                    return true;
                }
                if (isReg && it.getKey().equals(login) == false) {
                    return false;
                }*/
                if (it.getKey().equals(login) == true && it.getValue().equals(pass) == true
                    /*&& !onlineUsers.contains(login)*/)
                {
                    //onlineUsers.add(login);
                    return true;
                }
            }
        }
        System.out.println("FALSE");
        return false;
    }

    public static boolean checkNewUser(String login, String pass)
    {
        try
        {
            openAccounts();
            openRating();
        } catch (Exception e) {
            System.out.println(e);
        }
        synchronized(users)
        {
            for (Map.Entry it : users.entrySet())
            {
                if (it.getKey().equals(login) == true)
                {
                    return false;
                }
            }
        }
        System.out.println("FALSE");
        return true;
    }

    public static void writeNewPlayer(String login, String password)
    {
        try
        {
            openAccounts();
            openRating();
        } catch (Exception e) {
            System.out.println(e);
        }
        try
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
                String res = login + ";" + password;
                writer.write(res);
                writer.close();
            }
        }
        catch(Exception e)
        {System.out.println(e);}
    }


}
