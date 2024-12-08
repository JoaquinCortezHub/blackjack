package Modelo;

import java.util.ArrayList;
import java.util.Collections;

public class Mazo {
	private final ArrayList<Carta> mazo;

	public Mazo() {
		mazo = new ArrayList<>();
		iniciarMazo();
	};

	private void iniciarMazo() {
		String[] tipos = {"Corazon", "Diamante", "Trebol", "Espada"};
		String[] valores = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

		for(String tipo : tipos) {
			for(String valor : valores) {
				mazo.add(new Carta(tipo, valor));
			};
		};
	};

	public void mezclarMazo() {
		Collections.shuffle(mazo);
	};

	public Carta sacarCarta() {
		try{
			if (!mazo.isEmpty()) {
				return mazo.remove(0);
			} else {
				throw new Exception("El mazo está vacío.");
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		return null;
		}
	};

	public Boolean estaVacio() {
		return mazo.isEmpty();
	};
}
