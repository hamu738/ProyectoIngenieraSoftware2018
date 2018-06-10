package mvc;

import java.awt.Window;

import interfaces.controllerInterface;
import interfaces.modelInterface;
import interfaces.vistaInterface;

public class controllerMenuPrincipal implements controllerInterface {

	private modelInterface modelo;
	private vistaInterface vistaMenuPrincipal;

	public controllerMenuPrincipal(modelInterface modelo) {

		this.modelo = modelo;
		vistaMenuPrincipal = new vistaMenuPrincipal(this, modelo);
		vistaMenuPrincipal.crearVista();
		((Window) vistaMenuPrincipal).setVisible(true);

	}

	@Override
	public void seleccionJuego(int i) {

		modelo.seleccionJuego(i);

	}

	@Override
	public void inicioJuego() {

	}

	@Override
	public void salirJuego() {
		// TODO Auto-generated method stub

	}

	@Override
	public void logicaJuego(int i) {
		// TODO Auto-generated method stub

	}

}
