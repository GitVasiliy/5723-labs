package com.company;

import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


class ServerSomthing extends Thread {
    
    private Socket socket; // сокет, через который сервер общается с клиентом,
    // кроме него - клиент и сервер никак не связаны
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток завписи в сокет
    private String name;
    private boolean isAdmin;

    private static boolean isAdminExist;// существует ли админ на сервере
    private static String secret = "admin";
    private Set<String> bannedUsers = new HashSet<>();
    
    public ServerSomthing(Socket socket) throws IOException {
        this.socket = socket;
        // если потоку ввода/вывода приведут к генерированию искдючения, оно проброситься дальше
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        // сооюбщений новому поключению
        //bannedUsers = new Set<String>();
        start(); // вызываем run()
    }

    private boolean checkName(String name)
    {
        for (int i = 0; i < Server.serverList.size(); i++) {
            if (Server.serverList.get(i).name != null && Server.serverList.get(i).name.equals(name))
                return false;
        }
        return true;
    }

    private boolean isBanned()
    {
        return bannedUsers.contains(name);
    }

    @Override
    public void run() {
        String word;
        try {
            boolean res = false;
            String clientName;
            do {
                // первое сообщение отправленное сюда - это никнейм
                clientName = in.readLine();
                res = checkName(clientName);
                String status;

                if (clientName.compareToIgnoreCase(secret)==0 && isAdminExist==false)
                {
                    isAdminExist = true;
                    status = "admin";
                    isAdmin = true;
                }
                else if (res)
                    status = "ok";
                else
                   status = "error";
                out.write(status + "\n");
                out.flush();
            }
           while (res == false);//?



           name = clientName;
            {
                try {
                    out.write("hello " + name + "\n");
                    out.flush(); // flush() нужен для выталкивания оставшихся данных
                    // если такие есть, и очистки потока для дьнейших нужд
                } catch (IOException ignored) {

                }
                try {
                    while (true) {
                        word = in.readLine();
                        if (word.equals("stop")) {
                            this.downService(); // харакири
                            break; // если пришла пустая строка - выходим из цикла прослушки
                        } else if (word.startsWith("ban")) {
                            if (isAdmin) {
                                String[] temp = word.split(": ");
                                String name = temp[1];
                                bannedUsers.add(name);
                            }
                        } else if (!isBanned() && word.startsWith("@")) {
                            String name = word.substring(1, word.indexOf(" "));
                            word = word.substring(word.indexOf(" ") + 1);
                            for (ServerSomthing vr : Server.serverList) {
                                if (vr.name.equals(name))
                                    vr.send(name + ": " + word); // отослать принятое сообщение с привязанного клиента всем остальным
                            }
                        } else if (!isBanned()) {
                            for (ServerSomthing vr : Server.serverList) {
                                if (vr != this)
                                    vr.send(name + ": " + word); // отослать принятое сообщение с привязанного клиента всем остальным
                            }
                        }

                        System.out.println("Echoing: " + word);
                    }
                } catch (NullPointerException ignored) {
                }
            }

            
        } catch (IOException e) {
            this.downService();
        }
    }
    

    private void send(String msg) {
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException ignored) {}
        
    }
    

    private void downService() {
            try {
            if(!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
                this.interrupt();
                Server.serverList.remove(this);

            }
        } catch (IOException ignored) {}
    }
}

public class Server {

    public static final int PORT = 8081;
    public static LinkedList<ServerSomthing> serverList = new LinkedList<>(); // список всех нитей - экземпляров
    // сервера, слушающих каждый своего клиента

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        System.out.println("Server Started");
        try {
            while (true) {
                // Блокируется до возникновения нового соединения:
                Socket socket = server.accept();
                try {
                    serverList.add(new ServerSomthing(socket)); // добавить новое соединенние в список
                } catch (IOException e) {
                    // Если завершится неудачей, закрывается сокет,
                    // в противном случае, нить закроет его:
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }
}