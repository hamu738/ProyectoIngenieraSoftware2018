import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

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
	private int mostrarMenu;

	
	private int aleatorioJuego2[] = {0,0,1,1,2,2,3,3,4,4,5,5} ;
	private int aleatorioJuego2_aux[] = new int[aleatorioJuego2.length] ;

	public model() {

		listaObservers = new ArrayList<observerInterface>();
		estadoJuego1 = 0;
		estadoJuego2 = 0;
		logicaJuego2 = 0;
		presionada0_juego2= presionada1_juego2= -1;
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
			notificarObservador(); //scar esto cuando se implemente 1

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
		//notificarObservador();

	}

	@Override
	public void inicioJuego2() {
		
		//  funcion que restea variables de juego2
		
		logicaJuego2 = 0;
		presionada0_juego2= presionada1_juego2= -1;
		aciertosJuego2 = 0;

		System.out.println("InicioJuego2");
		
		//creamos un arreglo con las variables 0,0,1,1 ... 5,5
		//que son las imagens 0,0,1,1, ... 5,5 
		//las mezclamos 
		
	//	aleatorioJuego2_aux = aleatorioJuego2.clone();
		
		ArrayList<Integer> arrayMezclado = new ArrayList<Integer>();
		for (int i = 0; i < aleatorioJuego2.length; i++) {
			arrayMezclado.add(aleatorioJuego2[i]);
		}
		
		Collections.shuffle(arrayMezclado);
		
		for (int i = 0; i < aleatorioJuego2.length; i++) {
			aleatorioJuego2_aux[i] = (int) arrayMezclado.get(i);
		}
		
		notificarObservador();
		

		
		

	}

	@Override
	public void finJuego1() {
		// TODO Auto-generated method stub

	}

	@Override
	public void finJuego2() {
		// TODO Auto-generated method stub
		estadoJuego1 = 0;
		estadoJuego2 = 0;
		mostrarMenu = 1;
		System.out.println("Termino juego 2");
		notificarObservador(); //scar esto cuando se implemente 1

	}

	@Override
	public void secuenciaJuego1() {
		// TODO Auto-generated method stub
		
		

	}

	@Override
	public void secuenciaJuego2(int i) {
		// TODO Auto-generated method stub

	
		switch (i) {
		case -1:
			estadoJuego2 = 2;
			break;

		default:
			
			  
			  if(logicaJuego2 == 0) { 
				 				
				  presionada0_juego2 = i;
				  estadoJuego2 = 3;			  
			  }
			  else { 
				  
				  presionada1_juego2 = i;
				  
				  System.out.println(presionada0_juego2);
				  System.out.println(presionada1_juego2);
				  
				  System.out.println(aleatorioJuego2_aux[presionada0_juego2]);
				  System.out.println(aleatorioJuego2_aux[presionada1_juego2]);
				  
				  if(aleatorioJuego2_aux[presionada0_juego2] == aleatorioJuego2_aux[presionada1_juego2]) {
					  
					  aleatorioJuego2_aux[presionada0_juego2] = -1;
					  aleatorioJuego2_aux[presionada1_juego2] = -1;
							  
					  aciertosJuego2 = aciertosJuego2 + 1;
					  estadoJuego2 = 3;
					  if(aciertosJuego2 == 6) {
						  estadoJuego2 = 0; //fin!
						  mostrarMenu = 1;
					  }
					  System.out.println("acierto: " + aciertosJuego2);
				  } else {					  
					  estadoJuego2 = 4;			    
				  }
			  }
			  
			  logicaJuego2 = (logicaJuego2 + 1) % 2; //varia entre 0 y 1	
				  	
			break;
		}
				
		notificarObservador();


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
	 * @param mostrarMenu the mostrarMenu to set
	 */
	public void setMostrarMenu(int mostrarMenu) {
		this.mostrarMenu = mostrarMenu;
	}

}
