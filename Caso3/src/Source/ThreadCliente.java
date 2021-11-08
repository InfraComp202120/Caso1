package Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadCliente extends Thread {
	
	
	private int id;
	
	public ThreadCliente(int id) {
		
		this.id=id;
		
		
	}
	
	public void run() {
		try {
			
			Socket socket = null;
			
			PrintWriter writer = null;
			BufferedReader reader = null;
			
			
			try {
				socket = new Socket (Cliente.DIR_SERVIDOR, Cliente.PUERTO_R);
				writer = new PrintWriter(socket.getOutputStream(),true);
				reader = new BufferedReader (new InputStreamReader (socket.getInputStream()));
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			
			ProtocoloCliente.procesar(stdIn,writer, reader, id);
			
			writer.close();
			reader.close();
			socket.close();
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	

}
