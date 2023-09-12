package admin;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Admin {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner teclado = new Scanner(System.in);
		String comando = "";
		String ipSever = "";
		String auth = "";
		System.out.println("digite o ip do servidor\n");
		ipSever = teclado.next();
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
			System.out.println("1 - caixa\n2 - histórico de compra");
			comando = teclado.next();
			
			
			
			if(comando.equals("1")) {
				System.out.println("Digite um comando:\n");
				System.out.println("1 - para bloquear ou liberar\n2 - para voltar ao menu anterior");
				comando = teclado.next();
				
				if(comando.equals("1")) {
					HttpClient client = HttpClient.newHttpClient();
					HttpRequest request = HttpRequest.newBuilder()
					          .GET()
					          .timeout(Duration.ofSeconds(10))
					          .uri(URI.create("http://"
					          +ipSever
					          +"/caixa"))
					          .header("auth", hash.toString(16))
					          .build();
					try {
						HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
						if(response.statusCode()==200) {
							JSONParser parser = new JSONParser(); 
							try {
								JSONObject json = (JSONObject) parser.parse(response.body());
								JSONArray caixas = (JSONArray) json.get("caixas");
								Iterator i = caixas.iterator();
								System.out.println("\ncodigo    ip    acesso\n");
								while(i.hasNext()) {
									JSONObject caixa = (JSONObject) i.next();
									String codigo = (String) caixa.get("codigo"); 
									String ip = (String) caixa.get("ip");
									String acesso = (boolean) caixa.get("acesso").equals("1") ? "livre" : "bloqueado"; 
									System.out.println("\n"+codigo+"    "+ip+"    "+acesso);
								}
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					System.out.println("\nDigite o código do caixa:\n");
					
					String caixa = teclado.next();
					
					System.out.println("\nDigite 1 - para liberar\n2 - bloquear:\n");
					
					comando = teclado.next();
					
					String acesso = comando.equals("1") ? "true" : "false" ; 
					
					HttpRequest requestCaixa = HttpRequest.newBuilder()
					          .PUT(HttpRequest.BodyPublishers.noBody())
					          .timeout(Duration.ofSeconds(10))
					          .uri(URI.create("http://"
					          +ipSever
					          +"/caixa?bloq-caixa="
					          +acesso
					          +"&"
					          +"caixa="
					          +caixa))
					          .header("auth", hash.toString(16))
					          .build();
					try {
						HttpResponse<String> response = client.send(requestCaixa,HttpResponse.BodyHandlers.ofString());
						if(response.statusCode()==200) {
							System.out.println("\noperação feita com sucesso\n");
						}else {
							System.out.println("\nerro na operação\n");
						}
					} catch (IOException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
			
			if(comando.equals("2")) {
				
			}
			
        	if(comando.equals("x")) {
				System.exit(0);
			}
        }
		
		
	}

}
