package Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class ProtocoloCliente {

	
	public static  void procesar(BufferedReader stdIn, PrintWriter out, BufferedReader in, int numCliente) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, Exception{

		System.out.println("Bienvenido cliente número: "+ numCliente);
		
		out.println(numCliente);
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
			
			//Llave simetrica de C-R
			String keyCPriv = Cliente.keysCPriv[numCliente];
			String keyRPub = Cliente.keyRPub;
			
			// Instancia de la clase asimetrico
			Asymmetric asimetrico= new Asymmetric();
			
			//Encripta el id del mensaje que se pedirá y lo envía a repetidor
			String msjEncriptado = asimetrico.encryptText(idMsj, asimetrico.getPublic(keyRPub));
			out.println(msjEncriptado);
			System.out.println("El num del mensaje es "+idMsj+"  el encriptado es: "+msjEncriptado);
			
			// Recibe el mensaje de repetidor y  lo desencripta
			
			
			String msjEncriptadoCR = in.readLine();
			String msjDesencriptado = asimetrico.decryptText(msjEncriptadoCR, asimetrico.getPrivate(keyCPriv)); 
			
			System.out.println("El mensaje es: " + msjDesencriptado);
		}
		
		
	}
	
	
	
}
