package server;


import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import api.Caixa;
import java.sql.*;
import org.json.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import api.Compra;
import api.Produto;

//representa a camada http

public class HttpLayer extends Thread {
	
	private String req;
	private Socket socket;
	
	public static final String conn = "jdbc:sqlite:"+System.getProperty("user.dir")+"/dados.db";
	
	public static final String  ERRO404 = "HTTP/1.1 404 Not Found\r\n"
			+ "Content-Type: text/html; charset=UTF-8\r\n"
			+"Content-Length: 60 \r\n"
			+ "\r\n"
			+"<html>\n"
			+ "<body>\n"
			+ "<h1>erro 404 o recurso não pode ser encontrado</h1>\n"
			+ "</body>\n"
			+ "</html>"; 
	public static final String CODE200 = "HTTP/1.1 200 OK\r\n"
			+ "Content-Type: text/plain; charset=utf-8\r\n"
			+"Content-Length: 0 \r\n"
			+ "\r\n";
	
	public static final String CODE500 = "HTTP/1.1 200 OK\r\n"
			+ "Content-Type: text/plain; charset=utf-8\r\n"
			+"Content-Length: 0 \r\n"
			+ "\r\n"
			+"erro interno";
	
	public static final String CODE401 = "HTTP/1.1 401 Unauthorized\r\n"
			+"WWW-Authenticate: Basic realm=\"Restricted area\"\r\n"
			+ "Content-Type: text/plain; charset=utf-8\r\n"
			+"Content-Length: 0 \r\n"
			+ "\r\n";

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
		
	    String key = "";
	    
