package admin;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Admin {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner teclado = new Scanner(System.in);
		String comando = "";
		String ipSever = "";
		String auth = "";
		System.out.println("digite o ip do servidor\n");
		ipSever = teclado.next();
		System.out.println("digite a chave do usuário\n");
		auth = teclado.next();
		
		MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        BigInteger hash = new BigInteger(1, md.digest(auth.getBytes()));
        
        while(true) {
        	System.out.println("Digite um comando:\n");
			System.out.println("1 - caixa\n 2 - histórico de compra");
			comando = teclado.next();
			
			
			
			if(comando.equals("1")) {
				System.out.println("Digite um comando:\n");
				System.out.println("1 - para bloquear ou liberar\n2 - para voltar ao menu anterior");
				comando = teclado.next();
			}
			
			if(comando.equals("2")) {
				System.out.println("Digite o código do caixa:\n");
				
				comando = teclado.next();
			}
			
        	if(comando.equals("x")) {
				System.exit(0);
			}
        }
		
		
	}

}
