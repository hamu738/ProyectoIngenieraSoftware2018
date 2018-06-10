package mvc;
import interfaces.controllerInterface;
import interfaces.modelInterface;
import interfaces.vistaInterface;

public class controllerJuego2 implements controllerInterface {

	private modelInterface modelo;
	private vistaInterface vistaJuego2;

	public controllerJuego2(model modelo) {
		this.modelo = modelo;
		vistaJuego2 = new vistaJuego2(this, modelo);
		vistaJuego2.crearVista();
	}

	@Override
	public void inicioJuego() {

		modelo.inicioJuego2();

	}

	@Override
	public void salirJuego() {

		modelo.finJuego2();

	}

	@Override
	public void logicaJuego(int i) {
		modelo.secuenciaJuego2(i);

	}

	@Override
	public void seleccionJuego(int i) {
		// TODO Auto-generated method stub

	}

}