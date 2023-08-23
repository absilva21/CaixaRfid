package api;

public class Produto implements IPersistente{
	private String codigo;
	private String descricao;
	private double valor;
	private ProdutoDB db;
	
	// representa a maniulção dos dados do produto
	
	public Produto(String cd){
		this.codigo = cd;
		this.descricao = "";
		this.codigo = "";
		this.valor = 0.0;
		
		
	}
	
	public Produto(String cd,String desc,double vl ) {
		// TODO Auto-generated constructor stub
		this.codigo = cd;
		this.descricao = desc;
		this.valor = vl;
		this.db = new ProdutoDB(this);
	}
	
	public int load() {
		return db.load();
	}
	
	public int save() {
		return db.save();
		
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}
