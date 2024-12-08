package Modelo;

public class Carta {
	private final String tipo; //Corazon, Diamante, Trébol, Espada
	private final String valor; //A,2,3,4,5,6,7,8,9,10,J,Q,K

	public Carta(String tipo, String valor) {
		this.tipo = tipo;
		this.valor = valor;
	};
	public String getTipo() {
		return tipo;
	};

	public String getValor() {
		return valor;
	};

	@Override
	public String toString() {
		return valor + " de " + tipo;
	};

}
