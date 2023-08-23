package api;

//representa a classe que vai carregar ou persistir os dados
public class ProdutoDB {
	

	//represente o objeto que será salvo ou carregado
	private Produto produto;

	public ProdutoDB(Produto p) {
		// TODO Auto-generated constructor stub
		this.produto = p;
	}
	
	public boolean load(){
		File csvDB = new File("pro")
		return false;
	}
	
	public boolean save() {
		return false;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
