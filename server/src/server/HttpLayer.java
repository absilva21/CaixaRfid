package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class HttpLayer extends Thread {
	
	private String req;
	private Socket socket;

	@Override
	public void run() {
		
		String resText = "HTTP/1.1 200 OK\n\r\n";
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			oos.writeUTF(resText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			
			socket.close();
			System.out.println("Resposta enviada com sucesso");
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

	
	public String[] mensageProcess(String men) {
		String[] reqSeparada = null;
	    reqSeparada = req.split(" "); 
		return reqSeparada;
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
