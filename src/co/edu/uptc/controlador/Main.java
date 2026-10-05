package co.edu.uptc.controlador;

import co.edu.uptc.gui.VentanaGestionLibros;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ControladorLibros controlador = new ControladorLibros();
            VentanaGestionLibros vista = new VentanaGestionLibros(controlador);
            vista.setVisible(true);
        });
    }
}