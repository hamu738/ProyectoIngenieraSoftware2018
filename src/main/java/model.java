import java.util.ArrayList;

import interfaces.modelInterface;
import interfaces.observerInterface;

public class model implements modelInterface {

	private ArrayList<observerInterface> listaObservers;
	private int iniciarJuego1;
	private int iniciarJuego2;
	private int mostrarMenu;

	public model() {

		listaObservers = new ArrayList<observerInterface>();
		iniciarJuego1 = 0;
		iniciarJuego2 = 0;
		setMostrarMenu(1);

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
		for (int i = 0; i < listaObservers.size(); i++) {
			observerInterface observador = (observerInterface) listaObservers.get(i);
			observador.actualizar();
		}

	}

	@Override
	public void seleccionJuego(int i) {

		if (i == 1) {
			iniciarJuego1 = 1;
			iniciarJuego2 = 0;
			mostrarMenu = 0;
			System.out.println("Aprieto  rompe");

		} else if (i == 2) {
			iniciarJuego1 = 0;
			iniciarJuego2 = 1;
			mostrarMenu = 0;
			System.out.println("Aprieto  memoria");
		}
		
		notificarObservador();

	}

	@Override
	public void inicioJuego1() {
		System.out.println("Aprieto  rompe");

		// aca funcio que resetea variables de juego1
		//notificarObservador();

	}

	@Override
	public void inicioJuego2() {
		System.out.println("Aprieto  memoria");

		// aca funcio que restea variables de juego2
		//notificarObservador();

	}

	@Override
	public void finJuego1() {
		// TODO Auto-generated method stub

	}

	@Override
	public void finJuego2() {
		// TODO Auto-generated method stub

	}

	@Override
	public void secuenciaJuego1(String boton, int posicion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void secuenciaJuego2(String boton, int posicion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void finTemporizador() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inicioEstadisticasJuego2() {
		// TODO Auto-generated method stub

	}

	@Override
	public int resetTemporizador() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @return the iniciarJuego1
	 */
	public int getIniciarJuego1() {
		return iniciarJuego1;
	}

	/**
	 * @param iniciarJuego1
	 *            the iniciarJuego1 to set
	 */
	public void setIniciarJuego1(int iniciarJuego1) {
		this.iniciarJuego1 = iniciarJuego1;
	}

	/**
	 * @return the iniciarJuego2
	 */
	public int getIniciarJuego2() {
		return iniciarJuego2;
	}

	/**
	 * @param iniciarJuego2
	 *            the iniciarJuego2 to set
	 */
	public void setIniciarJuego2(int iniciarJuego2) {
		this.iniciarJuego2 = iniciarJuego2;
	}

	/**
	 * @return the mostrarMenu
	 */
	public int getMostrarMenu() {
		return mostrarMenu;
	}

	/**
	 * @param mostrarMenu the mostrarMenu to set
	 */
	public void setMostrarMenu(int mostrarMenu) {
		this.mostrarMenu = mostrarMenu;
	}

}
