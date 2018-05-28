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
	//	((Window) vistaJuego2).setVisible(true);
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
	public void logicaJuego(String boton, int valor) {
		// TODO Auto-generated method stub

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
