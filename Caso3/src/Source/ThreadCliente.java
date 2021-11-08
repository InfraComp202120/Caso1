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
