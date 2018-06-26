package interfaces;

public interface timerInterface {
	
	void registrarObserver(observerInterface o);

	void removeObserver(observerInterface o);

	void notificarObservador();
	
	void iniciarTimer(int tiempoMaxJuego2);
	
	void  finalizarTimer();

	static timerInterface getInstance() {
		return null;
	}
	
}
