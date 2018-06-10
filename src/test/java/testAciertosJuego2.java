import static org.junit.Assert.assertEquals;

import java.util.concurrent.Semaphore;

import org.junit.Test;

import interfaces.modelInterface;
import interfaces.observerInterface;

// acierta todos lo valores de forma correcta los aciertos deben ser 6

public class testAciertosJuego2 implements observerInterface {

	private modelInterface modelo;
	private int aleatorio[];

	private Semaphore semaphore = new Semaphore(1, true);

	@Test
	public void test() throws InterruptedException {

		modelo = new model();
		modelo.registrarObserver(this);

		semaphore = new Semaphore(1, true);

		modelo.seleccionJuego(2); // seleccionamos juego 2
		semaphore.acquire(); // espero estado 1
		semaphore.acquire(); // espero estado 2

		int boton = 0; // de 0 a 5
		int logica = 0; // de 0 a 1
		int a_partir_de = -1;

		while (true) { // leemos el arreglo y presionamos botones de forma secuncial primero los 0
						// luego 1 luego los 2, etc.

			aleatorio = modelo.getAleatorioJuego2_aux();
			int posicion = find(aleatorio, boton, a_partir_de);
			a_partir_de = posicion;

			modelo.secuenciaJuego2(posicion);
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
		
		assertEquals(modelo.getAciertosJuego2(), 6);
		
	}

	@Override
	public void actualizar() {

		// System.out.println("ESTADO " + modelo.getEstadoJuego2());
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
