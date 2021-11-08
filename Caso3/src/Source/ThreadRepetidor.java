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

public class ThreadRepetidor extends Thread {
	
	
	private Socket socket;
	
	private int id;
	
	private long tiempoInicial;
	
	public ThreadRepetidor(Socket socket, int id, long tiempoInicial) {
		
		this.socket = socket;
		this.id=id;
		this.tiempoInicial=tiempoInicial;
		
	}
	
	public void run() {
		try {
			// Conexión de los flujos
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader (socket.getInputStream()) );
			
			// ACA VA LA PARTE DE ENVIO DE MENSAJES Y ESO
			
			ProtocoloRepetidor.procesar(writer, reader, tiempoInicial);
			
			
			// Cierre de los flujos y socket
			writer.close();
			reader.close();
			socket.close();
			
			
		}
		catch(IOException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
