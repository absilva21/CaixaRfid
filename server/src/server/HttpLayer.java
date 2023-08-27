package server;


import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

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
		String[] params = rota[1].split("&");
		//ROTA DE PRODUTOS
		if(rota[0].equals("/produto")) {
			
			resTextAscii = produto(auxRota[0], params);
			
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

	public String produto(String method, String[] params) {
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
			
		}
		return resTextAscii;
	}

	

}
