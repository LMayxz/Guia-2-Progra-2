package co.edu.uptc.modelo;

import java.util.List;

public interface IGestionUsuarios {
    boolean agregarUsuario(Usuario usuario);
    List<Usuario> obtenerUsuarios();
    Usuario buscarUsuarioPorId(String id);
    boolean actualizarUsuario(String id, String nuevoNombre, String nuevoEmail, String nuevoRol);
    boolean eliminarUsuario(String id);
}