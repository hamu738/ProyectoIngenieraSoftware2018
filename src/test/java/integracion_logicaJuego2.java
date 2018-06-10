import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import org.junit.Test;

import interfaces.modelInterface;
import interfaces.observerInterface;

public class integracion_logicaJuego2  implements observerInterface {

	private modelInterface modelo;
	private int aleatorio[];
	private ArrayList<Integer> estados;
	private ArrayList<Integer> estados_correctos;

	private Semaphore semaphore = new Semaphore(1, true);

	@Test
	public void test() throws InterruptedException{

		
		modelo = new model();
		controllerMenuPrincipal menuPrincipal = new controllerMenuPrincipal(modelo);
		controllerJuego1 controllerJuego1 = new controllerJuego1((model) modelo);
		controllerJuego2 controllerJuego2 = new controllerJuego2((model) modelo);
		
		modelo.registrarObserver(this);
		
		estados = new ArrayList<Integer>();
		estados_correctos = new ArrayList<Integer>();
		
		//pasamos del estado 1, 3, 5  //el 2 no se contabiliza por se parte de un swing timmer
		estados_correctos.add(1);
		estados_correctos.add(3);
		estados_correctos.add(5);

		
		semaphore = new Semaphore(1, true);

		controllerJuego2.seleccionJuego(2);
		semaphore.acquire(); // espero estado 1
		semaphore.acquire(); // espero estado 2

		
		int boton = 0; // de 0 a 5
		int logica = 0; // de 0 a 1
		int a_partir_de = -1;

		while (true) { // leemos el arreglo y presionamos botones de forma secuncial primero los 0
						// luego 1 luego los 2, etc.

			aleatorio = modelo.getAleatorioJuego2_aux(); //es lo que haria la vista
		//	System.out.println("ALEATORIO " + Arrays.toString(aleatorio));
			int posicion = find(aleatorio, boton, a_partir_de);
			a_partir_de = posicion;

			controllerJuego2.logicaJuego(posicion); //			modelo.secuenciaJuego2(posicion);
			logica = (logica + 1) % 2;
			if (logica == 0) {
				boton = boton + 1;
				a_partir_de = -1; // vuelvo a buscar "despues" de la posicion -1
			}

			if (boton == 6) {
				break;
			} else {
				semaphore = new Semaphore(1, true);
			}
		}

		System.out.println("Estados " + estados.toString()); 
		
		assertArrayEquals(estados_correctos.toArray(), estados.toArray()); 

	}

	@Override
	public void actualizar() {

				
		if (estados.isEmpty()) {			
			    System.out.println(modelo.getEstadoJuego2());
				estados.add(modelo.getEstadoJuego2());			
		} else {
			if (modelo.getEstadoJuego2() != estados.get(estados.size()-1)) {
				estados.add(modelo.getEstadoJuego2());
			}
		}  

		this.semaphore.release();

	}

	public int find(int[] array, int value, int a_partir_de) { // inicial -1

		for (int i = a_partir_de + 1; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}

}
