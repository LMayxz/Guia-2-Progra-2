package co.edu.uptc.modelo;

public class Libro {
    private String isbn;
    private String titulo;
    private Strin autores;
    private int anioPublicacion;
    private String categoria;
    private String editorial;
    private int paginas;
    private double precioVenta;
    private int cantidadInventario;
    private String formato;

    public Libro() {}

    public Libro(String isbn, String titulo, String autores, int anioPublicacion, 
                 String categoria, String editorial, int paginas, double precioVenta, 
                 int cantidadInventario, String formato) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autores = autores;
        this.anioPublicacion = anioPublicacion;
        this.categoria = categoria;
        this.editorial = editorial;
        this.paginas = paginas;
        this.precioVenta = precioVenta;
        this.cantidadInventario = cantidadInventario;
        this.formato = formato;
    }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutores() { return autores; }
    public void setAutores(String autores) { this.autores = autores; }

    public int getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(int anioPublicacion) { this.anioPublicacion = anioPublicacion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public int getPaginas() { return paginas; }
    public void setPaginas(int paginas) { this.paginas = paginas; }

    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }

    public int getCantidadInventario() { return cantidadInventario; }
    public void setCantidadInventario(int cantidadInventario) { this.cantidadInventario = cantidadInventario; }

    public String getFormato() { return formato; }
    public void setFormato(String formato) { this.formato = formato; }
}