package Source;

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
		String idMsj="00";
		//SIMETRICO
		if(!Cliente.tipoCifrado)
		{
			String keyCR = Cliente.keysS[numCliente];
			Symmetric simetrico= new Symmetric();
			String msjEncriptado = simetrico.encrypt(idMsj, keyCR);
			System.out.println("El num del mensaje es "+idMsj+"  el encriptado es: "+msjEncriptado);
			
			out.println(msjEncriptado);
			
			
			
			System.out.println("El desencriptado es : "+simetrico.decrypt(msjEncriptado, keyCR));
		}
		//ASIMETRICO
		else {
			System.out.println("hola");
		}
		
		
	}
	
	
	
}
