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
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;






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
		
		Scanner teclado = new Scanner(System.in);
		String comando = "";
		
		while(true) {
			System.out.println("Digite um comando:\n");
			comando = teclado.next();
			
			if(comando.equals("l")) {
				Socket leitor = new Socket("localhost",70);
				
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
				
				String[] produtos = req.split("\n");
				System.out.println("\nprodutos: \n");
				
				for(int i=0;i<produtos.length;i++) {
					HttpClient client = HttpClient.newHttpClient();
					HttpRequest request = HttpRequest.newBuilder()
					          .GET()
					          .timeout(Duration.ofSeconds(10))
					          .uri(URI.create("http://localhost/produto?produto="+produtos[i]))
					          .build();

					HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
					if(response.statusCode()==200) {
						JSONParser parser = new JSONParser();  
						try {
							JSONObject json = (JSONObject) parser.parse(response.body());
							System.out.println("\ncodigo             descricão            valor\n");
							System.out.println("\n"+json.get("codigo")+"             "+json.get("descricao")+"           R$"+ json.get("valor")+"\n");
						}catch(ParseException e) {
							e.printStackTrace();
						}
					}
				}
				
			}
			
			if(comando.equals("x")) {
				System.exit(0);
			}
			
			
		}
		
	

	}
}
