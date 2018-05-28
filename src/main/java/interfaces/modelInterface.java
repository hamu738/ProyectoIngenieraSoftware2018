package interfaces;

public interface modelInterface 
{
	void registrarObserver(observerInterface o);
	void removeObserver(observerInterface o);
	void notificarObservador();
	void inicioJuego1();
	void inicioJuego2();
	void finJuego1();
	void finJuego2();
	void secuenciaJuego1(String boton, int posicion);
	void secuenciaJuego2(String boton, int posicion);
	void finTemporizador();
	void inicioEstadisticasJuego2();
	int resetTemporizador();
	void seleccionJuego(int i);
	int getIniciarJuego1();
	int getIniciarJuego2();
	int getMostrarMenu();
}
