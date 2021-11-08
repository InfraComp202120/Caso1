package source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


public class ProtocoloCliente {

	
	public static  void procesar(BufferedReader stdIn, PrintWriter out, BufferedReader in) throws IOException{

		System.out.println("Bienvenido, escriba su identificador. (Números que inician en 0)");
		
		String idCliente = stdIn.readLine();
		int numCliente = Integer.parseInt(idCliente);
		
		out.println(idCliente);
		System.out.println(in.readLine());  //Recibe primer mensaje del repetidor
		String idMsj=""+(int) (Math.random()*10);
		
		
		//SIMETRICO
		if(!Cliente.tipoCifrado)
		{
			//Llave simetrica de C-R
			String keyCR = Cliente.keysS[numCliente];
			
			// Instancia de la clase simetrico
			Symmetric simetrico= new Symmetric();
			
			//Encripta el id del mensaje que se pedirá y lo envía a repetidor
			String msjEncriptado = simetrico.encrypt(idMsj, keyCR);
			out.println(msjEncriptado);
			System.out.println("El num del mensaje es "+idMsj+"  el encriptado es: "+msjEncriptado);
			
			// Recibe el mensaje de repetidor y  lo desencripta
			
			
			String msjEncriptadoCR = in.readLine();
			String msjDesencriptado = simetrico.decrypt(msjEncriptadoCR, keyCR); 
			
			System.out.println("El mensaje es: " + msjDesencriptado);
			
		}
		//ASIMETRICO
		else {
			System.out.println("hola");
		}
		
		
	}
	
	
	
}
