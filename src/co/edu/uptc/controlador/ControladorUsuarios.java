package co.edu.uptc.controlador;

import co.edu.uptc.gui.VentanaUsuarios;
import co.edu.uptc.modelo.GestionUsuarios;
import co.edu.uptc.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControladorUsuarios {

    private VentanaUsuarios vista;
    private GestionUsuarios modelo;

    public ControladorUsuarios(VentanaUsuarios vista, GestionUsuarios modelo) {
        this.vista = vista;
        this.modelo = modelo;

        inicializarEventos();
    }

    private void inicializarEventos() {
  
        vista.getBtnAgregar().addActionListener(e -> agregarUsuario());
        vista.getBtnActualizar().addActionListener(e -> actualizarUsuario());
        vista.getBtnEliminar().addActionListener(e -> eliminarUsuario());
        vista.getBtnLimpiar().addActionListener(e -> limpiarCampos());

      
        vista.getTablaUsuarios().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = vista.getTablaUsuarios().getSelectedRow();
                if (filaSeleccionada >= 0) {
                    vista.getTxtId().setText(vista.getModeloTabla().getValueAt(filaSeleccionada, 0).toString());
                    vista.getTxtNombre().setText(vista.getModeloTabla().getValueAt(filaSeleccionada, 1).toString());
                    vista.getTxtEmail().setText(vista.getModeloTabla().getValueAt(filaSeleccionada, 2).toString());
                    vista.getCbRol().setSelectedItem(vista.getModeloTabla().getValueAt(filaSeleccionada, 3).toString());
                }
            }
        });
    }

    private void agregarUsuario() {
        String id = vista.getTxtId().getText().trim();
        String nombre = vista.getTxtNombre().getText().trim();
        String email = vista.getTxtEmail().getText().trim();
        String rol = (String) vista.getCbRol().getSelectedItem();

        if (id.isEmpty() || nombre.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor complete todos los campos.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario nuevoUsuario = new Usuario(id, nombre, email, rol);
        boolean exito = modelo.agregarUsuario(nuevoUsuario);

        if (exito) {
            JOptionPane.showMessageDialog(vista, "Usuario registrado exitosamente.");
            actualizarTabla();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(vista, "Ya existe un usuario con ese ID / Documento.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarUsuario() {
        String id = vista.getTxtId().getText().trim();
        String nombre = vista.getTxtNombre().getText().trim();
        String email = vista.getTxtEmail().getText().trim();
        String rol = (String) vista.getCbRol().getSelectedItem();

        if (id.isEmpty() || nombre.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Seleccione o busque un usuario para actualizar.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean exito = modelo.actualizarUsuario(id, nombre, email, rol);
        if (exito) {
            JOptionPane.showMessageDialog(vista, "Usuario actualizado correctamente.");
            actualizarTabla();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(vista, "No se encontró el usuario con ID: " + id, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarUsuario() {
        String id = vista.getTxtId().getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese el ID del usuario a eliminar.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Está seguro de eliminar el usuario con ID " + id + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = modelo.eliminarUsuario(id);
            if (exito) {
                JOptionPane.showMessageDialog(vista, "Usuario eliminado correctamente.");
                actualizarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista, "No se encontró el usuario con ID: " + id, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarTabla() {
        DefaultTableModel modeloTabla = vista.getModeloTabla();
        modeloTabla.setRowCount(0);

        for (Usuario u : modelo.obtenerUsuarios()) {
            modeloTabla.addRow(new Object[]{u.getId(), u.getNombre(), u.getEmail(), u.getRol()});
        }
    }

    private void limpiarCampos() {
        vista.getTxtId().setText("");
        vista.getTxtNombre().setText("");
        vista.getTxtEmail().setText("");
        vista.getCbRol().setSelectedIndex(0);
        vista.getTablaUsuarios().clearSelection();
    }
}