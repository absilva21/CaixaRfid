package system;

import com.fazecast.jSerialComm.*;

import java.io.IOException;
import java.net.*;

public class Caixa {
	static SerialPort[] ports; 
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Ol�, selecione a porta serial do leitor:\n");
		ports = SerialPort.getCommPorts();
		for(int i=0;i<ports.length;i++) {
			System.out.println(ports[i].getDescriptivePortName()+"\n");
		}
	}

}
