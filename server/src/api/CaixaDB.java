package api;

import java.sql.*;

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
		
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:"+System.getProperty("user.dir")+"\\src\\"+"dados.db")){
			Statement statement = connection.createStatement();
			if(isUpdate) {
				statement.executeUpdate("UPDATE  caixa SET ip = \""
				+caixa.getIp()
				+"\" WHERE codigo = "
				+Integer.toString(caixa.getCodigo()));
			}else {
				statement.executeUpdate("INSERT INTO caixa(ip) VALUES (\""
				+caixa.getIp()
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
		// TODO Auto-generated method stub
		int result = 0;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:"+System.getProperty("user.dir")+"\\src\\"+"dados.db")){
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM caixa WHERE codigo = "
			+Integer.toString(caixa.getCodigo()));
			
			if(rs.next()) {
				caixa.setIp(rs.getString(2));
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
		int result = 0;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:"+System.getProperty("user.dir")+"\\src\\"+"dados.db")){
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
