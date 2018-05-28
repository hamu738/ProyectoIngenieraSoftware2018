
public interface modelInterface 
{
	void registrarObservador(observerInterface o);
	void removerObservador(observerInterface o);
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
