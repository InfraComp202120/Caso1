package source;

public class Lavaplatos extends Thread{


	/**
	 * Mesa donde se encuentran los comensales 
	 */
	private static Mesa mesa;

	/**
	 * Fregadero donde se ubican los cubiertos sucios
	 */
	private static Fregadero fregadero;

	public Lavaplatos(Mesa mesa, Fregadero fregadero) {
		this.mesa=mesa;
		this.fregadero=fregadero;
	}

	/**
	 * Método que inicia el ciclo de lavado del lavaplatos, intentando lavar pares de cubiertos en cada ciclo y 
	 * poniéndolos en la mesa.
	 */
	public void run(){

		while(true) {

			while(!fregadero.recogerCubiertos())  //Si es false no pudo recoger cubiertos y sigue en ciclo
			{
				yield();
			}

			lavarCubiertos();  // Duerme el thread entre 1 y 2 seg

			mesa.restaurarCubiertos(); //Devuelve los cubiertos a la mesa
			System.out.println("Devolvi 1 par de cubierto ");

		}

	}


	/**
	 * Lava los cubiertos durante un tiempo aleatorio entre 1 y 2 segundos 
	 */
	public void lavarCubiertos() {

		long tLavando = (Math.round( (Math.random()+1))); //Duerme Tiempo aleatorio entre 1 y 2 segundos
		try {
			sleep(tLavando*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}


}
