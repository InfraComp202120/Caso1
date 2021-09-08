package source;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Comensal extends Thread{

	/**
	 * Contador de platos que ha comido el comensal
	 */
	private int contadorPlatos;
	
	/**
	 * N�mero de platos totales que comer� el comensal
	 */
	private int maxPlatos;

	/**
	 * Identificador �nico del comensal
	 */
	private int id;
	
	/**
	 * Mesa donde comen los comensales 
	 */
	private static Mesa mesa;
	
	/**
	 * Fregadero donde se dejan los cubiertos sucios
	 */
	private static Fregadero fregadero;
	
	/**
	 * Barrera para esperar a los dem�s comensales
	 */
	private static CyclicBarrier barrera;


	/**
	 * M�todo constructor del Comensal
	 * @param maxPlatos N�mero de platos que comer� el Comensal	
	 * @param mesa Mesa donde est�n los cubiertos
	 * @param fregadero ubicaci�n donde se dejar�n los cubiertos sucios
	 * @param id identificador �nico del Comensal
	 * @param barrera Barrera para esperar a los dem�s comensales
	 */
	public Comensal( int maxPlatos, Mesa mesa, Fregadero fregadero, int id,  CyclicBarrier barrera)
	{
		this.maxPlatos=maxPlatos;
		contadorPlatos=0;
		
		this.mesa=mesa;
		this.id=id;
		this.barrera= barrera;
		this.fregadero= fregadero;
	}
	
	/**
	 * M�todo que corre el Thread Comensal, hace ciclo para comer todos los platos y enviar los cubiertos sucios al fregadero.
	 */
	public void run() {
		
		while(contadorPlatos<maxPlatos)
		{
			boolean tipo = (Math.round(Math.random())==1?true:false); // si tipo es false cubierto t1, true cubierto t2
			
			System.out.println("Comensal "+id+" : comenc� a comer mi plato n�mero: "+(contadorPlatos+1)+". \n"
					+ "	Intentar� agarrar un cubierto de tipo "+(tipo?2:1)+" \n");
			while(true)
			{
				mesa.agarrarCubierto(tipo, false, id); //Agarra el primer cubierto
				if(mesa.agarrarCubierto(!tipo, true, id)) //Agarra el segundo cubierto
				{
					System.out.println("Comensal "+id+" : logr� agarrar mis dos cubiertos, procedo a comer el plato"+contadorPlatos+".");
					break;
				}
				tipo = !tipo;
				System.out.println("Comensal "+id+" : no pude agarrar el segundo cubierto que era de tipo: "+(tipo?1:0));
			}
			
			
			
			comerPlato(); //Come el plato y suma 1 al contador de platos
		
			while(!fregadero.recibirCubiertos())  //El comensal intenta depositar sus cubiertos en fregadero
			{  
				System.out.println("Comensal "+id+" : Intento entregar cubiertos pero el fregadero est� lleno, espero para volver a intentar.");
				yield();
			}
			
			
			if(contadorPlatos==maxPlatos/2)
			{
				try {
					
					System.out.println("Comensal "+id+" : Acab� el plato n�mero "+contadorPlatos+" espero a que los dem�s lleguen.");
					if(barrera.await()==0)  //Duerme a los comensales hasta que todos lleguen a la mitad de los platos
						System.out.println("\n --------------TODOS LOS COMENSALES LLEGARON AL PLATO "+contadorPlatos+" LA CENA CONTINUA ----------------\n");
				
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
			
			
			// Dormir antes del siguiente plato
			long tAntesDeOtro = (Math.round( (Math.random()*2+1))); //Tiempo aleatorio entre 1 y 3 segundos
			try {
				sleep(tAntesDeOtro*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		System.out.println("---Comensal "+id+" : ya termin� todos mis platos.---");
		
		try {
			if(barrera.await()==0)  //Duerme a los comensales hasta que todos lleguen a la mitad de los platos
				System.out.println("\n --------------TODOS LOS COMENSALES LLEGARON AL ULTIMO PLATO ("+contadorPlatos+") LA CENA FINALIZ� ----------------\n");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}

		
	}
	/**
	 * M�todo que representa el Comensal comiendo un plato durante un tiempo aleatorio entre 3 y 5 segundos
	 */
	public void comerPlato() {
		
		long tComiendo = (Math.round( (Math.random()*2+3))); //Duerme Tiempo aleatorio entre 3 y 5 segundos
		try {
			sleep(tComiendo*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		contadorPlatos++;
	}
	
	
	/**

	*/

}
