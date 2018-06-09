package interfaces;

import java.awt.Point;
import java.util.ArrayList;

public interface modelInterface {
	void registrarObserver(observerInterface o);

	void removeObserver(observerInterface o);

	void notificarObservador();

	void inicioJuego1();

	void inicioJuego2();

	void finJuego1();

	void finJuego2();

	void secuenciaJuego1(int i);
	
	void secuenciaJuego2(int i);

	void finTemporizador_juego2();

	void inicioEstadisticasJuego2();

	void setTemporizador_juego2();

	void seleccionJuego(int i);

	int getEstadoJuego1();

	int getEstadoJuego2();

	int getMostrarMenu();

	int[] getAleatorioJuego2_aux();

	int getAciertosJuego2();
	
	int getDesaciertosJuego2();

	int getTiempoMaximoJuego2();
	
	ArrayList<Point> getPuntos_mezclados();

	ArrayList<Point> getVectorInicialJuego1();

	void finTemporizador_juego1();



} 
