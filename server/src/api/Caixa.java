package api;


public class Caixa implements IPersistente {
	
	private int codigo;
	private String ip;
	private CaixaDB db;

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
		this.db = new CaixaDB(this);
	}
	
	public Caixa(int cod,String ip) {
		this.codigo = cod;
		this.ip = ip;
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