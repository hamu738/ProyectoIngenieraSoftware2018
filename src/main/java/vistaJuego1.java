

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import interfaces.modelInterface;
import interfaces.observerInterface;
import interfaces.vistaInterface;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class vistaJuego1 extends JFrame implements vistaInterface, observerInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private modelInterface modelo;
	private controllerJuego1 controller;
	
	private ArrayList<JButton> buttons_original;
	private ArrayList<JButton> buttons_reordenados;
	private ArrayList<Point> puntos_mezclados;
	private JPanel panel;
	private JButton btnExit;
	private JTextArea txtEstadoJuego;
	private JTextArea txtEstadoJuego2;
	private BufferedImage source;
	private BufferedImage resized; // imagen ajustada
	private int width;
	private int height;

	private ActionListener listener;
	private JButton lastButton; 

	private Image image;

	private final int DESIRED_WIDTH = 600;
	private int inicio;
	private boolean active;

	public vistaJuego1(controllerJuego1 controller, modelInterface modelo) {

		this.controller = controller;
		this.modelo = modelo;
		modelo.registrarObserver((observerInterface) this);
		inicio = 0;
		active = true;
		//	crearVista(); 
	}

	@Override
	public void crearVista() {

		buttons_original = new ArrayList<JButton>();

		panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.gray));
		panel.setLayout(new GridLayout(5, 3, 0, 0));

		// cargamos imagen
		try {
			source = ImageIO.read(new File(getClass().getResource("/imagenes/imagenjuego1.jpg").toURI()));
			int h = getNewHeight(source.getWidth(), source.getHeight());
			resized = resizeImage(source, DESIRED_WIDTH, h, BufferedImage.TYPE_INT_ARGB);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}

		width = resized.getWidth(null);
		height = resized.getHeight(null);

		add(panel, BorderLayout.CENTER);

		// divido la imagen en 4x3 y agrego un boton vacio, esto es en orden

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {

				image = createImage(new FilteredImageSource(resized.getSource(),
						new CropImageFilter(j * width / 3, i * height / 4, (width / 3), height / 4)));
				ImageIcon imagen = new ImageIcon(image);  
				JButton button = new JButton(""); 
				button.setIcon(imagen); 
				button.putClientProperty("position", new Point(i, j));

				if (i == 3 && j == 2) {
					lastButton = new JButton("");
					lastButton.setBorderPainted(false);
					lastButton.setContentAreaFilled(false);
					lastButton.putClientProperty("position", new Point(i, j));
				} else {
					buttons_original.add(button);
				}
			}
		}

		buttons_original.add(lastButton);

		puntos_mezclados = modelo.getVectorInicialJuego1();
		buttons_reordenados = new ArrayList<JButton>(); 

	//	System.out.println(puntos_mezclados);

		// reordenar buttons_original en buttons_mezclados segun getPuntosmezclados

		for (int i = 0; i < buttons_original.size(); i++) {

			buttons_reordenados.add(buttons_original.get(i));
			buttons_reordenados.get(i).setBorder(BorderFactory.createLineBorder(Color.gray));
			panel.add(buttons_reordenados.get(i));

		}
		
		txtEstadoJuego = new JTextArea();
		txtEstadoJuego.setBackground(SystemColor.activeCaptionBorder);
		txtEstadoJuego.setForeground(Color.BLACK);
		txtEstadoJuego.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		txtEstadoJuego.setText("");
		panel.add(txtEstadoJuego);
		
		txtEstadoJuego2 = new JTextArea();
		txtEstadoJuego2.setBackground(SystemColor.activeCaptionBorder);
		txtEstadoJuego2.setForeground(Color.BLACK);
		txtEstadoJuego2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		txtEstadoJuego2.setText("");
		panel.add(txtEstadoJuego2);
		

		btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		btnExit.setBackground(SystemColor.activeCaptionBorder);
		panel.add(btnExit);

		evento();

	}

	@Override
	public void evento() {

		listener = new ActionListener() { // action listener para los botones del for ASI SE DEFINE
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JButton) {

					if (active) {
					//		Point point = (Point) ((JButton) e.getSource()).getClientProperty("position");
					//	System.out.println(point);
						int index = buttons_reordenados.indexOf((JButton) e.getSource()); // inidice en el arreglo de  botones																				
						controller.logicaJuego(index);
					}

				}
			}

		};

		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.salirJuego();
			}
		});

	}

	@Override
	public void actualizar() {

		int estado = modelo.getEstadoJuego1();

	//	System.out.println("estado juego 1: " + estado);

		switch (estado) {
		case 0:
			this.setVisible(false); //////////////
			break;
		case 1:
			// iniciando juego 1
			active = true;
			reordenarVista();
			txtEstadoJuego.setText("");
			txtEstadoJuego2.setText("");
			inicio = 1;
			this.setVisible(true);
			break;
		case 2: // desarollo
			reordenarVista();
			break;
		case 3:
			// juego terminado
			reordenarVista();
			active = false; //desactivamos clicks sobre imagen ver en evento!
			txtEstadoJuego.setText("\n        ¡Finalizado!");
			txtEstadoJuego2.setText("\n     Volviendo al      \r\n    menu principal." );
			break;
		default:
			break;
		}

	}

	@Override
	public void reordenarVista() {

		if (inicio == 0) {

			for (int i = 0; i < buttons_reordenados.size(); i++) {
				buttons_reordenados.get(i).addActionListener(listener);
			}
		}

		puntos_mezclados = modelo.getPuntos_mezclados();
		buttons_reordenados.clear();

		for (int i = 0; i < buttons_original.size(); i++) {
			Point point = puntos_mezclados.get(i); // tomamos el punto
			// busco en botons quien tiene puntos get
			for (int j = 0; j < buttons_original.size(); j++) {

				Point point2 = (Point) buttons_original.get(j).getClientProperty("position");

				if (point2.getX() == point.getX() && point2.getY() == point.getY()) {

					buttons_reordenados.add(buttons_original.get(j));
					break;
				}
			}
		}


		/*
		 * for (int i = 0; i < buttons_reordenados.size(); i++) {
		 * 
		 * System.out.println("puntos mezclados " + puntos_mezclados.get(i));
		 * System.out.println("boton reordenados " +
		 * buttons_reordenados.get(i).getClientProperty("position"));
		 * 
		 * }
		 */

		// agregamos botones al panel

		panel.removeAll();

		for (JComponent btn : buttons_reordenados) {
			panel.add(btn);
		}

		panel.add(txtEstadoJuego);
		panel.add(txtEstadoJuego2);
		panel.add(btnExit);

		panel.validate();

		pack();
		setTitle("Puzzle");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private int getNewHeight(int w, int h) {

		double ratio = DESIRED_WIDTH / (double) w;
		int newHeight = (int) (h * ratio);
		return newHeight;
	}

	private BufferedImage resizeImage(BufferedImage originalImage, int width, int height, int type) throws IOException {

		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();

		return resizedImage;
	}

}


