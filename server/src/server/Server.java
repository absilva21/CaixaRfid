package server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Server {
	
	static ServerSocket server;

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		//Ligando o servidor
		server = new ServerSocket(80);
		System.out.println("servidor ligado\n");
		while(true) {
			//entrado na  escuta
			System.out.println("aguardando mensagem\n");
			Socket socket = server.accept();

			//lendo a mensagem	
			
			//consumindo com segurança
			
			BufferedReader   entrada = new BufferedReader (new InputStreamReader (socket.getInputStream()));
			
		  
		    System.out.println("zona crítica\n");
		    String req = "";
		   
			char[] buf = new char[8388608];
			entrada.read(buf);
			int n = 0;
			
			while(buf[n]!=0) {
				req += buf[n];
				n++;
			}
			
			System.out.println("a requisição "+req+"\n");
						
			//enviando para a camada de aplicação
		
			System.out.println("enviando para a camada de aplicação\n");
			
			HttpLayer http = new HttpLayer(req,socket);
			http.run();
		
			
			/*String[] reqSeparada = req.split(" "); 
			for(int i = 0;i<reqSeparada.length;i++) {
				System.out.println(reqSeparada[i]);
			}*/
			
			//System.out.println(textBuilder.toString());
		
	        
		}
		
	}

}
