import interfaces.controllerInterface;
import interfaces.modelInterface;
import interfaces.vistaInterface;

public class controllerJuego1 implements controllerInterface {
	
	private modelInterface modelo;
	private vistaInterface vistaJuego1;
	

	public controllerJuego1(model modelo) {
		this.modelo = modelo;
		vistaJuego1 = new vistaJuego1(this, modelo);
		vistaJuego1.crearVista();
	}

	@Override
	public void inicioJuego() {
				
		modelo.inicioJuego1();

	}


	@Override
	public void salirJuego() {


		modelo.finJuego1();

	}

	@Override
	public void logicaJuego(int i) {
		// TODO Auto-generated method stub
		modelo.secuenciaJuego1(i);

	}

	@Override
	public void tiempoLimite() {
		// TODO Auto-generated method stub

	}

	@Override
	public void iniciarPuntuacion() {
		// TODO Auto-generated method stub

	}

	@Override
	public void seleccionJuego(int i) {
		// TODO Auto-generated method stub
		
	}

}
