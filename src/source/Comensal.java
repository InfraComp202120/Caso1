package source;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Comensal extends Thread{

	/**
	 * Contador de platos que ha comido el comensal
	 */
	private int contadorPlatos;
	
	/**
	 * Número de platos totales que comerá el comensal
	 */
	private int maxPlatos;

	/**
	 * Identificador único del comensal
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
	 * Barrera para esperar a los demás comensales
	 */
	private static CyclicBarrier barrera;


	/**
	 * Método constructor del Comensal
	 * @param maxPlatos Número de platos que comerá el Comensal	
	 * @param mesa Mesa donde están los cubiertos
	 * @param fregadero ubicación donde se dejarán los cubiertos sucios
	 * @param id identificador único del Comensal
	 * @param barrera Barrera para esperar a los demás comensales
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
	 * Método que corre el Thread Comensal, hace ciclo para comer todos los platos y enviar los cubiertos sucios al fregadero.
	 */
	public void run() {
		
		while(contadorPlatos<maxPlatos)
		{
			boolean tipo = (Math.round(Math.random())==1?true:false); // si tipo es false cubierto t1, true cubierto t2
			
			System.out.println("Comensal "+id+" : comencé a comer mi plato número: "+(contadorPlatos+1)+". \n"
					+ "	Intentaré agarrar un cubierto de tipo "+(tipo?2:1)+" \n");
			while(true)
			{
				mesa.agarrarCubierto(tipo, false, id); //Agarra el primer cubierto
				if(mesa.agarrarCubierto(!tipo, true, id)) //Agarra el segundo cubierto
				{
					System.out.println("Comensal "+id+" : logré agarrar mis dos cubiertos, procedo a comer el plato"+contadorPlatos+".");
					break;
				}
				tipo = !tipo;
				System.out.println("Comensal "+id+" : no pude agarrar el segundo cubierto que era de tipo: "+(tipo?1:0));
			}
			
			
			
			comerPlato(); //Come el plato y suma 1 al contador de platos
		
			while(!fregadero.recibirCubiertos())  //El comensal intenta depositar sus cubiertos en fregadero
			{  
				System.out.println("Comensal "+id+" : Intento entregar cubiertos pero el fregadero está lleno, espero para volver a intentar.");
				yield();
			}
			
			
			if(contadorPlatos==maxPlatos/2)
			{
				try {
					
					System.out.println("Comensal "+id+" : Acabé el plato número "+contadorPlatos+" espero a que los demás lleguen.");
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
		
		
		System.out.println("---Comensal "+id+" : ya terminé todos mis platos.---");
		
		try {
			if(barrera.await()==0)  //Duerme a los comensales hasta que todos lleguen a la mitad de los platos
				System.out.println("\n --------------TODOS LOS COMENSALES LLEGARON AL ULTIMO PLATO ("+contadorPlatos+") LA CENA FINALIZÓ ----------------\n");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}

		
	}
	/**
	 * Método que representa el Comensal comiendo un plato durante un tiempo aleatorio entre 3 y 5 segundos
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
