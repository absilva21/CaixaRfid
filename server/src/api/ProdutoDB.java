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
	
	//retorna 1 se tiver concluido, 0 se não encontrar a linha, -1 se der erro
	public int load(){
		int result = 0;
		
		File csvDB = new File(System.getProperty("user.dir")+"\\src\\produto.csv");
		try {
			String linha = ""; 
			String pdtStr = "";
			Scanner csvLeitor = new Scanner(csvDB);
			while(csvLeitor.hasNext()) {
				System.out.println(linha);
				if(linha.startsWith(this.produto.getCodigo())){
					
					break;
				}else {
					linha = csvLeitor.nextLine();
				}
				
			}
			pdtStr = linha;
			csvLeitor.close();
			String[] objP = pdtStr.split(";");
		
		    this.produto.setCodigo(objP[0]);
		    this.produto.setDescricao(objP[1]);
		    this.produto.setValor(Double.parseDouble(objP[2]));
		    this.produto.setEstoque(Double.parseDouble(objP[3]));
			result = 1;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = -1;
		}
		return result;
	}
	//se isUpadate for verdadeiro ele salva o registro na mesma linha
	public int save(boolean isUpdate) {
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
