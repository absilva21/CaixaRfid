package api;

public class Compra implements IPersistente {
	//codigo da compra
	private int codigo;
	//codigos dos produtos
	private String[] produtos;
	//codigo do caixa
	private int caixa;
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String[] getProdutos() {
		return produtos;
	}

	public void setProdutos(String[] produtos) {
		this.produtos = produtos;
	}

	public int getCaixa() {
		return caixa;
	}

	public void setCaixa(int caixa) {
		this.caixa = caixa;
	}

  //construtor para fazer consulta, carregamento e exclusão
	public Compra(int cod) {
		// TODO Auto-generated constructor stub
		this.codigo = cod;
		this.caixa = 0;
		this.produtos = null;
	}
	
	//para cadastro, o codigo é auto-incremento 
	public Compra(String[] p, int cx ) {
		this.produtos = p;
		this.caixa = cx;
	}
	
	@Override
	public int save(boolean isUpdate) {
		// TODO Auto-generated method stub
		return 0;
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
