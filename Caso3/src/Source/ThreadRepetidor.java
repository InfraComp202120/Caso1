package Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadRepetidor extends Thread {
	
	
	private Socket socket;
	
	private int id;
	
	public ThreadRepetidor(Socket socket, int id) {
		
		this.socket = socket;
		this.id=id;
		
		
	}
	
	public void run() {
		try {
			// Conexión de los flujos
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader (socket.getInputStream()) );
			
			// ACA VA LA PARTE DE ENVIO DE MENSAJES Y ESO
			
			ProtocoloRepetidor.procesar(writer, reader);
			
			
			// Cierre de los flujos y socket
			writer.close();
			reader.close();
			socket.close();
			
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	

}
