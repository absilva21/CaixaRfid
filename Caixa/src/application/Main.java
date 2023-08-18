package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.stage.Stage;



public class Main extends Application {

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
	
	public static void main(String[] args) {
		launch(args);
	}
}
