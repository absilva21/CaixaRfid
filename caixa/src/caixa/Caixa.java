package caixa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Caixa {

	public Caixa() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		Scanner teclado = new Scanner(System.in);
		String comando = "";
		
		while(true) {
			System.out.println("Digite um comando:\n");
			comando = teclado.next();
			
			if(comando.equals("l")) {
				Socket leitor = new Socket("172.16.103.0",7700);
				
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
					System.out.println(produtos[i]);
				}
				/*for(int i=0;i<produtos.length;i++) {
					HttpClient client = HttpClient.newHttpClient();
					HttpRequest request = HttpRequest.newBuilder()
					          .GET()
					          .timeout(Duration.ofSeconds(10))
					          .uri(URI.create("http://172.16.103.240/produto?produto="+produtos[i]))
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
				}*/
				
			}
			
			if(comando.equals("x")) {
				System.exit(0);
			}
			
			
		}
		
	}

}
