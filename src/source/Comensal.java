package source;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Comensal extends Thread{

	private int contadorPlatos;
	
	private int maxPlatos;

	private int id;
	
	private static Mesa mesa;
	
	private static Fregadero fregadero;
	
	private static CyclicBarrier barrera;


	public Comensal( int maxPlatos, Mesa mesa, Fregadero fregadero, int id,  CyclicBarrier barrera)
	{
		this.maxPlatos=maxPlatos;
		contadorPlatos=0;
		
		this.mesa=mesa;
		this.id=id;
		this.barrera= barrera;
		this.fregadero= fregadero;
	}
	
	
	public void run() {
		
		while(contadorPlatos<maxPlatos)
		{
			System.out.println("Comensal "+id+" : comencé a comer mi plato número: "+(contadorPlatos+1)+". ");
			boolean tipo = (Math.round(Math.random())==1?true:false); // si tipo es false cubierto t1, true cubierto t2
			
			System.out.println("Comensal "+id+" : comencé a comer mi plato número: "+(contadorPlatos+1)+". \n"
					+ "	Intentaré agarrar un cubierto de tipo "+(tipo));
			while(true)
			{
				mesa.agarrarCubierto(tipo, false, id); //Agarra el primer cubierto
				if(mesa.agarrarCubierto(!tipo, true, id)) //Agarra el segundo cubierto
				{
					break;
				}
				tipo = !tipo;
			}
			
			
			
			comerPlato(); //Come el plato y suma 1 al contador de platos
		
			while(!fregadero.recibirCubiertos())  //El comensal intenta depositar sus cubiertos en fregadero
			{  
				System.out.println("Comensal :"+ id +"Fregadero lleno, me pauso");
				yield();
			}
			

			if(contadorPlatos==maxPlatos/2)
			{
				try {
					System.out.println("Soy el comensal: "+id+" y me dormí ");
					barrera.await();  //Duerme a los comensales hasta que todos lleguen a la mitad de los platos
					System.out.println("Soy el comensal: "+id+" y me desperté ");
				
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
		System.out.println("Comensal: "+id+" ya terminó sus platos");
	}
	
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
