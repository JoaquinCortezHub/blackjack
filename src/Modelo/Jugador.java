package Modelo;

import java.util.ArrayList;

public class Jugador {
	private final ArrayList<Carta> mano;
	private int puntaje;

	public Jugador() {
		mano = new ArrayList<>();
		puntaje = 0;
	};

	public void recibirCarta(Carta carta) {
		if(carta != null) {
			mano.add(carta);
			sumarPuntaje(carta);
		}
	};

	private void sumarPuntaje(Carta carta) {
		String valor = carta.getValor();
		if(valor.equals("A")) {
			if(puntaje + 11 > 21) {
				puntaje += 1;
			} else {
				puntaje += 11;
			}
		} else if(valor.equals("J") || valor.equals("Q") || valor.equals("K")) {
			puntaje += 10;
		} else {
			puntaje += Integer.parseInt(valor);
		};
	};

	public int getPuntaje() {
		return puntaje;
	};
	public ArrayList<Carta> getMano() {
		return mano;
	};

	public void reiniciarMano() {
		mano.clear();
		puntaje = 0;
	};

}
