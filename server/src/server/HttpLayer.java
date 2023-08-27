package server;


import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.json.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import api.Produto;

//representa a camada http

public class HttpLayer extends Thread {
	
	private String req;
	private Socket socket;

	@Override
	public void run() {
		//dividindo a requisição em linhas
		String[] reqS = req.split("\r\n");
		//separando a primeira linha
		String[] auxRota = reqS[0].split(" ");
		// armazena a resposta a ser retornada
		String resTextAscii = "";
		//resgata a rota
		String[] rota = auxRota[1].split("\\?"); 
		//resgata os parametros
		String[] params = null;
		
		if(rota.length>1) {
			params = rota[1].split("&");
		}else {
			params = new String[1];
			params[0] = "";
		}
		
		//ROTA DE PRODUTOS
		if(rota[0].equals("/produto")) {
			
			resTextAscii = produto(auxRota[0], params,reqS[reqS.length-1]);
			
		}else {
			resTextAscii = "HTTP/1.1 404 Not Found\r\n"
					+ "Content-Type: text/html; charset=UTF-8\r\n"
					+"Content-Length: 60 \r\n"
					+ "\r\n"
					+"<html>\n"
					+ "<body>\n"
					+ "<h1>erro 404 o recurso não pode ser encontrado</h1>\n"
					+ "</body>\n"
					+ "</html>"; 
		}
		
		 
		byte[] bytes = resTextAscii.getBytes(StandardCharsets.UTF_8);
		String resTextUTF = new String(bytes,StandardCharsets.UTF_8);
		
		try {
			
			PrintStream response = new PrintStream(socket.getOutputStream());
			response.print(resTextUTF);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		try {
			
			socket.close();
			System.out.println("\nResposta enviada com sucesso\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
	}
	
	public HttpLayer(String req, Socket sock) {
		// TODO Auto-generated constructor stub
		this.req = req;
		this.socket = sock;
	}

	public String produto(String method, String[] params,String b) {
		String resTextAscii = "";
		//Método GET
		if(method.equals("GET")) {
			
			//recuperando o valor do parâmetro
			Produto p = new Produto(params[0].substring(params[0].indexOf('=')+1));
			//caso queira consultar um produto apenas
			if(params[0].startsWith("produto")){
				if(p.load()==1) {
					
					String body ="{\"codigo\":\""+p.getCodigo()+"\","
					+"\"descricao\":\""+p.getDescricao()+"\","
					+"\"valor\":\""+p.getValor()+"\","
					+"\"estoque\":\""+p.getEstoque()+"\"}";
					resTextAscii = "HTTP/1.1 200 OK\r\n"
							+ "Content-Type: application/json; charset=utf-8\r\n"
							+"Content-Length:"+ body.getBytes().length +"\r\n"
							+ "\r\n"
							+body;
				}else {
					resTextAscii = "HTTP/1.1 404 Not Found\r\n"
							+ "Content-Type: text/html; charset=UTF-8\r\n"
							+"Content-Length: 60 \r\n"
							+ "\r\n"
							+"<html>\n"
							+ "<body>\n"
							+ "<h1>erro 404 o recurso não pode ser encontrado</h1>\n"
							+ "</body>\n"
							+ "</html>"; 
				}
			}else if(params[0].startsWith("produtos")) {
				
			}
			else {
			
				resTextAscii = "HTTP/1.1 200 OK\r\n"
						+ "Content-Type: application/json; charset=utf-8\r\n"
						+"Content-Length: 16 \r\n"
						+ "\r\n"
						+"{\"mensagem\":\"ok\"}";
			}
			
		}if(method.equals("POST")) {
			resTextAscii = "HTTP/1.1 404 Not Found\r\n"
					+ "Content-Type: text/plain; charset=utf-8\r\n"
					+"Content-Length: 0 \r\n"
					+ "\r\n";
			
			JSONParser parser = new JSONParser();  
			try {
				JSONObject json = (JSONObject) parser.parse(b);
				
				String codigo = json.get("codigo").toString();
				String desc = json.get("descricao").toString();
				double valor = Double.parseDouble(json.get("valor").toString());
				double qtd = Double.parseDouble(json.get("estoque").toString());
				Produto p = new Produto(codigo,desc,valor,qtd);
				if(p.save(false)==1) {
					resTextAscii = "HTTP/1.1 200 OK\r\n"
							+ "Content-Type: text/plain; charset=utf-8\r\n"
							+"Content-Length: 0 \r\n"
							+ "\r\n";
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			
		}
		return resTextAscii;
	}

	

}
