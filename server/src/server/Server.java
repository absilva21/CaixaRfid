package server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Server {
	
	static ServerSocket server;

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		//Ligando o servidor
		server = new ServerSocket(80);
		System.out.println("servidor ligado");
		while(true) {
			//entrado na  escuta
			System.out.println("aguardando mensagem");
			Socket socket = server.accept();
			//lendo a mensagem
			
			InputStream inputStream = socket.getInputStream(); 
			
			
			//consumindo com segurança
					
			BufferedReader entrada =new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String menssage = "";
			String req = "";
			while((menssage = entrada.readLine())!=null){
				 	if(menssage.equals("")) {
				 		break;
				 	}
	                req += "\n"+menssage;
	                System.out.println( menssage);
	         }
			
			//enviando para a camada de aplicação
		
			System.out.println("enviando para a camada de aplicação");
			
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
