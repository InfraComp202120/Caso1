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
	 * Puede ser de cualquier tipo, pero hay que tener en cuenta cuales ya tiene a disposici�n
	 */
	public synchronized boolean agarrarCubierto( boolean tipo, boolean yaTengoUnCubierto, int id) {


		//System.out.println("Soy la mesa intentando agarrar un cubierto tipo "+ tipo+ " y hay T1: "+ cubiertosTipo1+"  T2: "+cubiertosTipo2);
		if(!tipo)  // Tipo false es cubierto tipo 1
		{
			while(cubiertosTipo1==0) {  // No tiene cubiertos TIPO 1

				if(yaTengoUnCubierto) { // Ya ten�a tipo 2 {
					cubiertosTipo2++;
					return false;
				}
				else  // Este es el primer cubiertos
				{ 
					try {
						System.out.println("Comensal "+id+": no hay cubiertos tipo 1, estoy a la espera.");
						wait();  //Duerme si no hay cubiertos tipo 1	
					}  
					catch (InterruptedException e) {} 				
				}
			} 
			cubiertosTipo1--;
			return true;
		}

		else {  // Agarrar el cubierto de tipo 2

			while(cubiertosTipo2==0) {  // No tiene cubiertos TIPO 2

				if(yaTengoUnCubierto) { // Ya ten�a tipo 1
					cubiertosTipo1++;
					return false;
				}

				else  // Este es el primer cubierto
				{ 
					try {
						System.out.println("Comensal "+id+": no hay cubiertos tipo 2, estoy a la espera.");
						wait();  //Duerme si no hay cubiertos tipo 2	
					}  
					catch (InterruptedException e) {} 				
				}
			} 
			cubiertosTipo2--;
			return true;
		}

	}



	public  synchronized void restaurarCubiertos() {

		cubiertosTipo1++;
		cubiertosTipo2++;  //cubiertos sucios
		notifyAll();
		System.out.println("Mesa: aviso para comensales, acaba de llegar un par de cubiertos."
				+ "\n	Ahora hay: "+ cubiertosTipo1+" cubiertos tipo 1  y "+cubiertosTipo2+" cubiertos tipo 2.\n ");





	}





}
