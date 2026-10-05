package co.edu.uptc.controlador;

import co.edu.uptc.modelo.Libro;
import java.util.ArrayList;
import java.util.List;

public class ControladorLibros {

    private final List<Libro> listaLibros;

    public ControladorLibros() {
        this.listaLibros = new ArrayList<>();
    }

    public boolean registrarLibro(Libro libro) throws Exception {
        if (buscarPorIsbnExacto(libro.getIsbn()) != null) {
            throw new Exception("El ISBN ingresado ya existe en el catálogo.");
        }
        return listaLibros.add(libro);
    }

    public boolean actualizarLibro(Libro libroActualizado) throws Exception {
        for (int i = 0; i < listaLibros.size(); i++) {
            if (listaLibros.get(i).getIsbn().equalsIgnoreCase(libroActualizado.getIsbn())) {
                listaLibros.set(i, libroActualizado);
                return true;
            }
        }
        throw new Exception("El libro con ISBN " + libroActualizado.getIsbn() + " no fue encontrado.");
    }

    public boolean eliminarLibro(String isbn) throws Exception {
        Libro libro = buscarPorIsbnExacto(isbn);
        if (libro == null) {
            throw new Exception("No existe ningún libro registrado con el ISBN especificado.");
        }
        return listaLibros.remove(libro);
    }

    public List<Libro> listarLibros() {
        return new ArrayList<>(listaLibros);
    }

    public List<Libro> buscarLibros(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return listarLibros();
        }
        
        String query = criterio.toLowerCase().trim();
        List<Libro> resultados = new ArrayList<>();

        for (Libro libro : listaLibros) {
            boolean coincideIsbn = libro.getIsbn() != null && libro.getIsbn().toLowerCase().contains(query);
            boolean coincideTitulo = libro.getTitulo() != null && libro.getTitulo().toLowerCase().contains(query);
            boolean coincideAutor = libro.getAutores() != null && libro.getAutores().toLowerCase().contains(query);

            if (coincideIsbn || coincideTitulo || coincideAutor) {
                resultados.add(libro);
            }
        }
        return resultados;
    }

    private Libro buscarPorIsbnExacto(String isbn) {
        for (Libro l : listaLibros) {
            if (l.getIsbn().equalsIgnoreCase(isbn)) {
                return l;
            }
        }
        return null;
    }
}