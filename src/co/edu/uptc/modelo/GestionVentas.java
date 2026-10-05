package co.edu.uptc.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GestionVentas implements IGestionVentas {
    private List<Venta> listaVentas;

    public GestionVentas() {
        this.listaVentas = new ArrayList<>();
    }

    @Override
    public boolean agregarVenta(Venta venta) {
        if (buscarVentaPorId(venta.getIdVenta()) != null) {
            return false;
        }
        return listaVentas.add(venta);
    }

    @Override
    public List<Venta> obtenerVentas() {
        return Collections.unmodifiableList(listaVentas);
    }

    @Override
    public Venta buscarVentaPorId(String idVenta) {
        for (Venta v : listaVentas) {
            if (v.getIdVenta().equalsIgnoreCase(idVenta)) {
                return v;
            }
        }
        return null;
    }

    @Override
    public boolean actualizarVenta(String idVenta, String idCliente, String isbn, int cantidad,
                                   double precioUnitario, boolean clientePremium, double tasaIva, String metodoPago) {
        Venta v = buscarVentaPorId(idVenta);
        if (v == null) {
            return false;
        }
        v.setIdCliente(idCliente);
        v.setIsbn(isbn);
        v.setCantidad(cantidad);
        v.setPrecioUnitario(precioUnitario);
        v.setClientePremium(clientePremium);
        v.setTasaIva(tasaIva);
        v.setMetodoPago(metodoPago);
        return true;
    }

    @Override
    public boolean eliminarVenta(String idVenta) {
        Venta v = buscarVentaPorId(idVenta);
        return v != null && listaVentas.remove(v);
    }
}
