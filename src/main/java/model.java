import java.awt.List;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;

import interfaces.modelInterface;
import interfaces.observerInterface;

public class model implements modelInterface {

	private ArrayList<observerInterface> listaObservers;
	private int estadoJuego1;
	private int estadoJuego2;
	private int logicaJuego2;
	private int presionada0_juego2;
	private int presionada1_juego2;
	private int aciertosJuego2;
	private int desaciertosJuego2;
	private Timer timerjuego2;
	private int mostrarMenu;

	private int aleatorioJuego2[] = { 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5 };
	private int aleatorioJuego2_aux[] = new int[aleatorioJuego2.length];
	private int tiempoMaxJuego2 = 15000; // 15 segundos

	// juego1
	private ArrayList<Point> puntos_solution = new ArrayList<Point>();
	private ArrayList<Point> puntos_mezclados = new ArrayList<Point>();

	public model() {

		listaObservers = new ArrayList<observerInterface>();
		estadoJuego1 = 0;
		estadoJuego2 = 0;
		mostrarMenu = 1;

	}

	@Override
	public void registrarObserver(observerInterface o) {
		listaObservers.add(o);

	}

	@Override
	public void removeObserver(observerInterface o) {
		int i = listaObservers.indexOf(o);

		if (i >= 0) {
			listaObservers.remove(i);
		}

	}

	@Override
	public void notificarObservador() {

		System.out.println("notifico");

		for (int i = 0; i < listaObservers.size(); i++) {
			observerInterface observador = (observerInterface) listaObservers.get(i);
			observador.actualizar();
		}

	}

	@Override
	public void seleccionJuego(int i) {

		if (i == 1) {
			estadoJuego1 = 1;
			estadoJuego2 = 0;
			mostrarMenu = 0;
			System.out.println("Aprieto  rompe");
			inicioJuego1();

		} else if (i == 2) {
			estadoJuego1 = 0;
			estadoJuego2 = 1;
			mostrarMenu = 0;
			System.out.println("Aprieto  memoria");
			inicioJuego2();
		}

	}

	@Override
	public void inicioJuego1() {

		// Collections.shuffle(puntos_mezclados);
		Collections.swap(puntos_mezclados, 10, 11);

		System.out.println("puntos mezclados " + puntos_mezclados);
		// puntos_mezclados.add(new Point(3, 2)); // agregamos boton vacio al final

		estadoJuego1 = 1;

		notificarObservador();

	}

