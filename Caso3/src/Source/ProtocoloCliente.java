package Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ProtocoloCliente {

	private static String idCliente;
	
	public static void procesar(BufferedReader stdIn, PrintWriter out, BufferedReader in) throws IOException{
		
		System.out.println("Bienvenido, escriba su identificador.");
		
		idCliente = stdIn.readLine();
		
		out.println(idCliente);
		System.out.println(in.readLine()+"\n    Escriba el identificador del mensaje que desea recibir. Debe ser un numero entre 00-09");  //Recibe primer mensaje del repetidor
		
		String idMensaje = stdIn.readLine();
		
	}
	
	
}
