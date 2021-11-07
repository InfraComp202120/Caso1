package Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ProtocoloRepetidor {

	
	
	public static void procesar(PrintWriter writer, BufferedReader reader) throws IOException{

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
		
		
		String idCliente=reader.readLine();
		System.out.println("Estableciendo conexión con el cliente "+idCliente);
		writer.println("Repetidor: Bienvenido cliente "+idCliente + ".");
		writer.println("	Cifre y escriba el identificador del mensaje que enviará.");
		
		
		writer_rs.println("Repetidor: Buenas noches, soy el repetidor delegado del cliente "+idCliente);
		
		
		
	}
	
	
	
	
}
