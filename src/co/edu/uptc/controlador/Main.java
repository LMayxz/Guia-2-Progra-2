package co.edu.uptc.controlador;

import co.edu.uptc.gui.VentanaUsuarios;
import co.edu.uptc.modelo.GestionUsuarios;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaUsuarios vista = new VentanaUsuarios();
            GestionUsuarios modelo = new GestionUsuarios();
            new ControladorUsuarios(vista, modelo);
            vista.setVisible(true);
        });
    }
}