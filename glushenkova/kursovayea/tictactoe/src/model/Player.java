package model;
import controller.ProfileController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;

public class Player extends Application {

	public static void main(String args[]) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("../view/AutorizeWindow.fxml").openStream());
        Scene scene = new Scene(root, 670, 470);
		stage.setTitle("Tic Tac");
		stage.setScene(scene);
		stage.show();
	}
}