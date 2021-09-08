package source;

public class Fregadero {

	/**
	 * Tama�o del fregadero en pares
	 */
	private int tamFregadero;

	/**
	 * Pares actualmente en el fregadero
	 */
	private int contadorPares;

	/**
	 * M�todo constructor de fregadero
	 * @param tamFregadero tama�o del fregadero
	 */
	public Fregadero(int tamFregadero) {
		this.tamFregadero=tamFregadero;
		contadorPares=0;
	}

	/**
	 * M�todo que recibe los cubiertos sucios dep�sitados por los Comensales
	 * @return True si se pudo almacenar el par de cubiertos, false de lo contrario.
	 */
	public synchronized boolean recibirCubiertos() { 

		if(contadorPares==tamFregadero)
			return false;

		contadorPares++;
		return true;

	}

	/**
	 * M�todo que entrega un par de cubiertos al lavaplatos.	
	 * @return true si se pudo entregar el par de cubiertos, false de lo contrario
	 */
	public synchronized boolean recogerCubiertos() { 

		if(contadorPares==0)
			return false;

		contadorPares--;
		return true;

	}





}
