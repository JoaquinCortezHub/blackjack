package Vista;

import Controlador.ControladorJuego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaJuego extends JFrame {
	private final ControladorJuego controlador;
	private JTextArea areaJugador;
	private JTextArea areaBanca;
	private JLabel puntajeJugador;
	private JLabel puntajeBanca;
	private JLabel puntaje;
	private JButton botonPedirCarta;
	private JButton botonPlantarse;
	private JButton botonReiniciar;
	private JButton botonVerHistorial;
	private JButton botonEliminarHistorial;

	public VistaJuego(ControladorJuego controlador) {
		this.controlador = controlador;
		configurarVentana();
		iniciarComponentes();
		agregarEventos();
	};

	private void configurarVentana() {
		setTitle("Blackjack");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(34,139,34));
	};

	private void iniciarComponentes() {
		//Fuentes para el texto y los botones.
		Font fuenteTexto = new Font("Arial", Font.BOLD, 16);
		Font fuenteBotones = new Font("Arial", Font.BOLD, 14);

		//Este panel muestra las manos de la banca y del jugador.
		JPanel panelManos = new JPanel(new GridLayout(2,1));
		panelManos.setOpaque(false);

		//Este panel es para el área de la banca.
		JPanel panelBanca = new JPanel(new BorderLayout());
		panelBanca.setBorder(BorderFactory.createTitledBorder("Mano de la Banca:"));
		panelBanca.setOpaque(false);
		areaBanca = new JTextArea();
		areaBanca.setEditable(false);
		areaBanca.setFont(fuenteTexto);
		panelBanca.add(new JScrollPane(areaBanca), BorderLayout.CENTER);
		puntajeBanca = new JLabel("Puntaje: 0");
		puntajeBanca.setFont(fuenteTexto);
		puntajeBanca.setForeground(Color.WHITE);
		panelBanca.add(puntajeBanca, BorderLayout.SOUTH);

		//Este panel es para el área del jugador.
		JPanel panelJugador = new JPanel(new BorderLayout());
		panelJugador.setBorder(BorderFactory.createTitledBorder("Tu mano:"));
		panelJugador.setOpaque(false);
		areaJugador = new JTextArea();
		areaJugador.setEditable(false);
		areaJugador.setFont(fuenteTexto);
		panelJugador.add(new JScrollPane(areaJugador), BorderLayout.CENTER);
		puntajeJugador = new JLabel("Puntaje: 0");
		puntajeJugador.setFont(fuenteTexto);
		puntajeJugador.setForeground(Color.WHITE);
		panelJugador.add(puntajeJugador, BorderLayout.SOUTH);

		panelManos.add(panelBanca);
		panelManos.add(panelJugador);

		//Este panel es para los botones de acción.
		JPanel panelControles = new JPanel();
		panelControles.setLayout(new FlowLayout());
		panelControles.setOpaque(false);

		botonPedirCarta = new JButton("Pedir carta");
		botonPlantarse = new JButton("Plantarse");
		botonReiniciar = new JButton("Reiniciar");
		botonVerHistorial = new JButton("Ver historial");
		botonEliminarHistorial = new JButton("Eliminar historial");

		botonPedirCarta.setFont(fuenteBotones);
		botonPlantarse.setFont(fuenteBotones);
		botonReiniciar.setFont(fuenteBotones);

		panelControles.add(botonPedirCarta);
		panelControles.add(botonPlantarse);
		panelControles.add(botonReiniciar);
		panelControles.add(botonVerHistorial);
		panelControles.add(botonEliminarHistorial);

		puntaje = new JLabel("Resultado: ");
		puntaje.setFont(new Font("Arial", Font.BOLD, 20));
		puntaje.setForeground(Color.WHITE);
		puntaje.setHorizontalAlignment(SwingConstants.CENTER);

		//Acá se agrega to do al frame.
		add(panelManos, BorderLayout.CENTER);
		add(panelControles, BorderLayout.SOUTH);
		add(puntaje, BorderLayout.NORTH);
	};

	private void agregarEventos() {
		botonPedirCarta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controlador.jugadorPideCarta();
					actualizarVista();
					if (controlador.getPuntajeJugador() > 21) { // El jugador pierde automáticamente
						String resultado = controlador.terminarJuego();
						mostrarResultado(resultado);
						deshabilitarBotones();
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error al pedir carta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		botonPlantarse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controlador.bancaPideCarta(VistaJuego.this);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error al finalizar la partida: " + ex.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		botonReiniciar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.iniciarJuego();
				actualizarVista();
				habilitarBotones();
				puntaje.setText("Resultado: ");
			}
		});

		botonVerHistorial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String historial = controlador.mostrarHistorial();
					if (historial.isEmpty()) {
						JOptionPane.showMessageDialog(null, "No hay historial disponible.", "Historial", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, historial, "Historial", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Error al mostrar el historial." + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		botonEliminarHistorial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controlador.eliminarHistorial();
					JOptionPane.showMessageDialog(null, "Historial limpiado con éxito.", "Historial", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error al limpiar el historial: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	};

	public void actualizarVista() {
		areaJugador.setText(controlador.mostrarEstadoJugador());
		areaBanca.setText(controlador.mostrarEstadoBanca());
		puntajeJugador.setText("Puntaje Jugador: " + controlador.getPuntajeJugador());
		puntajeBanca.setText("Puntaje Banca: " + controlador.getPuntajeBanca());
	};

	public void mostrarResultado(String mensaje) {
		puntaje.setText("Resultado: " + mensaje);
	};

	public void deshabilitarBotones() {
		botonPedirCarta.setEnabled(false);
		botonPlantarse.setEnabled(false);
	}

	private void habilitarBotones() {
		botonPedirCarta.setEnabled(true);
		botonPlantarse.setEnabled(true);
	}
}
