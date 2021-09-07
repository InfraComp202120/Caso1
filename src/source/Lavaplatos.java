package source;

public class Lavaplatos extends Thread{


	private static Mesa mesa;
	
	private static Fregadero fregadero;
	
	public Lavaplatos(Mesa mesa, Fregadero fregadero) {
		this.mesa=mesa;
		this.fregadero=fregadero;
	}
	
	
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
	
	
	
	public void lavarCubiertos() {
		
		long tLavando = (Math.round( (Math.random()+1))); //Duerme Tiempo aleatorio entre 1 y 2 segundos
		try {
			sleep(tLavando*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
