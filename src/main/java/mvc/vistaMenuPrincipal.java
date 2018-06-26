package mvc;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.URISyntaxException;

import javax.swing.*;
import javax.imageio.ImageIO;




import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import interfaces.controllerInterface;
import interfaces.modelInterface;
import interfaces.observerInterface;
import interfaces.vistaInterface;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;

public class vistaMenuPrincipal extends JFrame implements vistaInterface, observerInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel contentPane2;
	private JLabel label;
	private Component img;
	
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
		
		
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 646, 448);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		

		Button btnRompecabezas = new Button("Rompecabezas");

		btnRompecabezas.setFont(new Font("Arial Black", Font.BOLD, 15));
		
		
		btnRompecabezas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				controller.seleccionJuego(1);
			}
		});
		
		
		btnRompecabezas.setBounds(124, 181, 131, 70);
		contentPane.add(btnRompecabezas);
		Button btnMemoria = new Button("Memoria");
		btnMemoria.setFont(new Font("Arial Black", Font.BOLD, 15));
		btnMemoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				controller.seleccionJuego(2);

			}
		});
		
		
		btnMemoria.setBounds(351, 181, 131, 70);
		contentPane.add(btnMemoria);
		
		
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 40, 630, 369);
		contentPane.add(lblNewLabel);
		
		try {
			lblNewLabel.setIcon(new ImageIcon(this.getClass().getResource("../imagenes/game.jpg")));

		} catch (Exception e) {
		}
		
		
		JLabel lblNewLabel_1 = new JLabel("");
		
		try {
			lblNewLabel_1.setIcon(new ImageIcon(this.getClass().getResource("../imagenes/button.png")));
		} catch (Exception e) {
		}
		
		lblNewLabel_1.setBounds(0, 0, 645, 40);
		contentPane.add(lblNewLabel_1);
		
		
			
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
