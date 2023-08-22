package application;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.stage.Stage;



public class Caixa extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			
			Stage root = FXMLLoader.load(getClass().getResource("application.fxml"));
			
			primaryStage = root;
			
			primaryStage.show();
	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		launch(args);

	}
}
