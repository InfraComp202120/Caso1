package source;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.CyclicBarrier;

public class Main {

	/**
	 * Path del archivo con las properties para la carga inicial
	 */
	public final static String pathDatos = "./data/data.properties";
	
	/**
	 * Mesa donde se ubicar�n los comensales
	 */
	private static Mesa mesa;
	
	/**
	 * Fregadero donde se ubicar�n los cubiertos sucios
	 */
	private static Fregadero fregadero;
	
	/**
	 * N�mero de comensales en la mesa
	 */
	private static int numComensales;
	
	/**
	 * N�mero de cubiertos tipo 1 iniciales
	 */
	private static int numCubiertosT1;
	
	/**
	 * N�mero de cubiertos tipo 2 iniciales
	 */
	private static int numCubiertosT2;
	
	/**
	 * N�mero de platos que cada comensal comer�
	 */
	private static int numPlatos;
	
	/**
	 * N�mero m�ximo de pares de cubiertos que podr� contener el fregadero
	 */
	private static int tamFregadero;
	
	
	
	
	/**
	 * Carga los datos de un archivo properties en pathDatos
	 * @throws Exception Error en el formato del archivo properties
	 */
	public static void cargarDatos() throws Exception {

		Properties datos = new Properties( );
		FileInputStream in = new FileInputStream( pathDatos );
		try{
			datos.load( in );
			in.close( );
		}
		catch(Exception e){
			throw new Exception("Formato inv�lido.");
		}
		
		numComensales = Integer.parseInt(datos.getProperty("concurrencia.numComensales"));
		numCubiertosT1 = Integer.parseInt(datos.getProperty("concurrencia.numCubiertosT1"));
		numCubiertosT2 = Integer.parseInt(datos.getProperty("concurrencia.numCubiertosT2"));
		numPlatos = Integer.parseInt(datos.getProperty("concurrencia.numPlatos"));
		tamFregadero = Integer.parseInt(datos.getProperty("concurrencia.tamFregadero"));
		
	}
	
	
	/**
	 * M�todo de ejecuci�n del programa, inicializa los comensales, la barrera, el lavaplatos y el fregadero
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		cargarDatos();
		//System.out.println(numComensales);
		System.out.println("------------------INICIO DE LA CENA------------------\n");
		System.out.println("La cena inicia con: "+numComensales+" comensales, "+numPlatos+" platos para cada comensal,\n "
							+numCubiertosT1+" cubiertos de tipo 1, "+numCubiertosT2+" cubiertos de tipo 2 y un fregadero de tama�o "+tamFregadero +"\n");
		mesa = new Mesa(numCubiertosT1, numCubiertosT2, numPlatos, numComensales);
		fregadero = new Fregadero(tamFregadero);

		Comensal[] comensales=new Comensal[numComensales];
		
		CyclicBarrier barrera = new CyclicBarrier(numComensales);
		
		Lavaplatos lavaplatos = new Lavaplatos(mesa,fregadero);
		lavaplatos.start();
		for (int i = 0; i < comensales.length; i++) {
			comensales[i]=new Comensal(numPlatos, mesa,fregadero,i,barrera);
			comensales[i].start();
			System.out.println("Comensal "+i+" : Acabo de entrar a la mesa.");
		}
		 
		
	}

}
