package Controlador;

import Modelo.Carta;
import Modelo.HistorialJuego;
import Modelo.Mazo;
import Modelo.Jugador;
import Vista.VistaJuego;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorJuego {
	private final Mazo mazo;
	private final Jugador jugador;
	private final Jugador banca;
	private final HistorialJuego historial;

	public ControladorJuego() {
		mazo = new Mazo();
		jugador = new Jugador();
		banca = new Jugador();
		historial = new HistorialJuego();
		mazo.mezclarMazo();
	};

	public void iniciarJuego(){
		jugador.reiniciarMano();
		banca.reiniciarMano();
		mazo.mezclarMazo();

		jugador.recibirCarta(mazo.sacarCarta());
		jugador.recibirCarta(mazo.sacarCarta());

		banca.recibirCarta(mazo.sacarCarta());
	};

	public void jugadorPideCarta() {
		jugador.recibirCarta(mazo.sacarCarta());
	};

	public void bancaPideCarta(VistaJuego vista) {
		Timer temporizador = new Timer(1000, null);
		temporizador.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (banca.getPuntaje() < 17) {
					banca.recibirCarta(mazo.sacarCarta());
					vista.actualizarVista();
				} else {
					((Timer) e.getSource()).stop();
					String resultado = terminarJuego();
					vista.mostrarResultado(resultado);
					vista.deshabilitarBotones();
				}
			}
		});
		temporizador.start();
	};

	public String mostrarEstadoJugador() {
		StringBuilder estado = new StringBuilder();
		estado.append("Cartas en mano:\n");

		for (Carta carta : jugador.getMano()) {
			estado.append(carta.toString()).append("\n");
		}

		estado.append("Puntaje: ").append(jugador.getPuntaje());
		return estado.toString();
	};

	public String mostrarEstadoBanca() {
		StringBuilder estado = new StringBuilder();
		estado.append("Cartas en mano:\n");

		for (Carta carta : banca.getMano()) {
			estado.append(carta.toString()).append("\n");
		}

		estado.append("Puntaje: ").append(banca.getPuntaje());
		return estado.toString();
	};

	public String determinarGanador() {
		int puntajeJugador = jugador.getPuntaje();
		int puntajeBanca = banca.getPuntaje();

		if (puntajeJugador > 21) {
			return "¡Jugador se pasa! Gana la banca.";
		} else if (puntajeBanca > 21) {
			return "¡La banca se pasa! Gana el jugador.";
		} else if (puntajeJugador > puntajeBanca) {
			return "¡Jugador gana con " + puntajeJugador + " puntos!";
		} else if (puntajeJugador < puntajeBanca) {
			return "La banca gana con " + puntajeBanca + " puntos.";
		} else {
			return "¡Empate con " + puntajeJugador + " puntos!";
		}
	};

	public int getPuntajeJugador() {
		return jugador.getPuntaje();
	};

	public int getPuntajeBanca() {
		return banca.getPuntaje();
	};

	public String terminarJuego() {
		String resultado = determinarGanador();
		guardarResultado(resultado);
		return resultado;
	}

	public void guardarResultado(String resultado) {
		historial.guardarHistorial(resultado, jugador.getPuntaje(), banca.getPuntaje());
	};

	public String mostrarHistorial() {
		return historial.leerHistorial();
	};

	public void eliminarHistorial() {
		historial.eliminarHistorial();
	};
}
