package api;

import java.sql.*;
import server.HttpLayer;

public class CaixaDB implements IPersistente {
	
	private Caixa caixa;

	public CaixaDB(Caixa cx) {
		// TODO Auto-generated constructor stub
		this.caixa = cx;
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
			int acesso = 0;
			
			if(caixa.isAcesso()) {
				acesso = 1;
			}
			
			if(isUpdate) {
				statement.executeUpdate("UPDATE  caixa SET ip = \""
				+caixa.getIp()
				+"\" WHERE codigo = "
				+Integer.toString(caixa.getCodigo()));
				
				
				
				statement.executeUpdate("UPDATE usuario_caixa SET acesso = "
				+Integer.toString(acesso)
				+" WHERE caixa = "
				+Integer.toString(caixa.getCodigo()));
				result = 1;
				
			}else {
				Usuario usuario = new Usuario(0,caixa.getAuth(),2);
				int test = usuario.save(false);
				if(test==1) {
					statement.executeUpdate("INSERT INTO caixa(ip) VALUES (\""
							+caixa.getIp()
							+"\")");
					ResultSet rs = statement.executeQuery("SELECT MAX(codigo) FROM caixa");
					if(rs.next()) {
						statement.executeUpdate("INSERT INTO usuario_caixa(usuario,caixa,acesso) VALUES ("
					    +Integer.toString(usuario.getCodigo())
					    +","
					    +rs.getString(1)
					    +","
					    +Integer.toString(1)
					    +")");
						result = 1;
					}
				    
			    }
				
				if(test == 2) {
					result = 2;
				}
				
			
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int load() {
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
			ResultSet rs = statement.executeQuery("SELECT * FROM caixa WHERE codigo = "
			+Integer.toString(caixa.getCodigo()));
			
			if(rs.next()) {
				caixa.setIp(rs.getString(2));
				String codigo = rs.getString(1);
				 rs = statement.executeQuery("SELECT acesso FROM usuario_caixa WHERE caixa = "
				+ codigo);
				if(rs.next()) {
					if(rs.getInt(1)==1) {
						caixa.setAcesso(true);
					}else {
						caixa.setAcesso(false);
						
					}
					result = 1;
				}
				
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
		int result = 0;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (Connection connection = DriverManager.getConnection(HttpLayer.conn)){
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM caixa WHERE codigo = "
			+Integer.toString(caixa.getCodigo()));
			result = 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result ;
	}

}
