package source;

public class Comensal extends Thread{

	private int contadorPlatos;
	
	private boolean cubiertoTipo1;
	
	private boolean cubiertoTipo2;
	
	

	public Comensal()
	{
		contadorPlatos=0;
		cubiertoTipo1=false;
		cubiertoTipo2=false;

	}
	
	
	public void run() {
		
	}
	
	
	
	/** 
	 * Puede ser de cualquier tipo, pero hay que tener en cuenta cuales ya tiene a disposición
	 */
	public synchronized void agarrarCubierto() {
		
		
		
	}
	
	
	
	/**
	 * @throws InterruptedException 
	 * 
	 */
	public synchronized void comerPlato() throws InterruptedException {
		long tComiendo = (long) (Math.random()*2+3); //Tiempo aleatorio entre 3 y 5 segundos
		sleep(tComiendo);
		contadorPlatos++;
		
	}
	
	
	public  synchronized void soltarCubiertos() {
		
	}
	
	
}
