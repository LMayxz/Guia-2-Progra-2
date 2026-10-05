package co.edu.uptc.modelo;

import java.time.LocalDate;

public class Venta {
    public static final double DESCUENTO_PREMIUM = 0.10; // ajustar al valor pactado en el DAD (RF14)

    private String idVenta;
    private LocalDate fecha;
    private String idCliente;
    private String isbn;
    private int cantidad;
    private double precioUnitario;
    private boolean clientePremium;
    private double tasaIva;          // 0.19 o 0.05 (RF13)
    private String metodoPago;       // TARJETA, PSE, EFECTIVO CONTRA ENTREGA (RF17)

    public Venta(String idVenta, LocalDate fecha, String idCliente, String isbn, int cantidad,
                 double precioUnitario, boolean clientePremium, double tasaIva, String metodoPago) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.idCliente = idCliente;
        this.isbn = isbn;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.clientePremium = clientePremium;
        this.tasaIva = tasaIva;
        this.metodoPago = metodoPago;
    }

    public double getSubtotal() { return cantidad * precioUnitario; }

    public double getDescuento() {
        return clientePremium ? getSubtotal() * DESCUENTO_PREMIUM : 0;
    }

    // El descuento se aplica antes de impuestos
    public double getImpuesto() { return (getSubtotal() - getDescuento()) * tasaIva; }

    public double getTotal() { return getSubtotal() - getDescuento() + getImpuesto(); }

    public String getIdVenta() { return idVenta; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getIdCliente() { return idCliente; }
    public void setIdCliente(String idCliente) { this.idCliente = idCliente; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
    public boolean isClientePremium() { return clientePremium; }
    public void setClientePremium(boolean clientePremium) { this.clientePremium = clientePremium; }
    public double getTasaIva() { return tasaIva; }
    public void setTasaIva(double tasaIva) { this.tasaIva = tasaIva; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
}
