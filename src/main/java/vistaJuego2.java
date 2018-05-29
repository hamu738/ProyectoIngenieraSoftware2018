import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import interfaces.modelInterface;
import interfaces.observerInterface;
import interfaces.vistaInterface;
import javax.swing.JProgressBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class vistaJuego2 extends JFrame implements vistaInterface, observerInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private modelInterface modelo;
	private controllerJuego2 controller;
	private int aleatorio[]; // a cada posicion del arreglo le corresponde un boton
	private JButton buttons[] = new JButton[12];
	private JButton btnIniciar;
	private JButton btnExit;
	private ActionListener listener;
	ImageIcon ima[] = new ImageIcon[12]; // tengo 6 pares de imagens
	private int sel = 0;
	private int par_seleccionado [] = new int [2];


	public vistaJuego2(controllerJuego2 controller, modelInterface modelo) {
		setBackground(SystemColor.desktop);
		setForeground(SystemColor.activeCaption);

		this.controller = controller;
		this.modelo = modelo;
		modelo.registrarObserver((observerInterface) this);
		crearVista(); // IMPORTANTE DESCOMENTAR ESTA LINEA CUANDO SE SELCCIONA DESING Y COMENTAR PARA
						// SU USO COMUN Y CORRIENTE DEL PROGRAMA
	}

	@Override
	public void crearVista() {

		// importamos imagen que estan en carpeta src

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 582, 449);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(34, 139, 34));
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(4, 4, 0, 0));

		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton("" + i);
			buttons[i].addActionListener(listener);
			buttons[i].setBackground(SystemColor.activeCaption);
			buttons[i].setForeground(SystemColor.activeCaption);
			contentPane.add(buttons[i]);
			// ESTO NO SE VE EN LA VISTA PREVIA CUIDADO SOLO CUANDO SE CORRE EL PROGRMAA
		}

		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setForeground(Color.DARK_GRAY);
		progressBar.setBackground(Color.DARK_GRAY);
		contentPane.add(progressBar);

		btnIniciar = new JButton("Iniciar");
		btnIniciar.setBackground(SystemColor.activeCaption);
		btnIniciar.setForeground(SystemColor.desktop);
		contentPane.add(btnIniciar);

		btnExit = new JButton("Exit");
		btnExit.setBackground(SystemColor.activeCaption);
		contentPane.add(btnExit);

		evento();

	}

	@Override
	public void reordenarVista() {

		for (int i = 0; i < buttons.length; i++) {
			ima[i] = new ImageIcon(this.getClass().getResource("imagenes/" + aleatorio[i] + ".jpg")); // vamos
																										// recorriendo
																										// imagenes
			buttons[i].setIcon(ima[i]); // setIconNull para guardarlo
		}

	}

	@Override
	public void evento() {

		listener = new ActionListener() { // action listener para los botones del for ASI SE DEFINE
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JButton) {
					String text = ((JButton) e.getSource()).getText();
					if(aleatorio[Integer.parseInt(text)] != -1) {
						
				
					buttons[Integer.parseInt(text)].setIcon(ima[Integer.parseInt(text)]);
					par_seleccionado [sel] = Integer.parseInt(text);
					sel = (sel + 1) % 2;
					controller.logicaJuego(Integer.parseInt(text));
					// System.out.println(text);
					}
				}
			}
		};

		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.logicaJuego(-1);
			}
		});
		
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.salirJuego();
			}
		});

		
	
	}

	@Override
	public void actualizar() {

		int estado = modelo.getEstadoJuego2();

		switch (estado) {
		case 0:
			this.setVisible(false);
			break;
		case 1:
			aleatorio = modelo.getAleatorioJuego2_aux();
			System.out.println(Arrays.toString(aleatorio));
			reordenarVista();
			this.setVisible(true);
			break;
		case 2:
			for (int i = 0; i < buttons.length; i++) {
				buttons[i].setIcon(null); // setIconNull para guardarlo
			}
			break;
		case 3: // el juego ya empezado
			aleatorio = modelo.getAleatorioJuego2_aux(); //actulizamos aleatorior con -1 aquellos que fueron descubiertos
			break;
		case 4: // el juego ya empezado

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			buttons[par_seleccionado[0]].setIcon(null);
			buttons[par_seleccionado[1]].setIcon(null);			
			break;
		case 5: // el juego ya empezado
			this.setVisible(false);
			break;
		default:
			break;
		}

	}

}