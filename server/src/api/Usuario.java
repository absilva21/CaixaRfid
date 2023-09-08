package api;

public class Usuario implements IPersistente {

	private int codigo;
	private String auth;
	private int tipo;
	private UsuarioDB db;
	
	
	
	public UsuarioDB getDb() {
		return db;
	}

	public void setDb(UsuarioDB db) {
		this.db = db;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	
	public Usuario(int cod) {
		// TODO Auto-generated constructor stub
		this.codigo = cod;
		this.auth = "";
		this.tipo = 0;
		this.db = new UsuarioDB(this);
		
	}
	
	public Usuario(int cod, String auth, int tp) {
		this.codigo = cod;
		this.auth = auth;
		this.tipo = tp;
		this.db = new UsuarioDB(this);
	}

	@Override
	public int save(boolean isUpdate) {
		// TODO Auto-generated method stub
		return this.db.save(isUpdate);
	}

	@Override
	public int load() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete() {
		// TODO Auto-generated method stub
		return 0;
	}

}