	@Override
	public void inicioJuego2() {

		// funcion que restea variables de juego2

		logicaJuego2 = 0;
		presionada0_juego2 = presionada1_juego2 = -1;
		aciertosJuego2 = 0;
		desaciertosJuego2 = 0;

		System.out.println("InicioJuego2");

		// creamos un arreglo con las variables 0,0,1,1 ... 5,5 // que son las imagens
		// 0,0,1,1, ... 5,5 // las mezclamos

		// aleatorioJuego2_aux = aleatorioJuego2.clone();

		ArrayList<Integer> arrayMezclado = new ArrayList<Integer>();
		for (int i = 0; i < aleatorioJuego2.length; i++) {
			arrayMezclado.add(aleatorioJuego2[i]);
		}

		Collections.shuffle(arrayMezclado);

		for (int i = 0; i < aleatorioJuego2.length; i++) {
			aleatorioJuego2_aux[i] = (int) arrayMezclado.get(i);
		}

		notificarObservador();

		// crea un solo retardo de 10000 para que las imagens aparezcan visibles durante
		// 2,5 segundos
		Timer timer = new Timer(2500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				estadoJuego2 = 2;
				System.out.println("Termino");
				notificarObservador();
				setTemporizador_juego2();
			}

		});

		timer.start();
		timer.setRepeats(false);

	}

	@Override
	public void finJuego1() { // TODO Auto-generated method stub

		estadoJuego1 = 0;
		estadoJuego2 = 0;
		mostrarMenu = 1;
		notificarObservador();

	}

	@Override
	public void finJuego2() {

		estadoJuego1 = 0;
		estadoJuego2 = 0;
		mostrarMenu = 1;
		notificarObservador();

	}

	@Override
	public void secuenciaJuego1(int boton) {

		System.out.println(boton);
		System.out.println(puntos_mezclados.get(boton));

		// tengo que buscar el boton point 3,2 que es el boton final
		int last_index = 0;

		for (int j = 0; j < puntos_mezclados.size(); j++) {

			Point point = puntos_mezclados.get(j);

			if (point.getX() == 3 && point.getY() == 2) {
				last_index = puntos_mezclados.indexOf(puntos_mezclados.get(j));
			}
		}

		System.out.println("ultimo boton " + last_index);
		// indice del boton en el arreglo es boton
		// chequeamos si el boton es contiguo al boton vacio

		if ((boton - 1 == last_index) || (boton + 1 == last_index) || (boton - 3 == last_index)
				|| (boton + 3 == last_index)) {
			Collections.swap(puntos_mezclados, boton, last_index);
		}

		estadoJuego1 = 2;
		notificarObservador();

		// chequear estado de juego

		if (puntos_solution.toString().contentEquals(puntos_mezclados.toString())) {
			// TERMINADO BIEN!!
			estadoJuego1 = 3;
			notificarObservador();

			finTemporizador_juego1(); //corremos un timer y luego mostramos menu principal

		}

	}

	@Override
	public void secuenciaJuego2(int i) {

		if (logicaJuego2 == 0) { // tecla 1

			presionada0_juego2 = i;
			estadoJuego2 = 3;

		} else {

			presionada1_juego2 = i;

			System.out.println(presionada0_juego2);
			System.out.println(presionada1_juego2);

			System.out.println(aleatorioJuego2_aux[presionada0_juego2]);
			System.out.println(aleatorioJuego2_aux[presionada1_juego2]);

			if (aleatorioJuego2_aux[presionada0_juego2] == aleatorioJuego2_aux[presionada1_juego2]) {

				aleatorioJuego2_aux[presionada0_juego2] = -1;
				aleatorioJuego2_aux[presionada1_juego2] = -1;

				aciertosJuego2 = aciertosJuego2 + 1;
				estadoJuego2 = 3;
				if (aciertosJuego2 == 6) {
					finTemporizador_juego2();
				}
				System.out.println("acierto: " + aciertosJuego2);
			} else {
				desaciertosJuego2 = desaciertosJuego2 + 1;
				estadoJuego2 = 4;
			}
		}

		logicaJuego2 = (logicaJuego2 + 1) % 2; // varia entre 0 y 1 para indicar si presiono y

		notificarObservador();

	}

	@Override
	public void finTemporizador_juego1() {

		System.out.println("Termino juego 1");

		// crea un solo retardo de 10000
		Timer timer = new Timer(5000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finJuego1();
			}

		});

		timer.start();
		timer.setRepeats(false);

	}

	@Override
	public void finTemporizador_juego2() {

		estadoJuego2 = 5;
		System.out.println("Termino juego 2");
		notificarObservador();

		// crea un solo retardo de 10000
		Timer timer = new Timer(5000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finJuego2();
			}

		});

		timer.start();
		timer.setRepeats(false);

	}

	@Override
	public void inicioEstadisticasJuego2() {

	}

	@Override
	public void setTemporizador_juego2() {

		System.out.println("Inicio Temporizador");

		timerjuego2 = new Timer(tiempoMaxJuego2, new ActionListener() { // cada segundo
			public void actionPerformed(ActionEvent e) {
				System.out.println("FINALIZO Temporizador");
				if (estadoJuego2 == 3 || estadoJuego2 == 4) {
					// PROMBLE ATERMINA Y ENTRA DOS VECE CONTROLAR!
					System.out.println("Finaliza el juego 2 ");
					finTemporizador_juego2();
				} else {

					System.out.println("temporizador en mdoel no termino ");
				}
			}

		});

		timerjuego2.start();
		timerjuego2.setRepeats(false);

	}

	public int getEstadoJuego1() {
		return estadoJuego1;
	}

	public int getEstadoJuego2() {
		return estadoJuego2;
	}

	public int[] getAleatorioJuego2_aux() {
		return aleatorioJuego2_aux;
	}

	public int getMostrarMenu() {
		return mostrarMenu;
	}

	public void setMostrarMenu(int mostrarMenu) {
		this.mostrarMenu = mostrarMenu;
	}

	@Override
	public int getAciertosJuego2() {
		return aciertosJuego2;
	}

	@Override
	public int getDesaciertosJuego2() {
		return desaciertosJuego2;
	}

	@Override
	public int getTiempoMaximoJuego2() {
		return tiempoMaxJuego2;
	}

	public ArrayList<Point> getPuntos_mezclados() {
		return puntos_mezclados;
	}

	@Override
	public ArrayList<Point> getVectorInicialJuego1() {

		for (int i = 0; i < 4; i++) { // filas 0 a 3
			for (int j = 0; j < 3; j++) { // columnas de 0 a 4
				// agregamos todos menos el ultimo 3,2 que siempre esta en el mismo lugar punto
				// que lo queremos vacio
				puntos_solution.add(new Point(i, j));
				puntos_mezclados.add(new Point(i, j));
			}

		}

		return puntos_mezclados;
	}

}
