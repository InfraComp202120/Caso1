package source;

import java.util.concurrent.BrokenBarrierException;

public class Mesa {

	private int cubiertosTipo1;

	private int cubiertosTipo2;

	private int mitadPlatos;
	
	

	public Mesa(int maxCubiertosTipo1, int maxCubiertosTipo2, int maxPlatos, int numComensales) {

		this.cubiertosTipo1=maxCubiertosTipo1;
		this.cubiertosTipo2=maxCubiertosTipo2;
		mitadPlatos=maxPlatos/2;
		
	}



	/** 
	 * Puede ser de cualquier tipo, pero hay que tener en cuenta cuales ya tiene a disposición
	 */
	public synchronized void agarrarCubierto( boolean tipo, boolean yaTengoUnCubierto) {
		

		System.out.println("Soy la mesa intentando agarrar un cubierto tipo "+ tipo+ "y hay T1: "+ cubiertosTipo1+"  T2: "+cubiertosTipo2);
		if(!tipo)  // Tipo false es cubierto tipo 1
		{
			while(cubiertosTipo1==0) {  // No tiene cubiertos TIPO 1

				if(yaTengoUnCubierto) { // Ya tenía tipo 2 
					devolverAnteriorYSeguir(true);  //Quiere devolver tipo 2
				}
				else  // Este es el primer cubierto
				{ 
					try {
						System.out.println("Entrando a dormir porque no hay cubiertos tipo 1");
						wait();  //Duerme si no hay cubiertos tipo 1	

						System.out.println("Me despertó el notifyAll");
					}  
					catch (InterruptedException e) {} 				
				}
			} 
			cubiertosTipo1--;
		}
		
		else {  // Agarrar el cubierto de tipo 2

			while(cubiertosTipo2==0) {  // No tiene cubiertos TIPO 2

				if(yaTengoUnCubierto) { // Ya tenía tipo 1
					devolverAnteriorYSeguir(false); //Se devuelve el tipo 1 
				}

				else  // Este es el primer cubierto
				{ 
					try {
						System.out.println("Entrando a dormir porque no hay cubiertos tipo 2");
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




	public  synchronized void restaurarCubiertos() {
		
		cubiertosTipo1++;
		cubiertosTipo2++;  //cubiertos sucios
		notifyAll();
		System.out.println("Soy la mesa y hay T1: "+ cubiertosTipo1+"  T2: "+cubiertosTipo2);
		
		
		
		
		
	}





}
