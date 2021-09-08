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
					+ "	Intentaré agarrar un cubierto de tipo "+(tipo?1:0)+" \n");
			while(true)
			{
				mesa.agarrarCubierto(tipo, false, id); //Agarra el primer cubierto
				if(mesa.agarrarCubierto(!tipo, true, id)) //Agarra el segundo cubierto
				{
					System.out.println("Comensal "+id+" : logré agarrar mis dos cubiertos, procedo a comer el plato.");
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
