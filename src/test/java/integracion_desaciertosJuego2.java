import static org.junit.Assert.assertEquals;

import java.util.concurrent.Semaphore;

import org.junit.Test;

import interfaces.modelInterface;
import interfaces.observerInterface;

// acierta todos lo valores de forma correcta los aciertos deben ser 6

public class integracion_desaciertosJuego2 implements observerInterface {

	private modelInterface modelo;
	private int aleatorio[];
	

	private Semaphore semaphore = new Semaphore(1, true);

	@Test
	public void test() throws InterruptedException {

		modelo = new model();
		controllerMenuPrincipal menuPrincipal = new controllerMenuPrincipal(modelo);
		controllerJuego1 controllerJuego1 = new controllerJuego1((model) modelo);
		controllerJuego2 controllerJuego2 = new controllerJuego2((model) modelo);
		
		modelo.registrarObserver(this);

		semaphore = new Semaphore(1, true);

		controllerJuego2.seleccionJuego(2); // seleccionamos juego 2
		semaphore.acquire(); // espero estado 1
		semaphore.acquire(); // espero estado 2

		int logica = 0; // de 0 a 1
		int par_iteraciones = 10; //2*5 
		int pos_0 = 0;
		int pos_1 = 0;
		// int a_partir_de = -1;

		while (true) { // seleccioanamos dos botones de forma aleatoria, lo hacemos 5 veces para tener
						// 5 desaciertos

			aleatorio = modelo.getAleatorioJuego2_aux();

			if (logica == 0) {

				pos_0 = (int) (Math.random() * aleatorio.length); // numero de 0 a 11
				controllerJuego2.logicaJuego(pos_0);  //modelo.secuenciaJuego2(pos_0);
				
				System.out.println(" aleatorio.length: "  +  aleatorio.length);
				System.out.println("pos_0: "  + aleatorio[pos_0]);
				
				semaphore = new Semaphore(1, true); //espero actualizacion
				
			} else if (logica == 1) {

				while (true) { // buscamos dos valores distintos en el arreglo en el segundo click.

					pos_1 = (int) (Math.random() * aleatorio.length);
					if (aleatorio[pos_1] != aleatorio[pos_0])
						break;

				}
				
				System.out.println("pos_1: "  + aleatorio[pos_1]);

				controllerJuego2.logicaJuego(pos_1);  //modelo.secuenciaJuego2(pos_1);
				semaphore = new Semaphore(1, true); //espero actualizacion
			}

	

			logica = (logica + 1) % 2;
			par_iteraciones = par_iteraciones - 1;
			
			if(par_iteraciones == 0) break;
		}

		System.out.println("modelo.getDesaciertosJuego2(): "  + modelo.getDesaciertosJuego2());

		assertEquals(modelo.getDesaciertosJuego2(), 5);

	}

	@Override
	public void actualizar() {

		// System.out.println("ESTADO " + modelo.getEstadoJuego2());
		this.semaphore.release();

	}

}

