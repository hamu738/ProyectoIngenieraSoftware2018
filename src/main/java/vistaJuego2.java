import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.swing.Timer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import interfaces.modelInterface;
import interfaces.observerInterface;
import interfaces.vistaInterface;
import javax.swing.JProgressBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.DropMode;
import java.awt.Font;

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
	private JProgressBar progressBar;
	private ActionListener listener;
	ImageIcon ima[] = new ImageIcon[12]; // tengo 6 pares de imagens
	private int sel = 0;
	private int par_seleccionado[] = new int[2];
	public final static int tiempo_error = 500;
	public boolean active;
	private int aciertos;
	private int desaciertos;
	private JTextArea txtPuntajes;
	private JTextField textField;
	private int aux_contador_tiempo;

	public vistaJuego2(controllerJuego2 controller, modelInterface modelo) {
		setBackground(SystemColor.desktop);
		setForeground(SystemColor.activeCaption);

		this.controller = controller;
		this.modelo = modelo;
		modelo.registrarObserver((observerInterface) this);
		this.active = true;
		aciertos = desaciertos = 0;
		aux_contador_tiempo = 0;
		crearVista(); // IMPORTANTE DESCOMENTAR ESTA LINEA CUANDO SE SELCCIONA DESING Y COMENTAR PARA
						// SU USO COMUN Y CORRIENTE DEL PROGRAMA
	}

	@Override
	public void crearVista() {

		// importamos imagen que estan en carpeta src

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 756, 562);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaptionBorder);
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

		progressBar = new JProgressBar();
		progressBar.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		progressBar.setStringPainted(true);
		progressBar.setForeground(Color.DARK_GRAY);
		progressBar.setBackground(SystemColor.activeCaptionBorder);
		contentPane.add(progressBar);
		progressBar.setMaximum(modelo.getTiempoMaximoJuego2()/1000); //sobre 1000 por estar en miliseg  

		txtPuntajes = new JTextArea();
		txtPuntajes.setBackground(SystemColor.activeCaptionBorder);
		txtPuntajes.setForeground(Color.BLACK);
		txtPuntajes.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		txtPuntajes.setText("\n       Aciertos: " + aciertos + "\r\n\n       Desaciertos: " + desaciertos);
		contentPane.add(txtPuntajes);

		textField = new JTextField();
		textField.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		textField.setBackground(SystemColor.activeCaptionBorder);
		contentPane.add(textField);
		textField.setColumns(10);

		btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		btnExit.setBackground(SystemColor.activeCaptionBorder);
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
					if (aleatorio[Integer.parseInt(text)] != -1 && active) {
						buttons[Integer.parseInt(text)].setIcon(ima[Integer.parseInt(text)]);
						par_seleccionado[sel] = Integer.parseInt(text);
						sel = (sel + 1) % 2;
						controller.logicaJuego(Integer.parseInt(text));
						System.out.println(text);
					}
				}
			}

		};

		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				active = false;
				controller.salirJuego();
			}
		});

	}

	@Override
	public void actualizar() {

		int estado = modelo.getEstadoJuego2();

		System.out.println("estado: " + estado);

		switch (estado) {
		case 0:
			this.setVisible(false);
			active = false;
			break;
		case 1:
			active = false;
			aleatorio = modelo.getAleatorioJuego2_aux();
			aciertos = modelo.getAciertosJuego2();
			desaciertos = modelo.getDesaciertosJuego2();
			txtPuntajes.setText("\n       Aciertos: " + aciertos + "\r\n\n       Desaciertos: " + desaciertos);
			// System.out.println(Arrays.toString(aleatorio));
			reordenarVista();
			this.setVisible(true);
			break;
		case 2:
			for (int i = 0; i < buttons.length; i++) {
				buttons[i].setIcon(null); // setIconNull para guardarlo
			}

			iniciarTimer();
			active = true;
			break;
		case 3: // el juego ya empezado

			aleatorio = modelo.getAleatorioJuego2_aux(); // actulizamos aleatoror con -1 aquellos que fueron
															// descubiertos
			aciertos = modelo.getAciertosJuego2();
			desaciertos = modelo.getDesaciertosJuego2();
			txtPuntajes.setText("\n       Aciertos: " + aciertos + "\r\n\n       Desaciertos: " + desaciertos);
		
			System.out.println(Arrays.toString(aleatorio));

			break;
		case 4: // el juego ya empezado

			active = false; // bloqueamos events listeners

			Timer timer = new Timer(tiempo_error, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buttons[par_seleccionado[0]].setIcon(null);
					buttons[par_seleccionado[1]].setIcon(null);
					aciertos = modelo.getAciertosJuego2();
					desaciertos = modelo.getDesaciertosJuego2();
					txtPuntajes.setText("\n       Aciertos: " + aciertos + "\r\n\n       Desaciertos: " + desaciertos);
					active = true;

				}
			});

			timer.start();
			timer.setRepeats(false);

			break;
		case 5: // el juego ya terminado

			active = false;
			System.out.println("Caso 5, termino");

			// mostrsar mensaje de fializacion

			// this.setVisible(false);

			break;
		default:
			break;
		}

	}

	private void iniciarTimer() {

		aux_contador_tiempo = 0;

		Timer timer = new Timer(1000, null); // cada un segundo

		timer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				aux_contador_tiempo = aux_contador_tiempo + 1;
				System.out.println("Contador: " + aux_contador_tiempo);
				progressBar.setValue(aux_contador_tiempo);
				if (modelo.getEstadoJuego2() == 0 || modelo.getEstadoJuego2() == 5) {
			    	//System.out.println("Finalizado: " );
					timer.stop();
					finalizarTimer();
				}
			}
		});


		timer.start();

	}

	private void finalizarTimer() {

	}

}