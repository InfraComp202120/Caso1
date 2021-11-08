package Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ProtocoloServidor {

	public static void procesar(PrintWriter writer_rs, BufferedReader reader_rs) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, Exception{
		
		System.out.println(reader_rs.readLine());
		
		
		
		
		//SIMETRICO
		if(!Servidor.tipoCifrado)
		{
			// Llaves simetrica
			String keyRS  = Servidor.keyRS;
			//Instancia del encriptador
			Symmetric simetrico= new Symmetric();
			// Recibe y desencripta el mensaje encriptado
			String msjEncriptadoRS = reader_rs.readLine();
			String msjDesencriptadoRS = simetrico.decrypt(msjEncriptadoRS, keyRS); //Se almacena en idMsj el número del mensaje.
			System.out.println("Se recibió el mensaje encriptado: "+msjEncriptadoRS + " Se desencriptó con la llave correspondiente: "+msjDesencriptadoRS);
			
			// id del mensaje que se requiere
			int idMsj = Integer.parseInt(msjDesencriptadoRS);
			
			// Mensaje correspondiente al id  procede a encriptarlo 
			String msjSR = Servidor.mensajes[idMsj];
			String msjEncriptadoSR = simetrico.encrypt(msjSR, keyRS);
			writer_rs.println(msjEncriptadoSR);
			
			System.out.println("El mensaje correspondiente a ese identificador es: "+ msjSR +"  su encriptación es: "+ msjEncriptadoSR);
			
			
		}
		//ASIMETRICO
		else {
			// Llaves simetrica
			String keySPriv  = Servidor.keySPriv;
			String keyRPub = Servidor.keyRPub;
			//Instancia del encriptador
			Asymmetric asimetrico= new Asymmetric();
			// Recibe y desencripta el mensaje encriptado
			String msjEncriptadoRS = reader_rs.readLine();
			String msjDesencriptadoRS = asimetrico.decryptText(msjEncriptadoRS, asimetrico.getPrivate(keySPriv));  //Se almacena en idMsj el número del mensaje.
			
			System.out.println("Se recibió el mensaje encriptado: "+msjEncriptadoRS + " Se desencriptó con la llave correspondiente: "+msjDesencriptadoRS);
			
			// id del mensaje que se requiere
			int idMsj = Integer.parseInt(msjDesencriptadoRS);
			
			// Mensaje correspondiente al id  procede a encriptarlo 
			String msjSR = Servidor.mensajes[idMsj];
			
			String msjEncriptadoSR = asimetrico.encryptText(msjSR, asimetrico.getPublic(keyRPub));
			writer_rs.println(msjEncriptadoSR);
			
			System.out.println("El mensaje correspondiente a ese identificador es: "+ msjSR +"  su encriptación es: "+ msjEncriptadoSR);
			
			
			
		}
		
		
		
	}
	
	
}
