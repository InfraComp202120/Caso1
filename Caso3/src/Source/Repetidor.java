package Source;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Repetidor extends Thread {

	
	static int numThreads = 0;
	
	public static final int PUERTO_R = 9999 ;
	
	public Repetidor() {
		
	}
	
	
	
	public static void main(String[] args) throws IOException{
		ServerSocket ss_r = null;
		boolean continuar = true;
		
		try {
			ss_r = new ServerSocket(PUERTO_R);
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		
		
		while(continuar) {
			
			Socket socket=ss_r.accept();
			ThreadRepetidor threadDelegado =  new ThreadRepetidor(socket, numThreads);
			numThreads++;
			
			threadDelegado.start();
			
			 
		}
		ss_r.close();
		

	}

	
}
