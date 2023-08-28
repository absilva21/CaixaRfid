package api;

public class CompraDB implements IPersistente {
	
	private Compra compra;
	
	public CompraDB(Compra c) {
		// TODO Auto-generated constructor stub
		this.compra = c;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
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
