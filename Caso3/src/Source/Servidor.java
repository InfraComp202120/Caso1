package Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	
	
	static int numThreads = 0;
	
	public static final int PUERTO_S = 3400 ;
	
	public Servidor() {
		
	}
	
	
	
	public static void main(String[] args) throws IOException{
		ServerSocket ss = null;
		boolean continuar = true;
		
		try {
			ss=new ServerSocket(PUERTO_S);
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		
		
		while(continuar) {
			
			Socket socket=ss.accept();
			ThreadServidor threadDelegado =  new ThreadServidor(socket, numThreads);
			numThreads++;
			
			threadDelegado.start();
			
			 
		}
		ss.close();
		

	}

}
