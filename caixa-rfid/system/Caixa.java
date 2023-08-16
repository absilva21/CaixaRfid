package system;

import com.fazecast.jSerialComm.*;

public class Caixa {
	static SerialPort[] ports; 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("olá mundo");
		ports = SerialPort.getCommPorts();
		for(int i=0;i<ports.length;i++) {
			System.out.println(ports[i].getDescriptivePortName());
		}
	}

}
