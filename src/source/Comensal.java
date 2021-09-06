package source;

public class Comensal extends Thread{

	private int contadorPlatos;
	
	private int maxPlatos;
	
	private boolean cubiertoTipo1;
	
	private boolean cubiertoTipo2;
	
	
	

	public Comensal( int maxPlatos)
	{
		this.maxPlatos=maxPlatos;
		contadorPlatos=0;
		cubiertoTipo1=false;
		cubiertoTipo2=false;
	}
	
	
	public void run() {
		
		while(contadorPlatos<maxPlatos)
		{
			
			
			//agarrarCubierto(); //Agarra el primer cubierto
			//agarrarCubierto(); //Agarra el segundo cubierto
			
			//comerPlato(); //Come el plato y suma 1 al contador de platos
		
			//soltarCubiertos(); //Libera ambos cubiertos
			
			
			
			// Dormir antes del siguiente plato
			long tAntesDeOtro = (long)((int) (Math.random()*2+1)); //Tiempo aleatorio entre 1 y 3 segundos
			try {
				sleep(tAntesDeOtro);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	/**
	long tComiendo = (long) (Math.random()*2+3); //Tiempo aleatorio entre 3 y 5 segundos
	try {
		sleep(tComiendo);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	contadorPlatos++;
	*/

}
