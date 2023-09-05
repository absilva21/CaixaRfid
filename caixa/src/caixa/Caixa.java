package caixa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Caixa {

	public Caixa() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		Scanner teclado = new Scanner(System.in);
		String comando = "";
		String ipSever = "";
		String ipLeitor = "";
		String auth = "";
		System.out.println("digite o ip do servidor\n");
		ipSever = teclado.next();
		System.out.println("digite bo ip do leitor\n");
		ipLeitor = teclado.next();
		System.out.println("digite a chave do usuário\n");
		auth = teclado.next();
		
		MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        BigInteger hash = new BigInteger(1, md.digest(auth.getBytes()));
		
		
		while(true) {
			System.out.println("Digite um comando:\n");
			comando = teclado.next();
			
			if(comando.equals("l")) {
				Socket leitor = new Socket(ipLeitor,7710);
				
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
				
				
				for(int i=0;i<produtos.length;i++) {
					HttpClient client = HttpClient.newHttpClient();
					HttpRequest request = HttpRequest.newBuilder()
					          .GET()
					          .timeout(Duration.ofSeconds(10))
					          .uri(URI.create("http://"
					          +ipSever
					          +"/produto?produto="
					          +produtos[i])).header("auth", hash.toString(16))
					          .build();
					

					HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
					if(response.statusCode()==200) {
						JSONParser parser = new JSONParser();  
						
						try {
							JSONObject json = (JSONObject) parser.parse(response.body());
							System.out.println("\ncodigo             descricão            valor\n");
							System.out.println("\n"+json.get("codigo")+"             "+json.get("descricao")+"           R$"+ json.get("valor")+"\n");
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						System.out.println("não foi possível consultar a base de dados");
					}
				}
				
			}
			
			if(comando.equals("x")) {
				System.exit(0);
			}
			
			
		}
		
	}

}
