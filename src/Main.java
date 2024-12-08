import Controlador.ControladorJuego;
import Vista.VistaJuego;

public class Main {
	public static void main(String[] args) {
		ControladorJuego controlador = new ControladorJuego();
		VistaJuego vista = new VistaJuego(controlador);
		vista.setVisible(true);
	}
}
