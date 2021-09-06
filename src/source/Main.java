package source;

import java.io.FileInputStream;
import java.util.Properties;

public class Main {

	public final static String pathDatos = "./data/data.properties";
	
	
	
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
		// TODO Auto-generated method stub
		cargarDatos();
		System.out.println(tamFregadero);
		

		
		
	}

}
