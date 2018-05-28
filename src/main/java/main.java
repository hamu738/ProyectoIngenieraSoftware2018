
public class main {

	public static void main(String[] args) 	{

		model modelo = new model();
		controllerMenuPrincipal menuPrincipal = new controllerMenuPrincipal(modelo);
		controllerJuego1 juego1 = new controllerJuego1(modelo);
		controllerJuego2 juego2 = new controllerJuego2(modelo);
	}
	
}
