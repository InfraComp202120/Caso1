package Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ProtocoloServidor {

	public static void procesar(PrintWriter writer_rs, BufferedReader reader_rs) throws IOException{
		
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
			
			
		}
		
		
		
	}
	
	
}
