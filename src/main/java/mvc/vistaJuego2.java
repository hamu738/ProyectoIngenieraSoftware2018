package mvc;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.Timer;

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
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.SwingConstants;

public class vistaJuego2 extends JFrame implements vistaInterface, observerInterface {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private modelInterface modelo;
	private controllerJuego2 controller;
	private int aleatorio[]; // a cada posicion del arreglo le corresponde un boton
	private JButton buttons[] = new JButton[12];
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
	private JTextField txtEstadoJuego;
	private int aux_contador_tiempo;
	private Timer timer;

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
		progressBar.setMaximum(modelo.getTiempoMaximoJuego2() / 1000); // sobre 1000 por estar en miliseg

		txtPuntajes = new JTextArea();
		txtPuntajes.setBackground(SystemColor.activeCaptionBorder);
		txtPuntajes.setForeground(Color.BLACK);
		txtPuntajes.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		txtPuntajes.setText("\n       Aciertos: " + aciertos + "\r\n\n       Desaciertos: " + desaciertos);
		contentPane.add(txtPuntajes);

		txtEstadoJuego = new JTextField();
		txtEstadoJuego.setHorizontalAlignment(SwingConstants.CENTER);
		txtEstadoJuego.setText("");
		txtEstadoJuego.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 20));
		txtEstadoJuego.setBackground(SystemColor.activeCaptionBorder);
		contentPane.add(txtEstadoJuego);
		txtEstadoJuego.setColumns(10);

		btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		btnExit.setBackground(SystemColor.activeCaptionBorder);
		contentPane.add(btnExit);

		evento();

	}

	@Override
	public void reordenarVista() {

		for (int i = 0; i < buttons.length; i++) {
			ima[i] = new ImageIcon(this.getClass().getResource("../imagenes/" + aleatorio[i] + ".jpg")); // vamos
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
						// System.out.println(text);
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

	private void estado1() {

		aleatorio = modelo.getAleatorioJuego2_aux();
		aciertos = modelo.getAciertosJuego2();
		desaciertos = modelo.getDesaciertosJuego2();
		txtPuntajes.setText("\n       Aciertos: " + aciertos + "\r\n\n       Desaciertos: " + desaciertos);
		progressBar.setValue(0);
		// System.out.println(Arrays.toString(aleatorio));
		txtEstadoJuego.setText("Iniciando juego.");
	}

	private void estado2() {

		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setIcon(null); // setIconNull para guardarlo
		}
		txtEstadoJuego.setText("Juego iniciado.");

	}

	private void estado3() {

		aleatorio = modelo.getAleatorioJuego2_aux(); // actulizamos aleatoror con -1 aquellos que fueron
		// descubiertos
		aciertos = modelo.getAciertosJuego2();
		desaciertos = modelo.getDesaciertosJuego2();
		txtPuntajes.setText("\n       Aciertos: " + aciertos + "\r\n\n       Desaciertos: " + desaciertos);

		// System.out.println(Arrays.toString(aleatorio));
	}

	private void estado4() {

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

	}

	private void estado5() {

		// System.out.println("Caso 5, termino");
		txtEstadoJuego.setText("Juego terminado.");

	}

	@Override
	public void actualizar() {

		int estado = modelo.getEstadoJuego2();

		 System.out.println("estado: " + estado);

		switch (estado) {
		case 0:
			active = false;
			this.setVisible(false);
			break;
		case 1:
			active = false;
			estado1();
			reordenarVista();
			this.setVisible(true);
			break;
		case 2:
			active = true;
			estado2();
			timer = iniciarTimer();
			break;
		case 3: // el juego ya empezado
			estado3();
			break;
		case 4: // el juego ya empezado
			active = false; // bloqueamos events listeners
			estado4();
			break;
		case 5: // el juego ya terminado
			active = false;
			finalizarTimer(timer);
			estado5();
			break;
		default:
			break;
		}

	}

	private Timer iniciarTimer() {

		aux_contador_tiempo = 0;
		Timer timer = new Timer(1000, null); // cada un segundo
		timer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				aux_contador_tiempo = aux_contador_tiempo + 1;
				progressBar.setValue(aux_contador_tiempo);

			}
		});

		timer.start();
		return timer;

	}

	private void finalizarTimer(Timer timer) {

		try {
			timer.stop();
		} catch (Exception e) {
		}

	}

}