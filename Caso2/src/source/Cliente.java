package source;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.sun.tools.javac.code.Type.ForAll;

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
	

	
	
	public static String[] keysS;

	
	public final static String pathKeysSimetrico = "./data/keysS.txt";
	
	
	
	public static void cargarConfiguraciones() throws NumberFormatException, IOException {
		
		File file = new File(pathConfigCifrado);
		BufferedReader br = new BufferedReader(new FileReader(file));

		tipoCifrado = Integer.parseInt(br.readLine())!=0;  
		
		
		//CASO SIMETRICO
		if(!tipoCifrado) {
			
			file = new File(pathKeysSimetrico);
			br = new BufferedReader(new FileReader(file));
			int maxClientes = Integer.parseInt(br.readLine());  
			keysS= new String[maxClientes];
			br.readLine();
			
			for (int i = 0; i < maxClientes; i++) {
				keysS[i]=br.readLine();
			}
			
		}
		//CASO ASIMETRICO
		
		
	}
	
	public static void main(String args[]) throws IOException{
		cargarConfiguraciones();
		
		System.out.println("------------------- INICIALIZANDO EL CLIENTE PRINCIPAL-------------------");
		System.out.println("El tipo de cifrado establecido es: "+ (tipoCifrado?"Asimetrico":"Simetrico"));
		
		System.out.println("");
		
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
		
		
		for (int i = 0; i < numClientes; i++) {
			
		}
	}
	
	
	
	
}
