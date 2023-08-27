package api;

public class Produto implements IPersistente{
	private String codigo;
	private String descricao;
	private double valor;
	private double estoque;
	public double getEstoque() {
		return estoque;
	}

	public void setEstoque(double estoque) {
		this.estoque = estoque;
	}

	private ProdutoDB db;
	
	// representa a maniulção dos dados do produto
	
	public Produto(String cd){
		this.codigo = cd;
		this.descricao = "";
		this.valor = 0.0;
		this.db = new ProdutoDB(this);
		
	}
	
	public Produto(String cd,String desc,double vl, double es ) {
		// TODO Auto-generated constructor stub
		this.codigo = cd;
		this.descricao = desc;
		this.valor = vl;
		this.estoque = es;
		this.db = new ProdutoDB(this);
	}
	
	public int load() {
		return this.db.load();
	}
	
	public int save(boolean Update) {
		return this.db.save( Update);
		
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
