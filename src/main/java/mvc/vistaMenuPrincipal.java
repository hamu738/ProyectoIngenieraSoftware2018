package mvc;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import interfaces.controllerInterface;
import interfaces.modelInterface;
import interfaces.observerInterface;
import interfaces.vistaInterface;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.SystemColor;

public class vistaMenuPrincipal extends JFrame implements vistaInterface, observerInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtBienvenidoSeleccioneEl;
	private modelInterface modelo;
	private controllerInterface controller;

	public vistaMenuPrincipal(controllerMenuPrincipal controller, modelInterface modelo) {

		this.controller = controller;
		this.modelo = modelo;
		modelo.registrarObserver((observerInterface) this);
	//	crearVista(); // IMPORTANTE DESCOMENTAR ESTA LINEA CUANDO SE SELCCIONA DESING Y COMENTAR PARA
						// SU USO COMUN Y CORRIENTE DEL PROGRAMA

	}

	@Override
	public void crearVista() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 497, 323);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(30, 144, 255));
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		txtBienvenidoSeleccioneEl = new JTextField();
		txtBienvenidoSeleccioneEl.setHorizontalAlignment(SwingConstants.CENTER);
		txtBienvenidoSeleccioneEl.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 22));
		txtBienvenidoSeleccioneEl.setForeground(SystemColor.inactiveCaptionText);
		txtBienvenidoSeleccioneEl.setBackground(SystemColor.textHighlight);
		txtBienvenidoSeleccioneEl.setText("Bienvenido! Seleccione el juego");
		GridBagConstraints gbc_txtBienvenidoSeleccioneEl = new GridBagConstraints();
		gbc_txtBienvenidoSeleccioneEl.gridwidth = 14;
		gbc_txtBienvenidoSeleccioneEl.insets = new Insets(0, 0, 5, 5);
		gbc_txtBienvenidoSeleccioneEl.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBienvenidoSeleccioneEl.gridx = 4;
		gbc_txtBienvenidoSeleccioneEl.gridy = 2;
		contentPane.add(txtBienvenidoSeleccioneEl, gbc_txtBienvenidoSeleccioneEl);
		txtBienvenidoSeleccioneEl.setColumns(10);

		JButton btnRompecabezas = new JButton("Rompecabezas");

		btnRompecabezas.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 17));
		btnRompecabezas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				controller.seleccionJuego(1);
			}
		});
		GridBagConstraints gbc_btnRompecabezas = new GridBagConstraints();
		gbc_btnRompecabezas.insets = new Insets(0, 0, 5, 5);
		gbc_btnRompecabezas.gridx = 7;
		gbc_btnRompecabezas.gridy = 4;
		contentPane.add(btnRompecabezas, gbc_btnRompecabezas);

		JButton btnMemoria = new JButton("Memoria");

		btnMemoria.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 17));
		btnMemoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				controller.seleccionJuego(2);

			}
		});
		GridBagConstraints gbc_btnMemoria = new GridBagConstraints();
		gbc_btnMemoria.gridwidth = 3;
		gbc_btnMemoria.insets = new Insets(0, 0, 5, 5);
		gbc_btnMemoria.gridx = 11;
		gbc_btnMemoria.gridy = 4;
		contentPane.add(btnMemoria, gbc_btnMemoria);
	}

	@Override
	public void evento() {

	}

	@Override
	public void actualizar() {

		if (modelo.getMostrarMenu() == 1) {
			this.setVisible(true);
		} else {
			this.setVisible(false);
		}

	}

	@Override
	public void reordenarVista() {

	}

}