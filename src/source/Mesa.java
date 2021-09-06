package source;

public class Mesa {

	private int cubiertosTipo1;

	private int cubiertosTipo2;


	public Mesa(int maxCubiertosTipo1, int maxCubiertosTipo2) {

		this.cubiertosTipo1=maxCubiertosTipo1;

		this.cubiertosTipo2=maxCubiertosTipo2;

	}



	/** 
	 * Puede ser de cualquier tipo, pero hay que tener en cuenta cuales ya tiene a disposición
	 */
	public synchronized void agarrarCubierto( boolean tipo, boolean yaTengoUnCubierto) {


		if(!tipo)  // Tipo false es cubierto tipo 1
		{
			while(cubiertosTipo1==0) {  // No tiene cubiertos TIPO 1

				if(yaTengoUnCubierto) { // Ya tenía tipo 2 
					devolverAnteriorYSeguir(!tipo);
				}
				else  // Este es el primer cubierto
				{ 
					try {
						wait();  //Duerme si no hay cubiertos tipo 1	
					}  
					catch (InterruptedException e) {} 				
				}
			} 
			cubiertosTipo1--;
		}
		
		else {  // Agarrar el cubierto de tipo 2

			while(cubiertosTipo2==0) {  // No tiene cubiertos TIPO 2

				if(yaTengoUnCubierto) { // Ya tenía tipo 1
					devolverAnteriorYSeguir(!tipo); //Se devuelve el tipo 1 
				}

				else  // Este es el primer cubierto
				{ 
					try {
						wait();  //Duerme si no hay cubiertos tipo 2	
					}  
					catch (InterruptedException e) {} 				
				}
			} 
			cubiertosTipo2--;
		}

	}



	public synchronized void devolverAnteriorYSeguir(boolean tipoDevuelve) {

		if(tipoDevuelve) //Devuelve cubierto tipo 2 y cubierto de tipo 1 es el primero que debe agarrar ahora
		{
			cubiertosTipo2++; 
		}
		else //Devuelve cubierto tipo 1 y cubierto de tipo 2 es el primero que debe agarrar ahora
		{
			cubiertosTipo1++; 
		}
		
		agarrarCubierto(!tipoDevuelve, false); // Coge de primeras el cubierto contrario al que devolvio 
		agarrarCubierto(tipoDevuelve, true);  // Coge su segundo cubierto que sería aquel que devolvio



	}















	/**
	 * @throws InterruptedException 
	 * 
	 */
	public synchronized void comerPlato()  {



	}


	public  synchronized void soltarCubiertos() {

		notifyAll();
	}





}
