package interfaces;

public interface modelInterface {
	void registrarObserver(observerInterface o);

	void removeObserver(observerInterface o);

	void notificarObservador();

	void inicioJuego1();

	void inicioJuego2();

	void finJuego1();

	void finJuego2();

	void secuenciaJuego1();

	void secuenciaJuego2(int i);

	void finTemporizador();

	void inicioEstadisticasJuego2();

	int resetTemporizador();

	void seleccionJuego(int i);

	int getEstadoJuego1();

	int getEstadoJuego2();

	int getMostrarMenu();

	int[] getAleatorioJuego2_aux();

	
}
