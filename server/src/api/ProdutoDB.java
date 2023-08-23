package api;

//representa a classe que vai carregar ou persistir os dados
public class ProdutoDB {
	

	
	private Produto produto;

	public ProdutoDB(Produto p) {
		// TODO Auto-generated constructor stub
		this.produto = p;
	}
	
	public boolean load(){
		
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
