package system;

public class Produto{
	private String descricao;
	private String codEntiqueta;
	private double valor;
	
	public Produto(String des,String cod,double v ) {
		descricao = des;
		codEntiqueta = cod;
		valor = v;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCodEntiqueta() {
		return codEntiqueta;
	}

	public void setCodEntiqueta(String codEntiqueta) {
		this.codEntiqueta = codEntiqueta;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
	
}