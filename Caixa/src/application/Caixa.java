package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import java.net.Socket;



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
		//launch(args);
		Socket leitor = new Socket("192.168.0.111",70);
		
		
			
		BufferedWriter bufferOut = new BufferedWriter(new OutputStreamWriter(leitor.getOutputStream()));
		bufferOut.write( "read");
		bufferOut.flush();
		
		BufferedReader bufferIn = new BufferedReader(new InputStreamReader(leitor.getInputStream()));
		
	
		
		String req = "";
		
		char[] buf = new char[8388608];
		bufferIn.read(buf);
		
		int n = 0;
		
		while(buf[n]!=0) {
			req += buf[n];
			n++;
		}
		bufferOut.close();
		bufferIn.close();
		
		leitor.close();
		System.out.println("produtos:\n");
		System.out.println(req);
		

		
		System.exit(0);

	}
}
