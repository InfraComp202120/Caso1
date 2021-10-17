package source;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Properties;

public class main {

	public final static String pathDatos = "./data/referencias8_16_75.txt";

	private static int numMarcos;


	private static int numPag;


	private static int numRef;

	private static int[] pgSec;

	private static String[] refSec;

	public static void cargarDatos() throws Exception {


		File file = new File(pathDatos);
		BufferedReader br = new BufferedReader(new FileReader(file));

		numMarcos = Integer.parseInt(br.readLine());
		numPag = Integer.parseInt(br.readLine());
		numRef = Integer.parseInt(br.readLine());

		pgSec = new int[numRef];
		refSec = new String[numRef];
		String linea;
		int i=0;
		while ((linea = br.readLine()) != null) {

			pgSec[i]=Integer.parseInt(linea.split(",")[0]);	
			refSec[i]= linea.split(",")[1];
			i++;
		}
	}




	public static void main(String[] args) {

		try {
			cargarDatos();
		} catch (Exception e) { }



	}

}
