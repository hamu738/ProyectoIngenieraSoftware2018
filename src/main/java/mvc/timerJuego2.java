package mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import interfaces.observerInterface;
import interfaces.timerInterface;

public class timerJuego2 implements timerInterface {
	
	private static timerJuego2 uniqueInstance;
	
	private ArrayList<observerInterface> listaObservers;
	private Timer timer;

	private timerJuego2() {

		listaObservers = new ArrayList<observerInterface>();
	}
	
	static timerInterface getInstance() {

		if(uniqueInstance == null) {
			uniqueInstance = new timerJuego2();
		}
		
		return uniqueInstance;
			
	}


	public void iniciarTimer(int TiempoMaximo) {

		timer = new Timer(TiempoMaximo, new ActionListener() { // cada segundo
			public void actionPerformed(ActionEvent e) {
			
				finalizarTimer();
			}

		});

		timer.start();
		timer.setRepeats(false);

	}
	
	public void finalizarTimer() {
		
		notificarObservador();
				
					
		
	}
	
	public void terminarTimer() {
		
		try {
			timer.stop();	
		} catch (Exception e) {
		
		}
	
		
		
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

		// System.out.println("notifico");

		for (int i = 0; i < listaObservers.size(); i++) {
			observerInterface observador = (observerInterface) listaObservers.get(i);
			observador.actualizar();
		}

	}

}
