package Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ProtocoloRepetidor {

	
	
	public static void procesar(PrintWriter writer_cr, BufferedReader reader_cr) throws IOException{

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
			writer_rs.println(msjEncriptadoRS);
			
			
			
			
		}
		//ASIMETRICO
		else {
			
			
		}
		
		
	}
	
	
	
	
}
