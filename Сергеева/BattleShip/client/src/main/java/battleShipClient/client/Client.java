package battleShipClient.client;

import battleShipClient.client.gui.*;

public class Client {
    public static void main(String[] args) {
        ClientAction clientAction = new ClientAction();
        ClientReaction clientReaction = new ClientReaction();
        ClientGame clientGame = new ClientGame(clientAction, clientReaction);
        new ServerConnection(clientAction, clientReaction, clientGame);
        new MainWindow(clientAction, clientReaction);
    }
}