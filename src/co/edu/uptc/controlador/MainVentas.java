package co.edu.uptc.controlador;

import co.edu.uptc.gui.VentanaVentas;
import co.edu.uptc.modelo.GestionVentas;
import javax.swing.SwingUtilities;

public class MainVentas {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaVentas vista = new VentanaVentas();
            GestionVentas modelo = new GestionVentas();
            new ControladorVentas(vista, modelo);
            vista.setVisible(true);
        });
    }
}