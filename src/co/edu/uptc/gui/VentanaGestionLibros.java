package co.edu.uptc.gui;

import co.edu.uptc.controlador.ControladorLibros;
import co.edu.uptc.modelo.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaGestionLibros extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final ControladorLibros controlador;

    private JTextField txtIsbn, txtTitulo, txtAutores, txtAnio, txtCategoria, txtEditorial, txtPaginas, txtPrecio, txtCantidad, txtBuscar;
    private JComboBox<String> comboFormato;
    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnBuscar, btnLimpiar;

    public VentanaGestionLibros(ControladorLibros controlador) {
        this.controlador = controlador;
        setTitle("Gestión de Libros - Tienda Virtual");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
        actualizarTabla(controlador.listarLibros());
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("Buscar (ISBN, Título o Autor):"));
        txtBuscar = new JTextField(20);
        panelBusqueda.add(txtBuscar);
        btnBuscar = new JButton("Buscar");
        btnLimpiar = new JButton("Limpiar / Listar Todo");
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(btnLimpiar);
        add(panelBusqueda, BorderLayout.NORTH);

        String[] columnas = {"ISBN", "Título", "Autores", "Año", "Categoría", "Editorial", "Pág.", "Precio", "Stock", "Formato"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaLibros = new JTable(modeloTabla);
        add(new JScrollPane(tablaLibros), BorderLayout.CENTER);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtIsbn = new JTextField(10);
        txtTitulo = new JTextField(15);
        txtAutores = new JTextField(15);
        txtAnio = new JTextField(5);
        txtCategoria = new JTextField(10);
        txtEditorial = new JTextField(10);
        txtPaginas = new JTextField(5);
        txtPrecio = new JTextField(8);
        txtCantidad = new JTextField(5);
        comboFormato = new JComboBox<>(new String[]{"Físico", "Digital"});

        agregarCampo(panelFormulario, gbc, "ISBN:", txtIsbn, 0, 0);
        agregarCampo(panelFormulario, gbc, "Título:", txtTitulo, 2, 0);
        agregarCampo(panelFormulario, gbc, "Autores:", txtAutores, 4, 0);

        agregarCampo(panelFormulario, gbc, "Año:", txtAnio, 0, 1);
        agregarCampo(panelFormulario, gbc, "Categoría:", txtCategoria, 2, 1);
        agregarCampo(panelFormulario, gbc, "Editorial:", txtEditorial, 4, 1);

        agregarCampo(panelFormulario, gbc, "Páginas:", txtPaginas, 0, 2);
        agregarCampo(panelFormulario, gbc, "Precio ($):", txtPrecio, 2, 2);
        agregarCampo(panelFormulario, gbc, "Cantidad:", txtCantidad, 4, 2);

        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulario.add(new JLabel("Formato:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(comboFormato, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRegistrar = new JButton("Registrar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        gbc.gridx = 2; gbc.gridy = 3; gbc.gridwidth = 4;
        panelFormulario.add(panelBotones, gbc);

        add(panelFormulario, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> buscar());
        btnLimpiar.addActionListener(e -> { txtBuscar.setText(""); actualizarTabla(controlador.listarLibros()); });
        btnRegistrar.addActionListener(e -> registrar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());

        tablaLibros.getSelectionModel().addListSelectionListener(e -> cargarSeleccionTabla());
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, String label, JComponent comp, int x, int y) {
        gbc.gridx = x; gbc.gridy = y; gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = x + 1;
        panel.add(comp, gbc);
    }

    private void actualizarTabla(List<Libro> libros) {
        modeloTabla.setRowCount(0);
        for (Libro l : libros) {
            modeloTabla.addRow(new Object[]{
                l.getIsbn(), l.getTitulo(), l.getAutores(), l.getAnioPublicacion(),
                l.getCategoria(), l.getEditorial(), l.getPaginas(),
                String.format("$ %.2f", l.getPrecioVenta()), l.getCantidadInventario(), l.getFormato()
            });
        }
    }

    private void buscar() {
        List<Libro> resultados = controlador.buscarLibros(txtBuscar.getText());
        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron libros que coincidan con la búsqueda.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
        }
        actualizarTabla(resultados);
    }

    private void registrar() {
        try {
            Libro libro = extraerLibroFormulario();
            if (controlador.registrarLibro(libro)) {
                JOptionPane.showMessageDialog(this, "Libro registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarTabla(controlador.listarLibros());
                limpiarFormulario();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al Registrar", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizar() {
        try {
            Libro libro = extraerLibroFormulario();
            if (controlador.actualizarLibro(libro)) {
                JOptionPane.showMessageDialog(this, "Libro actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarTabla(controlador.listarLibros());
                limpiarFormulario();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al Actualizar", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        String isbn = txtIsbn.getText().trim();
        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione o ingrese un ISBN para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el libro con ISBN " + isbn + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (controlador.eliminarLibro(isbn)) {
                    JOptionPane.showMessageDialog(this, "Libro eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    actualizarTabla(controlador.listarLibros());
                    limpiarFormulario();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al Eliminar", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private Libro extraerLibroFormulario() throws Exception {
        String isbn = txtIsbn.getText().trim();
        String titulo = txtTitulo.getText().trim();
        String autores = txtAutores.getText().trim();

        if (isbn.isEmpty() || titulo.isEmpty() || autores.isEmpty()) {
            throw new Exception("Los campos ISBN, Título y Autores son obligatorios.");
        }

        try {
            int anio = Integer.parseInt(txtAnio.getText().trim());
            int paginas = Integer.parseInt(txtPaginas.getText().trim());
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());

            if (precio <= 0 || cantidad < 0) {
                throw new Exception("El precio debe ser mayor a 0 y el inventario no puede ser negativo.");
            }

            return new Libro(isbn, titulo, autores, anio, txtCategoria.getText().trim(),
                             txtEditorial.getText().trim(), paginas, precio, cantidad,
                             (String) comboFormato.getSelectedItem());
        } catch (NumberFormatException e) {
            throw new Exception("Valores numéricos inválidos en Año, Páginas, Precio o Cantidad.");
        }
    }

    private void cargarSeleccionTabla() {
        int fila = tablaLibros.getSelectedRow();
        if (fila >= 0) {
            txtIsbn.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtTitulo.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtAutores.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtAnio.setText(modeloTabla.getValueAt(fila, 3).toString());
            txtCategoria.setText(modeloTabla.getValueAt(fila, 4).toString());
            txtEditorial.setText(modeloTabla.getValueAt(fila, 5).toString());
            txtPaginas.setText(modeloTabla.getValueAt(fila, 6).toString());
            
            String precioStr = modeloTabla.getValueAt(fila, 7).toString().replace("$", "").replace(",", ".").trim();
            txtPrecio.setText(precioStr);
            
            txtCantidad.setText(modeloTabla.getValueAt(fila, 8).toString());
            comboFormato.setSelectedItem(modeloTabla.getValueAt(fila, 9).toString());
        }
    }

    private void limpiarFormulario() {
        txtIsbn.setText(""); txtTitulo.setText(""); txtAutores.setText("");
        txtAnio.setText(""); txtCategoria.setText(""); txtEditorial.setText("");
        txtPaginas.setText(""); txtPrecio.setText(""); txtCantidad.setText("");
        comboFormato.setSelectedIndex(0);
        tablaLibros.clearSelection();
    }
}