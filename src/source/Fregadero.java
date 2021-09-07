package source;

public class Fregadero {

	/**
	 * Tamaño del fregadero en pares
	 */
	private int tamFregadero;

	/**
	 * Pares actualmente en el fregadero
	 */
	private int contadorPares;
	
	
	public Fregadero(int tamFregadero) {
		this.tamFregadero=tamFregadero;
		contadorPares=0;
	}
	
	
	public synchronized boolean recibirCubiertos() { 
		/**boolean lavar = (Math.round(Math.random())==1?true:false);
		if (lavar && contadorPares>0) {
			contadorPares--;
			System.out.println("Se acaba de lavar un par, contador es: "+ contadorPares);
		}*/
		
		if(contadorPares==tamFregadero)
			return false;
		
		contadorPares++;
		return true;
		
		}
	
	
	
	
	
	public synchronized boolean recogerCubiertos() { 

		if(contadorPares==0)
			return false;
		
		contadorPares--;
		return true;
		
		}
	
	
	
	
	
}
