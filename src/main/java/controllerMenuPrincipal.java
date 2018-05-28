
public class controllerMenuPrincipal implements controllerInterface{

	private modelInterface modelo;
	private vistaInterface vistaMenuPrincipal;
	
	public controllerMenuPrincipal(modelInterface modelo) {
		
		this.modelo = modelo;
		vistaMenuPrincipal = new vistaMenuPrincipal(this, modelo);
		vistaMenuPrincipal.crearVista();
		
	}
	
	@Override
	public void inicioJuego1() {
		
		modelo.inicioJuego1();
		
	}

	@Override
	public void inicioJuego2() {
		
		modelo.inicioJuego1();
		
	}

	@Override
	public void salirJuego() {
		// TODO Auto-generated method stub
		
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

}
