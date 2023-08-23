package api;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//representa a classe que vai carregar ou persistir os dados
public class ProdutoDB implements IPersistente{
	

	//represente o objeto que será salvo ou carregado
	private Produto produto;

	public ProdutoDB(Produto p) {
		// TODO Auto-generated constructor stub
		this.produto = p;
	}
	
	//retorna 1 se tiver concluido o se der erro -1 se o arquivo estiver aberto
	public int load(){
		int result = 0;
		File csvDB = new File("produto.csv");
		try {
			Scanner csvLeitor = new Scanner(csvDB);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public int save() {
		int result = 0;
		return result;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
