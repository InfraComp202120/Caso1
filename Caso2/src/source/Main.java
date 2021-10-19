package source;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Properties;
import java.util.Scanner;

public class Main extends Thread {

	public boolean tipoT;

	public final static String pathDatos0 = "./data/referencias2_16_75.txt";
	public final static String pathDatos1 = "./data/referencias2_32_75.txt";
	public final static String pathDatos2 = "./data/referencias2_64_75.txt";
	public final static String pathDatos3 = "./data/referencias2_128_75.txt";

	//public final static String pathDatos = "./data/prueba0.txt";


	private static int numMarcos;


	private static int numPag;


	private static int numRef;

	private static int[] secPag;

	private static String[] secTipo;

	private static int[][] estadoPag;

	private static int[] marcos;

	private static int fallos;

	private static boolean enProceso;

	public Main(boolean tipoT) {

		this.tipoT=tipoT;
	}


	public void run() {

		if (tipoT) {
			run2();
		}
		else{
			run1();
		}
	}

	public void run1() {

		for (int i = 0; i <numRef; i++) {
			int pag = secPag[i];
			String tipo = secTipo[i];
			//System.out.println("La ref es: "+ pag+"  el tipo es  "+tipo);

			// TIPO R
			if(tipo.equalsIgnoreCase("r"))
			{
				procesoLectura(pag);
			}

			// TIPO M
			else {
				procesoLectura(pag); // Se hace el cambio por R al leer en caso de que sea necesario
				procesoEscritura(pag); // En caso de que no este previamente en modo escritura se considera el fallo
			}

			try {
				sleep(1); //Duerme 1 ms
			} catch (InterruptedException e) {}
		}


		enProceso=false;

		System.out.println("El numero de fallos fue: "+ fallos);
	}

	public void procesoLectura(int pag) {

		//  La p�gina NO ESTA EN MARCO
		if(!estaEnMarco(pag)) {

			// El Marco est� vacio
			if(meterEnVacio(pag)) {	
				fallos++;
				cambiarR(pag);
				//System.out.println("Se agrego un fallo por vacio lectura con la pg: "+pag);
			}

			// No hay vacio en el marco
			else {
				cambioEnMarco(pag);
			}
		}

		//  La p�gina s� est� en el marco
		else {
			enMarco(pag);
		}
	}

	public synchronized void procesoEscritura(int pag) {
		if(estadoPag[pag][2]==0) {
			estadoPag[pag][2]=1;
			//fallos++;
			//System.out.println("Se agrego un fallo por escritura con la pg: "+pag);
		}
	}
	public synchronized void enMarco(int pag) {

		if(estadoPag[pag][1]==0)
		{
			//fallos++;
			cambiarR(pag);
		}
	}

	public boolean estaEnMarco(int pag) {
		for (int m : marcos) {
			if(pag==m)
				return true;
		}
		return false;
	}

	public boolean meterEnVacio(int pag) {
		for (int i = 0; i < numMarcos; i++) {
			if(marcos[i]==-1) {
				marcos[i]=pag;
				return true;
			}
		}
		return false;
	}


	public synchronized void cambioEnMarco(int pag) {
		int indiceMenor=-1;
		int grupoMenor=-1;

		for (int i = 0; i < numMarcos; i++) {
			int m = marcos[i];
			int grupo = estadoPag[m][1]*2 + estadoPag[m][2];

			if (grupo==0) {
				indiceMenor=i;
				break;
			}
			else if(indiceMenor==-1 || grupoMenor>grupo)
			{
				indiceMenor=i;
				grupoMenor=grupo;
			}
		}
		int pagEliminada = marcos[indiceMenor];
		estadoPag[pagEliminada][2]=0;

		marcos[indiceMenor] = pag;
		if(estadoPag[pag][1]==0)
		{
			cambiarR(pag);
		}	
		fallos++;
		//System.out.println("Se agrego un fallo por cambioEnMarco lectura con la pg: "+pag+ "  se elimin� la pg "+pagEliminada);
	}


	public synchronized void cambiarR(int pag) {
		estadoPag[pag][1]=1;
	}

	/**
	public synchronized void cambiarM(int pag) {
		estadoPag[pag][2]=1;
	}
	 */



	/*
	 * Metodo de run para el thread 2 
	 */
	public void run2() {

		while(enProceso) {

			try {
				sleep(20); //Duerme 20 ms
			} catch (InterruptedException e) {}	
			limpiarR();
		}

	}


	public synchronized void limpiarR() {
		for (int i = 0; i <numMarcos; i++) {
			estadoPag[i][1]=0;
		}
		//System.out.println("---------------------LIMPIEZA-----------------");
	}


	public static void cargarDatos() throws Exception {

		Scanner sc = new Scanner(System.in);
		System.out.println("------- Bienvenido al simulador de Memoria Virtual -------");
		System.out.println("A continuaci�n elija el archivo que desea cargar para la configuraci�n del sistema: "
				+ "\n	0: referencias8_16"
				+ "\n	1: referencias8_32"
				+ "\n	2: referencias8_64"
				+ "\n	3: referencias8_128");
		int p = sc.nextInt();
		String pathDatos = ((p==0)?pathDatos0:(p==1)?pathDatos1:(p==2)?pathDatos2:pathDatos3);
		File file = new File(pathDatos);
		BufferedReader br = new BufferedReader(new FileReader(file));

		numMarcos = Integer.parseInt(br.readLine());
		numPag = Integer.parseInt(br.readLine());
		numRef = Integer.parseInt(br.readLine());

		secPag = new int[numRef];
		secTipo = new String[numRef];
		String linea;
		int i=0;
		while ((linea = br.readLine()) != null) {

			secPag[i]=Integer.parseInt(linea.split(",")[0]);	
			secTipo[i]= linea.split(",")[1];
			i++;
		}
	}




	public static void main(String[] args) {
		while(true) {
			enProceso=true;
			try {
				cargarDatos();
			} catch (Exception e) { }

			estadoPag = new int[numPag][3];
			for (int i = 0; i < numPag; i++) {
				estadoPag[i][1]=0;
				estadoPag[i][2]=0;
			}
			marcos = new int[numMarcos];
			for (int i = 0; i < marcos.length; i++) {
				marcos[i]=-1;
			}

			fallos=0;

			Main th1 = new Main(false);
			Main th2 = new Main(true);

			th1.start();
			th2.start();

		}
	}

}
