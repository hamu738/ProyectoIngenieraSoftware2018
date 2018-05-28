package interfaces;

public interface modelInterface 
{
	void registrarObserver(observerInterface o);
	void removeObserver(observerInterface o);
	void notificarObservador();
	void inicioJuego1();
	void inicioJuego2();
	void finJuego();
	void secuenciaJuego1(String boton, int posicion);
	void secuenciaJuego2(String boton, int posicion);
	void finTemporizador();
	void inicioEstadisticasJuego2();
	int resetTemporizador();
}
