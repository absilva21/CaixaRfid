package server;


import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

//representa a camada http

public class HttpLayer extends Thread {
	
	private String req;
	private Socket socket;

	@Override
	public void run() {
		
		String[] reqS = req.split("\r\n");
		String resTextAscii = "";
		
		for(int i = 0;i<reqS.length;i++) {
			System.out.println(reqS[i]);
		}
		
		if(reqS[0].startsWith("GET")) {
			resTextAscii = "HTTP/1.1 200 OK\r\n"
					+ "Content-Type: application/json; charset=utf-8\r\n"
					+"Content-Length: 29 \r\n"
					+ "\r\n"
					+"{\"resposta\":\"deu tudo certo\"}"; 
		}else {
			resTextAscii = "HTTP/1.1 404 Not Found\r\n"
					+ "Content-Type: text/html; charset=UTF-8\r\n"
					+"Content-Length: 60 \r\n"
					+ "\r\n"
					+"<html>\n"
					+ "<body>\n"
					+ "<h1>erro 404 o recuso não pode ser encontrado</h1>\n"
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

	

	public HttpLayer(Runnable target) {
		super(target);
		// TODO Auto-generated constructor stub
	}

	public HttpLayer(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public HttpLayer(ThreadGroup group, Runnable target) {
		super(group, target);
		// TODO Auto-generated constructor stub
	}

	public HttpLayer(ThreadGroup group, String name) {
		super(group, name);
		// TODO Auto-generated constructor stub
	}

	public HttpLayer(Runnable target, String name) {
		super(target, name);
		// TODO Auto-generated constructor stub
	}

	public HttpLayer(ThreadGroup group, Runnable target, String name) {
		super(group, target, name);
		// TODO Auto-generated constructor stub
	}

	public HttpLayer(ThreadGroup group, Runnable target, String name, long stackSize) {
		super(group, target, name, stackSize);
		// TODO Auto-generated constructor stub
	}

	public HttpLayer(ThreadGroup group, Runnable target, String name, long stackSize, boolean inheritThreadLocals) {
		super(group, target, name, stackSize, inheritThreadLocals);
		// TODO Auto-generated constructor stub
	}

}
