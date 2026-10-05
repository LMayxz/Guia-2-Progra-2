package co.edu.uptc.controlador;

import co.edu.uptc.gui.VentanaVentas;
import co.edu.uptc.modelo.GestionVentas;
import co.edu.uptc.modelo.Venta;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.Locale;

public class ControladorVentas {

    private VentanaVentas vista;
    private GestionVentas modelo;

    public ControladorVentas(VentanaVentas vista, GestionVentas modelo) {
        this.vista = vista;
        this.modelo = modelo;
        inicializarEventos();
    }

    private void inicializarEventos() {
        vista.getBtnAgregar().addActionListener(e -> agregarVenta());
        vista.getBtnActualizar().addActionListener(e -> actualizarVenta());
        vista.getBtnEliminar().addActionListener(e -> eliminarVenta());
        vista.getBtnLimpiar().addActionListener(e -> limpiarCampos());

        vista.getTablaVentas().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vista.getTablaVentas().getSelectedRow();
                if (fila >= 0) {
                    DefaultTableModel t = vista.getModeloTabla();
                    vista.getTxtIdVenta().setText(t.getValueAt(fila, 0).toString());
                    vista.getTxtIdCliente().setText(t.getValueAt(fila, 2).toString());
                    vista.getTxtIsbn().setText(t.getValueAt(fila, 3).toString());
                    vista.getTxtCantidad().setText(t.getValueAt(fila, 4).toString());
                    vista.getTxtPrecio().setText(t.getValueAt(fila, 5).toString());
                    vista.getChkPremium().setSelected("Sí".equals(t.getValueAt(fila, 6)));
                    Venta v = modelo.buscarVentaPorId(t.getValueAt(fila, 0).toString());
                    if (v != null) {
                        vista.getCbIva().setSelectedItem(v.getTasaIva() == 0.05 ? "5%" : "19%");
                        vista.getCbMetodoPago().setSelectedItem(v.getMetodoPago());
                    }
                }
            }
        });
    }

    /** Lee y valida el formulario. Devuelve null si hay error (ya muestra el mensaje). */
    private Venta leerFormulario() {
        String id = vista.getTxtIdVenta().getText().trim();
        String cliente = vista.getTxtIdCliente().getText().trim();
        String isbn = vista.getTxtIsbn().getText().trim();
        String cantTxt = vista.getTxtCantidad().getText().trim();
        String precioTxt = vista.getTxtPrecio().getText().trim();

        if (id.isEmpty() || cliente.isEmpty() || isbn.isEmpty() || cantTxt.isEmpty() || precioTxt.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor complete todos los campos.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        int cantidad;
        double precio;
        try {
            cantidad = Integer.parseInt(cantTxt);
            precio = Double.parseDouble(precioTxt);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Cantidad y precio deben ser numéricos.", "Dato inválido", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (cantidad <= 0 || precio <= 0) {
            JOptionPane.showMessageDialog(vista, "Cantidad y precio deben ser mayores a cero.", "Dato inválido", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        double iva = "5%".equals(vista.getCbIva().getSelectedItem()) ? 0.05 : 0.19;
        String pago = (String) vista.getCbMetodoPago().getSelectedItem();

        return new Venta(id, LocalDate.now(), cliente, isbn, cantidad, precio,
                vista.getChkPremium().isSelected(), iva, pago);
    }

    private void agregarVenta() {
        Venta v = leerFormulario();
        if (v == null) return;

        if (modelo.agregarVenta(v)) {
            JOptionPane.showMessageDialog(vista, "Venta registrada exitosamente.");
            actualizarTabla();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(vista, "Ya existe una venta con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarVenta() {
        Venta v = leerFormulario();
        if (v == null) return;

        boolean exito = modelo.actualizarVenta(v.getIdVenta(), v.getIdCliente(), v.getIsbn(), v.getCantidad(),
                v.getPrecioUnitario(), v.isClientePremium(), v.getTasaIva(), v.getMetodoPago());
        if (exito) {
            JOptionPane.showMessageDialog(vista, "Venta actualizada correctamente.");
            actualizarTabla();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(vista, "No se encontró la venta con ID: " + v.getIdVenta(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarVenta() {
        String id = vista.getTxtIdVenta().getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese el ID de la venta a eliminar.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int conf = JOptionPane.showConfirmDialog(vista, "¿Está seguro de eliminar la venta " + id + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            if (modelo.eliminarVenta(id)) {
                JOptionPane.showMessageDialog(vista, "Venta eliminada correctamente.");
                actualizarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista, "No se encontró la venta con ID: " + id, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarTabla() {
        DefaultTableModel t = vista.getModeloTabla();
        t.setRowCount(0);
        for (Venta v : modelo.obtenerVentas()) {
            t.addRow(new Object[]{
                v.getIdVenta(), v.getFecha(), v.getIdCliente(), v.getIsbn(), v.getCantidad(),
                v.getPrecioUnitario(), v.isClientePremium() ? "Sí" : "No",
                formato(v.getSubtotal()), formato(v.getDescuento()), formato(v.getImpuesto()),
                formato(v.getTotal()), v.getMetodoPago()
            });
        }
    }

    private String formato(double valor) {
        return String.format(Locale.US, "%.2f", valor);
    }

    private void limpiarCampos() {
        vista.getTxtIdVenta().setText("");
        vista.getTxtIdCliente().setText("");
        vista.getTxtIsbn().setText("");
        vista.getTxtCantidad().setText("");
        vista.getTxtPrecio().setText("");
        vista.getChkPremium().setSelected(false);
        vista.getCbIva().setSelectedIndex(0);
        vista.getCbMetodoPago().setSelectedIndex(0);
        vista.getTablaVentas().clearSelection();
    }
}
