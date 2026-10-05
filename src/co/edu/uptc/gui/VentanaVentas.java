package co.edu.uptc.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class VentanaVentas extends JFrame {

    private JTextField txtIdVenta;
    private JTextField txtIdCliente;
    private JTextField txtIsbn;
    private JTextField txtCantidad;
    private JTextField txtPrecio;
    private JCheckBox chkPremium;
    private JComboBox<String> cbIva;
    private JComboBox<String> cbMetodoPago;

    private JButton btnAgregar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    private JTable tablaVentas;
    private DefaultTableModel modeloTabla;

    public VentanaVentas() {
        setTitle("Gestión de Ventas - CRUD Ventas");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Formulario (Norte)
        JPanel panelFormulario = new JPanel(new GridLayout(4, 4, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la Venta"));

        panelFormulario.add(new JLabel("ID Venta:"));
        txtIdVenta = new JTextField();
        panelFormulario.add(txtIdVenta);

        panelFormulario.add(new JLabel("ID Cliente:"));
        txtIdCliente = new JTextField();
        panelFormulario.add(txtIdCliente);

        panelFormulario.add(new JLabel("ISBN del libro:"));
        txtIsbn = new JTextField();
        panelFormulario.add(txtIsbn);

        panelFormulario.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        panelFormulario.add(txtCantidad);

        panelFormulario.add(new JLabel("Precio unitario:"));
        txtPrecio = new JTextField();
        panelFormulario.add(txtPrecio);

        panelFormulario.add(new JLabel("Cliente Premium:"));
        chkPremium = new JCheckBox("Aplicar descuento");
        panelFormulario.add(chkPremium);

        panelFormulario.add(new JLabel("IVA:"));
        cbIva = new JComboBox<>(new String[]{"19%", "5%"});
        panelFormulario.add(cbIva);

        panelFormulario.add(new JLabel("Método de pago:"));
        cbMetodoPago = new JComboBox<>(new String[]{"TARJETA", "PSE", "EFECTIVO CONTRA ENTREGA"});
        panelFormulario.add(cbMetodoPago);

        add(panelFormulario, BorderLayout.NORTH);

        // Tabla (Centro)
        String[] columnas = {"ID Venta", "Fecha", "Cliente", "ISBN", "Cant.", "Precio U.",
                             "Premium", "Subtotal", "Descuento", "IVA", "Total", "Pago"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaVentas = new JTable(modeloTabla);
        add(new JScrollPane(tablaVentas), BorderLayout.CENTER);

        // Botones (Sur)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar Campos");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public JTextField getTxtIdVenta() { return txtIdVenta; }
    public JTextField getTxtIdCliente() { return txtIdCliente; }
    public JTextField getTxtIsbn() { return txtIsbn; }
    public JTextField getTxtCantidad() { return txtCantidad; }
    public JTextField getTxtPrecio() { return txtPrecio; }
    public JCheckBox getChkPremium() { return chkPremium; }
    public JComboBox<String> getCbIva() { return cbIva; }
    public JComboBox<String> getCbMetodoPago() { return cbMetodoPago; }
    public JButton getBtnAgregar() { return btnAgregar; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JTable getTablaVentas() { return tablaVentas; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
}
