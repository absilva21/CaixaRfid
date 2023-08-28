package api;


import java.sql.*;

//representa a classe que vai carregar ou persistir os dados
public class ProdutoDB implements IPersistente{
	

	//represente o objeto que será salvo ou carregado
	private Produto produto;

	public ProdutoDB(Produto p) {
		// TODO Auto-generated constructor stub
		this.produto = p;
	}
	
	//retorna 1 se tiver concluido, 0 se não encontrar a linha, -1 se der erro
	public int load(){
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
			  
			  ResultSet rs =  statement.executeQuery("Select * FROM produto p WHERE p.codigo =  " + "\""+produto.getCodigo()+"\"");
              if(rs.next()) {
            	  produto.setDescricao(rs.getString(2));
            	  produto.setValor(rs.getDouble(3));
            	  produto.setEstoque(rs.getDouble(4));
            	  connection.close();
            	  result = 1;
              }
            	  
		  } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	//se isUpadate for verdadeiro faz o update
	public int save(boolean update) {
		int result = 0;
		
		if(update) {
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try (Connection connection = DriverManager.getConnection("jdbc:sqlite:"+System.getProperty("user.dir")+"\\src\\"+"dados.db")){
				Statement statement = connection.createStatement();
				statement.executeUpdate("UPDATE produto SET descricao =  "
				+"\""
			    +produto.getDescricao()
			    +"\",preco = "
			    +Double.toString(produto.getValor())
			    +",qtd = "
			    +Double.toString(produto.getEstoque()) 
			    +" WHERE codigo = \""
			    +produto.getCodigo()
			    +"\"");
				result = 1;
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try (Connection connection = DriverManager.getConnection("jdbc:sqlite:"+System.getProperty("user.dir")+"\\src\\"+"dados.db")){
				 System.out.println("Conexão realizada !!!!");
				 Statement statement = connection.createStatement();
				 statement.executeUpdate("INSERT INTO produto(codigo,descricao,preco,qtd) VALUES (\""
				 +produto.getCodigo()
				 +"\",\""
				 +produto.getDescricao()
				 +"\","
				 +Double.toString(produto.getValor())
				 +","
				 +Double.toString(produto.getEstoque())
				 +")");
				 connection.close();
				 result = 1;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
		return result;
	}

	public int delete() {
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
			 statement.executeUpdate("DELETE FROM produto WHERE codigo = \""
			 +produto.getCodigo()
			 +"\""
			 );
			 result = 1; 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
