package api;

import java.sql.*;
import java.util.LinkedList;

import server.HttpLayer;

public class CompraDB implements IPersistente {
	
	private Compra compra;
	
	public CompraDB(Compra c) {
		// TODO Auto-generated constructor stub
		this.compra = c;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	@Override
	public int save(boolean isUpdate) {
		// TODO Auto-generated method stub
		int result = 0;
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		 try (Connection connection = DriverManager.getConnection(HttpLayer.conn)){
			 Statement statement = connection.createStatement();
			 statement.executeUpdate("INSERT INTO compra(caixa) VALUES ("
			 +Integer.toString(compra.getCaixa())
			 +")");
			 String[] produtos = compra.getProdutos().toArray(new String[0]);
			 
			 for(int i=0;i<produtos.length;i++) {
				 statement.executeUpdate("INSERT INTO produto_compra(compra,produto) VALUES ((SELECT  MAX(codigo) FROM compra),"
				 +"\""
			     +produtos[i]
				 +"\")");
			 }
			 
			 result = 1;
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int load() {
		int result = 0;
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		 try (Connection connection = DriverManager.getConnection(HttpLayer.conn)){
			  System.out.println("Conexão realizada !!!!");
			  
			  Statement statement = connection.createStatement();
			  ResultSet rs = statement.executeQuery("SELECT * FROM compra WHERE codigo = "
			  +Integer.toString(this.compra.getCodigo()));
			  Statement statementAux  =  connection.createStatement();
			  ResultSet resAux = statementAux.executeQuery("SELECT produto  FROM produto_compra WHERE compra = "
			  +Integer.toString(this.compra.getCodigo()));
			  
			  
			  this.compra.setCaixa(Integer.parseInt(rs.getString("caixa")));
			  rs.close();
			
					  
			  this.compra.setProdutos(new LinkedList<String>());
					 
			  while(resAux.next()) {	
				  this.compra.getProdutos().add(resAux.getString("produto")); 
			  }
				  
			  result = 1; 
			  
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int delete() {
		// TODO Auto-generated method stub
		return 0;
	}

}
