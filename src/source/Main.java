package source;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.CyclicBarrier;

public class Main {

	public final static String pathDatos = "./data/data.properties";
	
	private static Mesa mesa;
	
	private static Fregadero fregadero;
	
	private static int numComensales;
	
	private static int numCubiertosT1;
	
	private static int numCubiertosT2;
	
	private static int numPlatos;
	
	private static int tamFregadero;

	
	
	
	
	public static void cargarDatos() throws Exception {

		Properties datos = new Properties( );
		FileInputStream in = new FileInputStream( pathDatos );
		try{
			datos.load( in );
			in.close( );
		}
		catch(Exception e){
			throw new Exception("Formato inválido.");
		}
		
		numComensales = Integer.parseInt(datos.getProperty("concurrencia.numComensales"));
		numCubiertosT1 = Integer.parseInt(datos.getProperty("concurrencia.numCubiertosT1"));
		numCubiertosT2 = Integer.parseInt(datos.getProperty("concurrencia.numCubiertosT2"));
		numPlatos = Integer.parseInt(datos.getProperty("concurrencia.numPlatos"));
		tamFregadero = Integer.parseInt(datos.getProperty("concurrencia.tamFregadero"));
		
	}
	
	
	
	public static void main(String[] args) throws Exception {
		
		cargarDatos();
		System.out.println(numComensales);
		
		mesa = new Mesa(numCubiertosT1, numCubiertosT2, numPlatos, numComensales);
		fregadero = new Fregadero(tamFregadero);

		Comensal[] comensales=new Comensal[numComensales];
		
		CyclicBarrier barrera = new CyclicBarrier(numComensales);
		
		Lavaplatos lavaplatos = new Lavaplatos(mesa,fregadero);
		lavaplatos.start();
		for (int i = 0; i < comensales.length; i++) {
			comensales[i]=new Comensal(numPlatos, mesa,fregadero,i,barrera);
			comensales[i].start();
			System.out.println("Corriendo thread:  "+i);
		}
		 
		
	}

}
