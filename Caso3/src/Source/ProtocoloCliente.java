package Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ProtocoloCliente {

	
	public static  void procesar(BufferedReader stdIn, PrintWriter out, BufferedReader in) throws IOException{

		System.out.println("Bienvenido, escriba su identificador. (Números que inician en 1)");
		
		String idCliente = stdIn.readLine();
		int numCliente = Integer.parseInt(idCliente);
		
		out.println(idCliente);
		System.out.println(in.readLine());  //Recibe primer mensaje del repetidor
		
		String idMensaje = stdIn.readLine();
		//SIMETRICO
		if(!Cliente.tipoCifrado)
		{
			String key = Repetidor.keysS[numCliente];
			Symmetric simetrico= new Symmetric();
			simetrico.setKey(key);

			System.out.println(key);
		}
		//ASIMETRICO
		else {
			System.out.println("hola");
		}
		
		
	}
	
	
	
}