	    for(int i = 0;i<reqS.length;i++) {
	    	if(reqS[i].startsWith("auth")) {
	    		key = reqS[i];
	    		break;
	    		
	    	}
	    }
		
	    
		if(rota.length>1) {
			params = rota[1].split("&");
		}else {
			params = new String[1];
			params[0] = "";
		}
		
		
		
		
		if(auth(key)) {
			//ROTA DE PRODUTOS
			if(rota[0].equals("/produto")) {
				resTextAscii = produto(auxRota[0], params,reqS[reqS.length-1]);
			}else if(rota[0].equals("/compra")) {
				resTextAscii = compra(auxRota[0], params, reqS[reqS.length-1],key);
			}else if(rota[0].equals("/caixa")){
				resTextAscii = caixa(auxRota[0], params, reqS[reqS.length-1]);
			}else {
				resTextAscii = ERRO404; 
			}
		}else {
			resTextAscii = CODE401;
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
	
	public String usuario(String method, String[] params,String b) {
		String resTextAscii = ERRO404;
		
		return resTextAscii; 
	}
	
	public String caixa(String method, String[] params,String b) {
		String resTextAscii = ERRO404;
		
		if(method.equals("GET")) {
			if(params[0].startsWith("codigo")){
				Caixa caixa = new Caixa(Integer.parseInt(params[0].substring(params[0].indexOf('=')+1)));
				if(caixa.load()==1) {
					String body ="{\"codigo\":\""
							+Integer.toString(caixa.getCodigo())
							+"\","
							+"\"ip\":\""
							+caixa.getIp()
							+"\"}";
					 resTextAscii = "HTTP/1.1 200 OK\r\n"
								+ "Content-Type: application/json; charset=utf-8\r\n"
								+"Content-Length:"+ body.getBytes().length +"\r\n"
								+ "\r\n"
								+body;
					
				}
			}else {
				try {
					Class.forName("org.sqlite.JDBC");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try (Connection connection = DriverManager.getConnection(conn)){
					String body = "{\"caixas\":[";
					Statement statement = connection.createStatement();
					ResultSet rs = statement.executeQuery("SELECT c.codigo,c.ip,uc.acesso  FROM  caixa c INNER JOIN usuario_caixa uc ON c.codigo = uc.caixa ");
					
					while(rs.next()) {
						body += "{\"codigo\":\""
								+rs.getString(1)
								+"\","
								+"\"ip\":\""
								+rs.getString(2)
								+"\",\"acesso\":\""
								+rs.getShort(3)
								+ "\"},";
					}
					
					body += "]}";
					
					 resTextAscii = "HTTP/1.1 200 OK\r\n"
								+ "Content-Type: application/json; charset=utf-8\r\n"
								+"Content-Length:"+ body.getBytes().length +"\r\n"
								+ "\r\n"
								+body;
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if(method.equals("POST")) {
			JSONParser parser = new JSONParser(); 
			int result = 0;
			try {
				JSONObject json = (JSONObject) parser.parse(b);
				Caixa caixa = new Caixa(0,json.get("ip").toString(),Boolean.parseBoolean(json.get("acesso").toString()),json.get("auth").toString());
				result = caixa.save(false);
				if(result==1) {
					 resTextAscii = CODE200;
				}
				
				if(result==2) {
					resTextAscii = CODE500;
				}
				
			
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if(method.equals("DELETE")) {
			if(params[0].startsWith("codigo")){
				Caixa caixa = new Caixa(Integer.parseInt(params[0].substring(params[0].indexOf('=')+1)));
				if(caixa.delete()==1) {
					 resTextAscii = CODE200;
				}
			}
		}
		
		if(method.equals("PUT")) {
			
			if(params[0].startsWith("bloq-caixa")&&params[1].startsWith("caixa")) {
				String codigoS = params[1].substring(params[1].indexOf('=')+1);
				int codigo = Integer.parseInt(codigoS);
				String acesso = params[0].substring(params[0].indexOf('=')+1);
				Caixa caixa = new Caixa(codigo);
				if(caixa.load()==1) {
					caixa.setAcesso(Boolean.parseBoolean(acesso));
					if(caixa.save(true)==1) {
						 resTextAscii = CODE200; 
					}
				}
				
			}else {
				JSONParser parser = new JSONParser(); 
				
				try {
					JSONObject json = (JSONObject) parser.parse(b);
					Caixa caixa = new Caixa(Integer.parseInt(json.get("codigo").toString()),json.get("ip").toString(),Boolean.parseBoolean(json.get("acesso").toString()),json.get("auth").toString());
					if(caixa.save(true)==1) {
						 resTextAscii = CODE200; 
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}
		
		
		return resTextAscii;
	}
	
	public String compra(String method, String[] params,String b,String key) {
		String resTextAscii = ERRO404;
		
		if(method.equals("GET")) {
			
			if(params[0].startsWith("codigo")){
				Compra compra = new Compra(Integer.parseInt(params[0].substring(params[0].indexOf('=')+1)));
				if(compra.load()==1) {
					
					String[] produtos = compra.getProdutos().toArray(new String[0]);
					
					String body ="{\"codigo\":\""
					+Integer.toString(compra.getCodigo())
					+"\","
					+"\"produtos\":[\"";
					for(int i = 0;i<produtos.length;i++) {
						body +=  produtos[i] +"\",";
					}
				 body += "],\"caixa\":\""
				 +Integer.toString(compra.getCaixa())
				 +"\"}"; 
				 
				 resTextAscii = "HTTP/1.1 200 OK\r\n"
							+ "Content-Type: application/json; charset=utf-8\r\n"
							+"Content-Length:"+ body.getBytes().length +"\r\n"
							+ "\r\n"
							+body;
				 
				}
			}else if(params[0].startsWith("caixa")) {
				try (Connection connection = DriverManager.getConnection(conn)){
					Statement statement = connection.createStatement();
					ResultSet rs = statement.executeQuery("SELECT c.codigo,"
							+ "p.codigo,"
							+ "p.descricao,"
							+ "p.preco  "
							+ "FROM "
							+ "produto p, "
							+ "compra c  "
							+ "INNER JOIN "
							+ "produto_compra pc ON c.codigo = pc.compra "
							+ "WHERE p.codigo = pc.produto  AND c.caixa = "
							+params[0].substring(params[0].indexOf('=')+1));
					String body = "{\"compras\":[";
					
					while(rs.next()) {
						body += "{\"codigo\":\"" 
					          + rs.getString(1)
					          + "\",\"produto\":\""
					          +rs.getString(2)
						      +"\","
						      +"\"descricao\":\""
						      +rs.getString(3)
						      +"\","
						      +"\"preco\":\""
						      +rs.getString(4)
				              + "\"},";
					}
					body += "]}";
					
					resTextAscii = "HTTP/1.1 200 OK\r\n"
							+ "Content-Type: application/json; charset=utf-8\r\n"
							+"Content-Length:"+ body.getBytes().length +"\r\n"
							+ "\r\n"
							+body;
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				try (Connection connection = DriverManager.getConnection(conn)){
					Statement statement = connection.createStatement();
					ResultSet rs = statement.executeQuery("SELECT *  FROM compra");
					String body = "{\"compras\":[";
					
					while(rs.next()) {
						body += "{\"codigo\":\"" 
					          + rs.getString(1)
					          + "\",\"caixa\":\""
					          +rs.getString(2)
						      +"\","
						      +"\"produtos\":[";
						Statement statementAux = connection.createStatement();
						ResultSet rsAux = statementAux.executeQuery("SELECT produto FROM produto_compra WHERE compra = "
								 + rs.getString(1));
						while(rsAux.next()) {
							body += "\"" 
						            +rsAux.getString(1)
									+"\",";
						}
						body += "]},";
					}
					body += "]}";
					
					resTextAscii = "HTTP/1.1 200 OK\r\n"
							+ "Content-Type: application/json; charset=utf-8\r\n"
							+"Content-Length:"+ body.getBytes().length +"\r\n"
							+ "\r\n"
							+body;
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
		}
		
		if(method.equals("POST")) {
			int caixa = 0;
			JSONParser parser = new JSONParser(); 
			try {
				JSONObject json = (JSONObject) parser.parse(b);
				String[] p = json.get("produtos").toString().split(",");
				LinkedList<String> produtos = new LinkedList<String>();
				
				for(int i=0;i<p.length;i++) {
					produtos.add(p[i]);
				}
				
				
				try {
					Class.forName("org.sqlite.JDBC");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try (Connection connection = DriverManager.getConnection(conn)){
					Statement statement = connection.createStatement();
					String sql = "SELECT uc.caixa  FROM  usuario_caixa uc INNER JOIN usuario u ON "
                            + "uc.usuario = u.codigo WHERE u.auth = \""
                            +key.substring(key.indexOf(":")+2)
                            +"\"";
					ResultSet rs = statement.executeQuery(sql);
					if(rs.next()) {
						caixa = rs.getInt(1);
						connection.close();
						Compra c = new Compra(produtos,caixa);
						if(c.save(false)==1) {
							resTextAscii = CODE200;
						}
					}
	 				
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		
		return resTextAscii;
	}

	public String produto(String method, String[] params,String b) {
		String resTextAscii = ERRO404;
		
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
				}
			}
			else {
			
				resTextAscii = "HTTP/1.1 200 OK\r\n"
						+ "Content-Type: application/json; charset=utf-8\r\n"
						+"Content-Length: 16 \r\n"
						+ "\r\n"
						+"{\"mensagem\":\"ok\"}";
			}
			
		}
		
		if(method.equals("POST")) {
			
			JSONParser parser = new JSONParser();  
			try {
				JSONObject json = (JSONObject) parser.parse(b);
				
				String codigo = json.get("codigo").toString();
				String desc = json.get("descricao").toString();
				double valor = Double.parseDouble(json.get("valor").toString());
				double qtd = Double.parseDouble(json.get("estoque").toString());
				Produto p = new Produto(codigo,desc,valor,qtd);
				if(p.save(false)==1) {
					resTextAscii = CODE200;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			
		}
		
		if(method.equals("PUT")){
			JSONParser parser = new JSONParser();  
			try {
				JSONObject json = (JSONObject) parser.parse(b);
				
				String codigo = json.get("codigo").toString();
				String desc = json.get("descricao").toString();
				double valor = Double.parseDouble(json.get("valor").toString());
				double qtd = Double.parseDouble(json.get("estoque").toString());
				Produto p = new Produto(codigo,desc,valor,qtd);
				if(p.save(true)==1) {
					resTextAscii = CODE200;
				}
			}catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}  
		}
		if(method.equals("DELETE")) {
			Produto p = new Produto(params[0].substring(params[0].indexOf('=')+1));
			if(params[0].startsWith("produto")){
				if(p.delete()==1) {
					resTextAscii = CODE200;
				}
			}
		}
		return resTextAscii;
	}

	public boolean auth(String key ) {
		boolean result = false;
		String[] aux = key.split(":");
		if(aux.length>1) {
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try (Connection connection = DriverManager.getConnection(conn)){
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery("SELECT * FROM usuario  WHERE auth = \""
					      +aux[1].substring(1)
					      +"\"");
				
			
				if(rs.next()) {
					int codigo = rs.getInt(1) ;
					int tipo = rs.getInt(3);
					
					if(tipo==1) {
						result = true;
					}else{
						 rs = statement.executeQuery("SELECT acesso FROM usuario_caixa  WHERE usuario = "
							      +Integer.toString(codigo));
						 if(rs.next()) {
							 if(rs.getInt(1)==1) {
								 result = true;
							 }
						 }
					}
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

}
