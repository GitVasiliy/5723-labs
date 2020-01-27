package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ProfileController {

    @FXML
    private Button reg;

    @FXML
    private PasswordField password_reg;

    @FXML
    private TextField login_reg;

    @FXML
    private PasswordField password;

    @FXML
    private Label aut_error;

    @FXML
    private Label acc_exist;

    @FXML
    private Button sign_up;

    @FXML
    private TextField login;

    @FXML
    private Button sign_in;

    @FXML
    private Button back_button;

    @FXML
    private Label points_label;

    @FXML
    private Button join_to_game_button;

    @FXML
    private Button create_game_button;

    @FXML
    private Label waiting_label;

    @FXML
    private GridPane boardGUI;

    @FXML
    private TextField x_y_;

    @FXML
    private Button move_button;

    boolean inGame = false;


    public void setCWS(ConWithServer c/*, boolean iG*/)
    {
        cws = c;
       // inGame = iG;
    }

    @FXML
    void signIn(ActionEvent event) {
        String log = login.getText();
        String pass = password.getText();
        System.out.println(log + " " + pass);
        if (Server.checkUser(log, pass)) {
            System.out.println("log in");
            try
            {
                InetAddress ip = InetAddress.getByName("127.0.0.1");
                Socket fromServer = new Socket(ip, Integer.parseInt("209", 10));          // подключились к серверу с ip -> читаем и отправляем информацию на этот сервер
                PrintWriter toServer = new PrintWriter(fromServer.getOutputStream(), true); // поток чтобы отправлять на сервер информацию
                cws = new ConWithServer(fromServer, toServer);
               // cws.start();
                Stage profile = (Stage)sign_in.getScene().getWindow(); // открытие профайла в том же окнe
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getResource("../view/ProfileWindow.fxml").openStream());
                ProfileController controller = loader.getController();
                controller.setCWS(cws/*, inGame*/);
                Scene scene = new Scene(root, 670, 470);
                profile.setTitle("Tic Tac");
                profile.setScene(scene);
                profile.show();
            }
            catch(Exception e)
            {System.out.println(e);}
        }
        else
            aut_error.setVisible(true);

    }

    @FXML
    void signUp(ActionEvent event) throws IOException {
        try
        {
            Stage profile = (Stage)sign_up.getScene().getWindow(); // открытие профайла в том же окнe
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("../view/RegistrWindow.fxml").openStream());
            ProfileController controller = loader.getController();
            controller.setCWS(cws/*, inGame*/);
            Scene scene = new Scene(root, 670, 470);
            profile.setTitle("Tic Tac");
            profile.setScene(scene);
            profile.show();

        }
        catch(Exception e)
        {System.out.println(e);}
    }
    @FXML
    void registr(ActionEvent event) {
        String log = login_reg.getText();
        String pass = password_reg.getText();
        System.out.println(log + " " + pass);
        if (Server.checkNewUser(log, pass))
        {
            System.out.println("registration");
            Server.writeNewPlayer(log, pass);
            try
            {
                InetAddress ip = InetAddress.getByName("127.0.0.1");
                Socket fromServer = new Socket(ip, Integer.parseInt("209", 10));          // подключились к серверу с ip -> читаем и отправляем информацию на этот сервер
                PrintWriter toServer = new PrintWriter(fromServer.getOutputStream(), true); // поток чтобы отправлять на сервер информацию
                cws = new ConWithServer(fromServer, toServer);
               // cws.start();
                Stage profile = (Stage)reg.getScene().getWindow(); // открытие профайла в том же окнe
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getResource("../view/ProfileWindow.fxml").openStream());
                ProfileController controller = loader.getController();
                controller.setCWS(cws/*, inGame*/);
                Scene scene = new Scene(root, 670, 470);
                profile.setTitle("Tic Tac");
                profile.setScene(scene);
                profile.show();
            }
            catch(Exception e)
            {System.out.println(e);}
        }
        else
            acc_exist.setVisible(true);
    }

    @FXML
    void back(ActionEvent event) {
        try
        {
            Stage profile = (Stage)back_button.getScene().getWindow(); // открытие профайла в том же окнe
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("../view/AutorizeWindow.fxml").openStream());
            ProfileController controller = loader.getController();
            controller.setCWS(cws/*, inGame*/);
            Scene scene = new Scene(root, 670, 470);
            profile.setTitle("Tic Tac");
            profile.setScene(scene);
            profile.show();

        }
        catch(Exception e)
        {System.out.println(e);}
    }

    public class ConWithServer extends Thread // может быть не потоком
    {
        public PrintWriter toServer; // отправить
        public Socket fromServer;
        public BufferedReader inputFromServer; //принять
        ProfileController controller;
        GridPane boardGUI;

        public ConWithServer(Socket s , PrintWriter pw) {
            fromServer = s;
            toServer = pw;
            toServer.println("I JOIIIIN (CWS)");
            try {
                inputFromServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        public void setGUI(GridPane g, ProfileController c)
        {
            controller = c;
            boardGUI = g;
        }
        @Override
        public void run() // поток чтобы принимать ходы другого игрока и ставить их
        {
            boolean isEndGame = false;
            while (!isEndGame)
            {
                System.out.println("RUNNN");
                try {
                    String move = cws.getBR().readLine();
                    System.out.println("Server: " + move);
                    String[] coord;
                    boolean win = false;
                    int x = 0, y = 0;
                    if (move.contains(" ") == true)
                    {
                        System.out.println("MOVE FROM OPPPPP");
                        coord = move.split(" ");
                        x = Integer.parseInt(coord[0], 10);
                        y = Integer.parseInt(coord[1], 10);
                        System.out.println(x + " opp " + y);
                        //controller.drawMove(x, y);
                        Circle c = new Circle();
                        c.setRadius(10);
                        c.setStroke(Color.RED);
                        //controller.getBoardGUI().add(c, y-1, x-1);
                        System.out.println("GUI 1");
                        controller.getBoardGUI().add(c, y-1, x-1);
                        System.out.println("GUI 2");
                        //x_y_.setDisable(false);
                        //move_button.setDisable(false);
                    }
                    if (move.startsWith("@endyes")) {
                        isEndGame = true;
                        win = true;
                    }
                    if (move.startsWith("@endno")) {
                        isEndGame = true;
                        win = false;
                    }
                    if (isEndGame)
                    {
                        //загрузка виннера
                        if (win) System.out.println("You win");
                        else System.out.println("You lose");
                    }

                }
                catch (Exception e)
                {System.out.println(e); }
            }
        }
        public PrintWriter getPW()
        {
            return toServer;
        }
        public BufferedReader getBR()
        {
            return inputFromServer;
        }
        public void sendRequest(String request)
        {
            cws.getPW().println(request);
        }
        public void waitResponse()
        {
            try {
                String message = cws.getBR().readLine();
                System.out.println("Server: " + message);
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        public boolean isOpponentMove(String m) //переедалть правильно
        {
            if (m.charAt(0) >= '1' && m.charAt(0) <= '9' && m.contains(" ") == true)
                return true;
            return false;
        }
    }

    ConWithServer cws;


    @FXML
    void createGame(ActionEvent event) { //создает доску если ктото присоед.
        System.out.println("CHECK CREATE BUTTON");
        try
        {
            join_to_game_button.setDisable(true);
            create_game_button.setDisable(true);
            waiting_label.setVisible(true);
            cws.sendRequest("@create");
           // cws.waitResponse(); // waiting an opponent
            //cws.waitResponse();
            Stage profile = (Stage)create_game_button.getScene().getWindow(); // открытие профайла в том же окнe, если игрок нашелся
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("../view/BoardWindow.fxml").openStream());
            ProfileController controller = loader.getController();
            inGame = true;
            controller.setCWS(cws);
            cws.waitResponse(); // ypur game starts
            Scene scene = new Scene(root, 670, 470);
            profile.setTitle("Tic Tac");
            profile.setScene(scene);
            profile.show();
            cws.setGUI(boardGUI, this);
            cws.start();

        }
        catch(Exception e)
        {System.out.println(e);}
    }

    @FXML
    void joinGame(ActionEvent event) {
        try {
            System.out.println("CHECK JOIN" + inGame); //откртие доски с игроком и игра.
            join_to_game_button.setDisable(true);
            create_game_button.setDisable(true);
            boolean join = false;
            int i = 0;
            waiting_label.setVisible(true);
            cws.sendRequest("@join");
           // cws.waitResponse(); // ypur game starts
            //cws.waitResponse();
            Stage profile = (Stage)join_to_game_button.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("../view/BoardWindow.fxml").openStream());
            ProfileController controller = loader.getController();
            inGame = true;
            controller.setCWS(cws/*, inGame*/);
            cws.waitResponse(); // your game starts
            Scene scene = new Scene(root, 670, 470);
            profile.setTitle("Tic Tac");
            profile.setScene(scene);
            profile.show();
            cws.setGUI(boardGUI, this);
            cws.start();
        }
        catch(Exception e)
        {System.out.println(e);}
    }

    public GridPane getBoardGUI()
    {
        return boardGUI;
    }

    @FXML
    void doMove(ActionEvent event) {
        int[][] board = new int[10][10];
        try {
           /* String message;
            String move;
            boolean isEndGame = false;
            boolean win = false;
            boolean err = true;
           // while (!isEndGame) {
                //сделать по кнопке но как отправлть? пробегать по кнопкам ?? и отправлять координаты
                //нажатой кнопки
           // while (err)
           // {
                move = x_y_.getText();
                String[] coord;
                int x;
                int y;
                if (move.contains(" ") == true) {
                    coord = move.split(" ");
                    x = Integer.parseInt(coord[0], 10);
                    y = Integer.parseInt(coord[1], 10);
                    System.out.println(x + " " + y);
                    if (x >= 1 && x <= 10 && y >= 1 && y <= 10
                            && board[x - 1][y - 1] == 0 && coord.length == 2) {
                        board[x - 1][y - 1] = 1;
                        err = false;
                        *//*cws.getPW().println(move);*//*
                        cws.sendRequest(move);
                        Circle c = new Circle();
                        c.setRadius(10);
                        c.setStroke(Color.BLUEVIOLET);
                        boardGUI.add(c, y - 1, x - 1);
                        x_y_.setDisable(true);
                        move_button.setDisable(true);
                    }
                    //cws.getPW().println(move);
                    message = cws.getBR().readLine();
                    System.out.println(message + " !!!!!");
                //}
                    if (isOpponentMove(message) == true)
                    {
                        System.out.println("MOVE OPPON");
                        board[x - 1][y - 1] = 2;
                        coord = message.split(" ");
                        x = Integer.parseInt(coord[0], 10);
                        y = Integer.parseInt(coord[1], 10);
                        Circle c = new Circle();
                        *//*c.setRadiusX(18);
                        c.setCenterY(14);*//*
                        c.setRadius(10);
                        c.setStroke(Color.RED);
                        boardGUI.add(c, y-1, x-1);
                        x_y_.setDisable(false);
                        move_button.setDisable(false);
                    }
                    if (message.startsWith("@end yes")) {
                        isEndGame = true;
                        win = true;
                    }
                    if (message.startsWith("@end no")) {
                        isEndGame = true;
                        win = false;
                    }
                }
                if (isEndGame)
                {
                    //загрузка виннера
                    if (win) System.out.println("You win");
                    else System.out.println("You lose");
                }

            //}*/

            String move = x_y_.getText();

            String[] coord;
            int x;
            int y;
            if (move.contains(" ") == true) {
                coord = move.split(" ");
                x = Integer.parseInt(coord[0], 10);
                y = Integer.parseInt(coord[1], 10);
                System.out.println(x + "-" + y);
                if (x >= 1 && x <= 10 && y >= 1 && y <= 10
                        && board[x - 1][y - 1] == 0 && coord.length == 2) {
                    board[x - 1][y - 1] = 1;
                    Circle c = new Circle();
                    c.setRadius(10);
                    c.setStroke(Color.BLUEVIOLET);
                    boardGUI.add(c, y - 1, x - 1);
                    cws.sendRequest(move);
                    //x_y_.setDisable(true);
                    //move_button.setDisable(true);
                }

            }




        }
        catch(Exception e)
        {System.out.println(e);}
    }
    public void drawMove(int x, int y)
    {
        Circle c = new Circle();
        c.setRadius(10);
        c.setStroke(Color.RED);
        boardGUI.add(c, y-1, x-1);
    }
}



