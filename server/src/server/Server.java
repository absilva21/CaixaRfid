package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
	
	static ServerSocket server;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		server = new ServerSocket(80);
		System.out.println("servidor ligado");
		while(true) {
			System.out.println("aguardando mensagem");
			Socket socket = server.accept();
			
			
			
			InputStream inputStream = socket.getInputStream(); 
			StringBuilder textBuilder = new StringBuilder();
			try (Reader reader = new BufferedReader(new InputStreamReader
				      (inputStream, StandardCharsets.UTF_8))) {
				        int c = 0;
				        while ((c = reader.read()) != -1) {
				            textBuilder.append((char) c);
				        }
			}
			
			System.out.println("aqui está a requisição\n");
			
			String req  = textBuilder.toString();
			
			String[] reqSeparada = req.split(" "); 
			for(int i = 0;i<reqSeparada.length;i++) {
				System.out.println(reqSeparada[i]);
			}
			
			//System.out.println(textBuilder.toString());
		
	         socket.close();
			 System.out.println("conexão encerrada");
		}
		
	}

}
