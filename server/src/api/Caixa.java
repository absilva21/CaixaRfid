package api;


public class Caixa implements IPersistente {
	
	private int codigo;
	private String ip;
	private boolean acesso;
	private String auth;
	private CaixaDB db;
	
	public boolean isAcesso() {
		return acesso;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public void setAcesso(boolean acesso) {
		this.acesso = acesso;
	}

	

	public CaixaDB getDb() {
		return db;
	}

	public void setDb(CaixaDB db) {
		this.db = db;
	}

	public Caixa(int cod) {
		// TODO Auto-generated constructor stub
		this.codigo = cod;
		this.ip = "";
		this.auth = "";
		this.acesso = false;
		this.db = new CaixaDB(this);
	}
	
	public Caixa(int cod,String ip, boolean acs,String auth) {
		this.codigo = cod;
		this.ip = ip;
		this.auth = auth;
		this.acesso = acs;
		this.db = new CaixaDB(this);
	}

	@Override
	public int save(boolean isUpdate) {
		// TODO Auto-generated method stub
		return this.db.save(isUpdate);
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public int load() {
		// TODO Auto-generated method stub
		return this.db.load();
	}

	@Override
	public int delete() {
		// TODO Auto-generated method stub
		return this.db.delete();
	}

}
