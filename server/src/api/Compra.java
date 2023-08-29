package api;

import java.util.LinkedList;

public class Compra implements IPersistente {
	//codigo da compra
	private int codigo;
	//codigos dos produtos
	private  LinkedList<String> produtos;
	//codigo do caixa
	private int caixa;
	
	private CompraDB compraDB;
	
	public CompraDB getCompraDB() {
		return compraDB;
	}

	public void setCompraDB(CompraDB compraDB) {
		this.compraDB = compraDB;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}


	public LinkedList<String> getProdutos() {
		return produtos;
	}

	public void setProdutos(LinkedList<String> produtos) {
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
		this.compraDB = new CompraDB(this);
	}
	
	//para cadastro, o codigo é auto-incremento 
	public Compra( LinkedList<String> p, int cx ) {
		this.produtos = p;
		this.caixa = cx;
		this.compraDB = new CompraDB(this);
	}
	
	@Override
	public int save(boolean isUpdate) {
		// TODO Auto-generated method stub
		return this.compraDB.save(isUpdate);
	}

	@Override
	public int load() {
		// TODO Auto-generated method stub
		return this.compraDB.load();
	}

	@Override
	public int delete() {
		// TODO Auto-generated method stub
		return 0;
	}

}
