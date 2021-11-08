package Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ProtocoloRepetidor {

	private static long tiempoTotal1=0;

	private static long tiempoTotal2=0;
	
	private static int contadorClientes=0;
	
	public static void procesar(PrintWriter writer_cr, BufferedReader reader_cr, long tInicial1) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, Exception{

		Socket socket_rs = null;
		
		PrintWriter writer_rs = null;
		BufferedReader reader_rs = null;
		
		try {
			socket_rs = new Socket (Repetidor.DIR_SERVIDOR, Repetidor.PUERTO_S);
			writer_rs = new PrintWriter(socket_rs.getOutputStream(),true);
			reader_rs = new BufferedReader (new InputStreamReader (socket_rs.getInputStream()));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		
		String idCliente = reader_cr.readLine();
		int numCliente = Integer.parseInt(idCliente);
		System.out.println("Estableciendo conexión con el cliente "+idCliente);
		
		writer_cr.println("Repetidor: Bienvenido cliente "+idCliente + ".");
		
		
		writer_rs.println("Repetidor: Buenas noches, soy el repetidor delegado del cliente "+idCliente);
		
		long tFinal1, tInicial2, tFinal2;
		//SIMETRICO
		if(!Repetidor.tipoCifrado)
		{
			// Llaves simetricas
			String keyCR = Repetidor.keysS[numCliente];
			String keyRS  = Repetidor.keyRS;
			//Instancia del encriptador
			Symmetric simetrico= new Symmetric();
			// Recibe y desencripta el mensaje encriptado
			String msjClienteEncriptado = reader_cr.readLine();
			String idMsjClienteDes = simetrico.decrypt(msjClienteEncriptado, keyCR); //Se almacena en idMsj el número del mensaje.
			
			System.out.println("Se recibió el mensaje encriptado: "+msjClienteEncriptado + " Se desencriptó con la llave correspondiente: "+idMsjClienteDes);
		
			
			
			// Encripta y envía el mensaje al servidor con su llave
			String msjEncriptadoRS = simetrico.encrypt(idMsjClienteDes, keyRS);
			tFinal1=System.currentTimeMillis();
			writer_rs.println(msjEncriptadoRS);
			
			
			// RECIBE y DESENCRIPTA EL MENSAJE RECIBIDO DE SERVIDOR CON LLAVE RS
			String msjEncriptadoSR = reader_rs.readLine();
			tInicial2=System.currentTimeMillis();
			String msjDesencriptadoSR = simetrico.decrypt(msjEncriptadoSR, keyRS); 
			//System.out.println("Recibí");
			
			// ENCRIPTA Y ENVÍA EL MENSAJE A CLIENTE 
			String msjEncriptadoCR = simetrico.encrypt(msjDesencriptadoSR, keyCR);
			tFinal2=System.currentTimeMillis();
			writer_cr.println(msjEncriptadoCR);
		}
		//ASIMETRICO
		else {
			
			
			// Llaves asimetricas
			String keyCPub = Repetidor.keysAPub[numCliente];
			String keyRPriv  = Repetidor.keyRPriv;
			String keySPub = Repetidor.keySPub;
			
			//Instancia del encriptador
			Asymmetric asimetrico= new Asymmetric();
			
			// Recibe y desencripta el mensaje encriptado
			String msjClienteEncriptado = reader_cr.readLine();
			String idMsjClienteDes = asimetrico.decryptText(msjClienteEncriptado, asimetrico.getPrivate(keyRPriv)); //Se almacena en idMsj el número del mensaje.
			
			System.out.println("Se recibió el mensaje encriptado: "+msjClienteEncriptado + " Se desencriptó con la llave correspondiente: "+idMsjClienteDes);
			
			
			// Encripta y envía el mensaje al servidor con su llave
			String msjEncriptadoRS = asimetrico.encryptText(idMsjClienteDes, asimetrico.getPublic(keySPub));
			tFinal1=System.currentTimeMillis();
			writer_rs.println(msjEncriptadoRS);
			
			
			// RECIBE y DESENCRIPTA EL MENSAJE RECIBIDO DE SERVIDOR CON LLAVE RS
			String msjEncriptadoSR = reader_rs.readLine();
			tInicial2=System.currentTimeMillis();
			String msjDesencriptadoSR = asimetrico.decryptText(msjEncriptadoSR, asimetrico.getPrivate(keyRPriv));
			//System.out.println("Recibí");
			
			// ENCRIPTA Y ENVÍA EL MENSAJE A CLIENTE 
			String msjEncriptadoCR = asimetrico.encryptText(msjDesencriptadoSR, asimetrico.getPublic(keyCPub));
			tFinal2=System.currentTimeMillis();
			writer_cr.println(msjEncriptadoCR);
			
		}

		contadorClientes++;
		long tiempo1= tFinal1-tInicial1;
		long tiempo2= tFinal2-tInicial2;
		tiempoTotal1+=tiempo1;
		tiempoTotal2+=tiempo2;
		if(contadorClientes==Repetidor.maxClientes) 
			System.out.println("La suma total es: "+(tiempoTotal1+tiempoTotal2)
					+"\n El promedio es: "+ (tiempoTotal1+tiempoTotal2)/contadorClientes);
		
	}
	
	
	
	
}
