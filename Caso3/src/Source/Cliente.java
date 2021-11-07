package Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {

	public static final int PUERTO_R = 9999;
	
	public static final String DIR_SERVIDOR = "localhost";
	
	public static void main(String args[]) throws IOException{
		
		Socket socket = null;
		
		PrintWriter writer = null;
		BufferedReader reader = null;
		
		
		try {
			socket = new Socket (DIR_SERVIDOR, PUERTO_R);
			writer = new PrintWriter(socket.getOutputStream(),true);
			reader = new BufferedReader (new InputStreamReader (socket.getInputStream()));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		//BufferedReader stdIn =
		
		ProtocoloCliente.procesar(writer, reader);
		
		writer.close();
		reader.close();
		socket.close();
		
	}
	
	
	
}
