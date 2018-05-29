

import javax.swing.JFrame;
import javax.swing.JPanel;

import interfaces.modelInterface;
import interfaces.observerInterface;
import interfaces.vistaInterface;


import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

public class vistaJuego1 extends JFrame implements vistaInterface, observerInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private modelInterface modelo;
	private controllerJuego1 controller;
	private JPanel contentPane;
	private JTextField txtSoyJuego;



	public vistaJuego1(controllerJuego1 controller, modelInterface modelo) {
		
		this.controller = controller;
		this.modelo = modelo;
		modelo.registrarObserver((observerInterface) this);
		crearVista(); // IMPORTANTE DESCOMENTAR ESTA LINEA CUANDO SE SELCCIONA DESING Y COMENTAR PARA
						// SU USO COMUN Y CORRIENTE DEL PROGRAMA
		
	}

	@Override
	public void crearVista() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		txtSoyJuego = new JTextField();
		txtSoyJuego.setText("SOY JUEGO 1");
		contentPane.add(txtSoyJuego, BorderLayout.CENTER);
		txtSoyJuego.setColumns(10);

	}

	@Override
	public void evento() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actualizar() {

		if (modelo.getEstadoJuego1() == 1) {
			System.out.println("hols");
			this.setVisible(true);
		} else {
			this.setVisible(false);
		}

	}

	@Override
	public void reordenarVista() {
		// TODO Auto-generated method stub
		
	}

}
