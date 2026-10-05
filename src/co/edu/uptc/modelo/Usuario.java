package co.edu.uptc.modelo;

public class Usuario extends Persona {
    private String rol;

    public Usuario(String id, String nombre, String email, String rol) {
        super(id, nombre, email);
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String obtenerTipoUsuario() {
        return "Usuario Sistema - Rol: " + rol;
    }
}