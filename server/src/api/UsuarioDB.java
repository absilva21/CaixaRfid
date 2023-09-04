package api;
import java.sql.*;

public class UsuarioDB implements IPersistente {
	
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioDB(Usuario us) {
		// TODO Auto-generated constructor stub
		this.usuario = us;
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
			if(isUpdate) {
				Statement statement = connection.createStatement();
				statement.executeUpdate("UPDATE usuario SET auth = \" "
						+this.usuario.getAuth()
						+"\",tipo = "
						+Integer.toString(this.usuario.getTipo()));
			}else {
				Statement statement = connection.createStatement();
				statement.executeUpdate("INSERT INTO usuario(auth,tipo) VALUES (\""
				+this.usuario.getAuth()
				+"\","
				+Integer.toString(this.usuario.getTipo())
				+")"
				);
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
			ResultSet rs = statement.executeQuery("SELECT * FROM usuario WHERE codigo = "
				      +Integer.toString(this.usuario.getCodigo()));
			if(rs.next()) {
				this.usuario.setAuth(rs.getString(2));
				this.usuario.setTipo(Integer.parseInt(rs.getString(3)));
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
			statement.execute("DELETE FROM usuario WHERE codigo = "
			+Integer.toString(this.usuario.getCodigo()));
			result = 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result;
	}

}
