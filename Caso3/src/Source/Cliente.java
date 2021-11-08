package Source;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Scanner;


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
	
	public static String keyRPub;
	
	public static String[] keysCPriv;

	
	public final static String pathKeysSimetrico = "./data/keysS.txt";
	
	public final static String pathKeysAsimetricoPub = "./data/KeyPair/publicKey.txt";

	public final static String pathKeysAsimetricoPriv = "./data/KeyPair/privateKey.txt";
	
	
	
	public static void cargarConfiguraciones(int numClientes) throws NumberFormatException, IOException {
		
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
		else {
			file = new File(pathKeysAsimetricoPub);
			br = new BufferedReader(new FileReader(file));
			int maxClientes = Integer.parseInt(br.readLine());
			br.readLine();
			keyRPub = br.readLine();
			
			
			file = new File(pathKeysAsimetricoPriv);
			br = new BufferedReader(new FileReader(file));
			br.readLine();
			br.readLine();
			br.readLine();
			keysCPriv= new String[numClientes];
			for (int i = 0; i < numClientes; i++) {
				keysCPriv[i]=br.readLine();
			}
		}
		
		
		
	}
	
	public static void main(String args[]) throws IOException, NoSuchAlgorithmException, NoSuchProviderException{

		
		System.out.println("------------------- INICIALIZANDO EL CLIENTE PRINCIPAL-------------------");
		System.out.println("El tipo de cifrado establecido es: "+ (tipoCifrado?"Asimetrico":"Simetrico"));
		
		System.out.println("Digite el número de clientes que desea para el programa: ");
		Scanner sc = new Scanner(System.in);
		int numClientes = sc.nextInt();
		KeyGen keygen = new KeyGen(1024, numClientes);
		
		System.out.println("Ejecute el servidor y el repetidor y escriba 1 para continuar:");
		sc.nextLine();
		
		cargarConfiguraciones(numClientes);
		
		for (int i = 0; i < numClientes; i++) {
			ThreadCliente threadC =  new ThreadCliente(i);
			threadC.start();
			
		}
	}
	
	
	
	
}
