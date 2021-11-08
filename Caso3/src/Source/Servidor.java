package Source;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	
	
	static int numThreads = 0;
	
	public static final int PUERTO_S = 3400 ;
	
	/**
	 * Establece el tipo de cifrado que se manejará, 
	 * 0: Simetrico
	 * 1: Asimetrico
	 */
	public static boolean tipoCifrado;

	
	public static String keyRS;
	
	public static String[] mensajes;
 	
	public final static String pathConfigCifrado = "./data/configCifrado.txt";

	
	public final static String pathKeysSimetrico = "./data/keysS.txt";
	
	public final static String pathMensajes = "./data/mensajes.txt";
	

	public final static String pathKeysAsimetricoPub = "./data/KeyPair/publicKey.txt";

	public final static String pathKeysAsimetricoPriv = "./data/KeyPair/privateKey.txt";
	
	public static String keyRPub;

	public static String keySPriv;

	
	public static void cargarConfiguraciones() throws NumberFormatException, IOException {
		
		File file = new File(pathConfigCifrado);
		BufferedReader br = new BufferedReader(new FileReader(file));
		tipoCifrado = Integer.parseInt(br.readLine())!=0;  
		
		//LECTURA DE LOS MENSAJES
		mensajes = new String[10];
		file = new File(pathMensajes);
		br = new BufferedReader(new FileReader(file));
		
		for (int i = 0; i < 10; i++) {
			mensajes[i]=br.readLine();
		}
		
		
		//CASO SIMETRICO
		if(!tipoCifrado) {
			
			file = new File(pathKeysSimetrico);
			br = new BufferedReader(new FileReader(file));

			br.readLine();  
			keyRS = br.readLine();
			
		}
		//CASO ASIMETRICO
		else {
			file = new File(pathKeysAsimetricoPriv);
			br = new BufferedReader(new FileReader(file));
			br.readLine();
			keySPriv = br.readLine();
			
			file = new File(pathKeysAsimetricoPub);
			br = new BufferedReader(new FileReader(file));
			br.readLine();
			br.readLine();
			keyRPub = br.readLine();
		
			
		}
		
		
	}
	
	
	public static void main(String[] args) throws IOException{
		cargarConfiguraciones();
		
		System.out.println("------------------- INICIALIZANDO EL SERVIDOR PRINCIPAL -------------------");
		System.out.println("El tipo de cifrado establecido es: "+ (tipoCifrado?"Asimetrico":"Simetrico"));
		
		
		ServerSocket ss = null;
		boolean continuar = true;
		
		try {
			ss=new ServerSocket(PUERTO_S);
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		
		
		while(continuar) {
			
			Socket socket=ss.accept();
			ThreadServidor threadDelegado =  new ThreadServidor(socket, numThreads);
			numThreads++;
			
			threadDelegado.start();
			
			 
		}
		ss.close();
		

	}

}
