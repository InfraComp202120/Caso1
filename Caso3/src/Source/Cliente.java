package Source;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {

	public static final int PUERTO_R = 9999;
	
	public static final String DIR_SERVIDOR = "localhost";
	
	public final static String pathConfigCifrado = "./data/configCifrado.txt";
	
	/**
	 * Establece el tipo de cifrado que se manejará, 
	 * 0: Simetrico
	 * 1: Asimetrico
	 */
	public static boolean tipoCifrado;
	
	
	
	
	public static void cargarConfiguraciones() throws NumberFormatException, IOException {
		
		File file = new File(pathConfigCifrado);
		BufferedReader br = new BufferedReader(new FileReader(file));

		tipoCifrado = Integer.parseInt(br.readLine())!=0;  
		
	}
	
	public static void main(String args[]) throws IOException{
		cargarConfiguraciones();
		
		System.out.println("------------------- INICIALIZANDO EL CLIENTE -------------------");
		System.out.println("El tipo de cifrado establecido es: "+ (tipoCifrado?"Asimetrico":"Simetrico"));
		Socket socket = null;
		
		PrintWriter writer = null;
		BufferedReader reader = null;
		
		
		try {
			socket = new Socket (DIR_SERVIDOR, PUERTO_R);
			writer = new PrintWriter(socket.getOutputStream(),true);
			reader = new BufferedReader (new InputStreamReader (socket.getInputStream()));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		
		ProtocoloCliente.procesar(stdIn,writer, reader);
		
		writer.close();
		reader.close();
		socket.close();
		
	}
	
	
	
}
