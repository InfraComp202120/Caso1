package Source;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Repetidor extends Thread {

	
	static int numThreads = 0;
	
	public static final int PUERTO_R = 9999 ;

	public static final int PUERTO_S = 3400;
	
	public static final String DIR_SERVIDOR = "localhost";
	

	
	public final static String pathConfigCifrado = "./data/configCifrado.txt";
	
	/**
	 * Establece el tipo de cifrado que se manejará, 
	 * 0: Simetrico
	 * 1: Asimetrico
	 */
	public static boolean tipoCifrado;
	
	
	
	
	
	public Repetidor() {
		
	}
	
	
	
	public static void cargarConfiguraciones() throws NumberFormatException, IOException {
		
		File file = new File(pathConfigCifrado);
		BufferedReader br = new BufferedReader(new FileReader(file));

		tipoCifrado = Integer.parseInt(br.readLine())!=0;  
		
	}
	
	
	public static void main(String[] args) throws IOException{
		cargarConfiguraciones();
		System.out.println("------------------- INICIALIZANDO EL REPETIDOR -------------------");
		System.out.println("El tipo de cifrado establecido es: "+ (tipoCifrado?"Asimetrico":"Simetrico"));
		
		ServerSocket ss_r = null;
		boolean continuar = true;
		
		
		try {
			ss_r = new ServerSocket(PUERTO_R);

		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		
		
		while(continuar) {
			
			Socket socket=ss_r.accept();
			ThreadRepetidor threadDelegado =  new ThreadRepetidor(socket, numThreads);
			numThreads++;
			
			threadDelegado.start();
			
			 
		}
		ss_r.close();
		

	}

	
}
