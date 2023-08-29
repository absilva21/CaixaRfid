package api;

import java.sql.*;

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
		return 0;
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
		
		 try (Connection connection = DriverManager.getConnection("jdbc:sqlite:"+System.getProperty("user.dir")+"\\src\\"+"dados.db")){
			  System.out.println("Conexão realizada !!!!");
			  
			  Statement statement = connection.createStatement();
			  ResultSet rs = statement.executeQuery("SELECT * FROM compra WHERE codigo = "
			  +Integer.toString(this.compra.getCodigo()));
			  Statement statementAux  =  connection.createStatement();
			  ResultSet resAux = statementAux.executeQuery("SELECT produto  FROM produto_compra WHERE compra = "
			  +Integer.toString(this.compra.getCodigo()));
			  
			  if(rs.getType() == ResultSet.TYPE_FORWARD_ONLY ) {
				  this.compra.setCaixa(Integer.parseInt(rs.getString("caixa")));
				  rs.close();
				  int length = 0;
				  if(rs.getType() == ResultSet.TYPE_FORWARD_ONLY ) {
					  this.compra.setProdutos(new String[1]);
					  this.compra.getProdutos()[0] = resAux.getString("produto");
				  }else{
					  while(resAux.next()) {
						  length ++;
					  }
					  this.compra.setProdutos(new String[length]);
					  resAux.beforeFirst();
					  while(resAux.next()) {
						  this.compra.getProdutos()[resAux.getRow()-1] = resAux.getString("produto");
					  }
				  }
				 
				  
				  
				  result = 1;
			  }
			 
			  
			  
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
