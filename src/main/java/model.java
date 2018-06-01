import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private int tiempo_actual_juego2;
	private int mostrarMenu;

	private int aleatorioJuego2[] = { 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5 };
	private int aleatorioJuego2_aux[] = new int[aleatorioJuego2.length];
	private int tiempoMaxJuego2 = 15000; // 15 segundos

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
			notificarObservador(); // scar esto cuando se implemente 1

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
		System.out.println("Aprieto  rompe");

		// aca funcio que resetea variables de juego1
		// notificarObservador();

	}

	@Override
	public void inicioJuego2() {

		// funcion que restea variables de juego2

		logicaJuego2 = 0;
		presionada0_juego2 = presionada1_juego2 = -1;
		aciertosJuego2 = 0;
		desaciertosJuego2 = 0;
		tiempo_actual_juego2 = 0;

		System.out.println("InicioJuego2");

		// creamos un arreglo con las variables 0,0,1,1 ... 5,5
		// que son las imagens 0,0,1,1, ... 5,5
		// las mezclamos

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

		// crea un solo retardo de 10000
		Timer timer = new Timer(2500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Termino");
				estadoJuego2 = 2;
				notificarObservador();
				setTemporizador_juego2();
			}

		});

		timer.start();
		timer.setRepeats(false);

	}

	@Override
	public void finJuego1() {
		// TODO Auto-generated method stub

	}

	@Override
	public void finJuego2() {
		
		estadoJuego1 = 0;
		estadoJuego2 = 0;
		mostrarMenu = 1;
		notificarObservador();
		
	}

	@Override
	public void secuenciaJuego1() {
		// TODO Auto-generated method stub

	}

	@Override
	public void secuenciaJuego2(int i) {
		// TODO Auto-generated method stub

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
					finTemporizador_juego2() ;
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
		

		timerjuego2 = new Timer(tiempoMaxJuego2, new ActionListener() { // cada un segundo
			public void actionPerformed(ActionEvent e) {
				System.out.println("FINALIZO Temporizador");
			//	if(estadoJuego2 == 3 || estadoJuego2 == 4) { //PROMBLE ATERMINA Y ENTRA DOS VECE CONTROLAR!
					 finTemporizador_juego2();
			//	}
			}

		});

		timerjuego2.start();
		timerjuego2.setRepeats(false);

	}

	/**
	 * @return the iniciarJuego1
	 */
	public int getEstadoJuego1() {
		return estadoJuego1;
	}

	/**
	 * @return the iniciarJuego2
	 */
	public int getEstadoJuego2() {
		return estadoJuego2;
	}

	public int[] getAleatorioJuego2_aux() {
		return aleatorioJuego2_aux;
	}

	/**
	 * @return the mostrarMenu
	 */
	public int getMostrarMenu() {
		return mostrarMenu;
	}

	/**
	 * @param mostrarMenu
	 *            the mostrarMenu to set
	 */
	public void setMostrarMenu(int mostrarMenu) {
		this.mostrarMenu = mostrarMenu;
	}

	/**
	 * @return the desaciertosJuego2
	 */
	@Override
	public int getAciertosJuego2() {
		return aciertosJuego2;
	}

	/**
	 * @return the desaciertosJuego2
	 */
	@Override
	public int getDesaciertosJuego2() {
		return desaciertosJuego2;
	}
	
	@Override
	public int getTiempoMaximoJuego2() {
		return tiempoMaxJuego2;
	}


}
