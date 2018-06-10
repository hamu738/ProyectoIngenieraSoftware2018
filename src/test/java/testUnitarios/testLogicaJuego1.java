package testUnitarios;
import static org.junit.Assert.assertArrayEquals;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Semaphore;
import org.junit.Test;
import interfaces.modelInterface;
import interfaces.observerInterface;
import mvc.model;

//prueba la logica del juego 1, que la transicion sea de los estados 0 1 2

public class testLogicaJuego1 implements observerInterface {

	private modelInterface modelo;
	private ArrayList<Integer> estados;
	private ArrayList<Integer> estados_correctos;

	private Semaphore semaphore = new Semaphore(1, true);

	@Test
	public void test() throws InterruptedException {

		//modelo = new model();
		
		
		/* creamos una clase modelo pero con una situacion en la que solo estan desordenadas las ultimas dos 
			imagenes, por la toanto cuando hagamos el swap el juego finaliza correctamente.
			Sobreescribimos las clase inicioJuego1*/
		modelo = new model() {
						
			@Override
			public void inicioJuego1() {
				Collections.swap(puntos_mezclados, 10, 11);
				estadoJuego1 = 1;
				notificarObservador();
			}
			
		};

		modelo.registrarObserver(this);

		estados = new ArrayList<Integer>();
		estados_correctos = new ArrayList<Integer>();

		// pasamos del estado 1, 2, 3
		estados_correctos.add(1);
		estados_correctos.add(2);
		estados_correctos.add(3);

		semaphore = new Semaphore(1, true);

		modelo.seleccionJuego(1); // seleccionamos juego 1
		semaphore.acquire(); // espero estado 1

		/*
		 * creamos una situacion en la que solo el ultimo boton se mueve para completar
		 * el juego
		 */

		modelo.secuenciaJuego1(11);

		semaphore = new Semaphore(1, true); // espero estado 2
		semaphore = new Semaphore(1, true); // espero estado 3

	//	System.out.println("Estados " + estados.toString());

		assertArrayEquals(estados_correctos.toArray(), estados.toArray());

	}

	@Override
	public void actualizar() {


		if (estados.isEmpty()) {
			estados.add(modelo.getEstadoJuego1());
		} else {
			if (modelo.getEstadoJuego1() != estados.get(estados.size() - 1)) { // agregamos estados nuevos
				estados.add(modelo.getEstadoJuego1());
			}
		}

		this.semaphore.release();

	}

}