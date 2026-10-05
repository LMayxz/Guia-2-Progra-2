package co.edu.uptc.modelo;

import java.util.List;

public interface IGestionVentas {
    boolean agregarVenta(Venta venta);
    List<Venta> obtenerVentas();
    Venta buscarVentaPorId(String idVenta);
    boolean actualizarVenta(String idVenta, String idCliente, String isbn, int cantidad,
                            double precioUnitario, boolean clientePremium, double tasaIva, String metodoPago);
    boolean eliminarVenta(String idVenta);
}
