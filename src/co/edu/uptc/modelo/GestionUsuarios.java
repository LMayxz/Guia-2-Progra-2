package co.edu.uptc.modelo;

import java.util.ArrayList;
import java.util.List;

public class GestionUsuarios implements IGestionUsuarios {
    private List<Usuario> listaUsuarios;

    public GestionUsuarios() {
        this.listaUsuarios = new ArrayList<>();
    }

    @Override
    public boolean agregarUsuario(Usuario usuario) {
        if (buscarUsuarioPorId(usuario.getId()) != null) {
            return false;
        }
        return listaUsuarios.add(usuario);
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        return listaUsuarios;
    }

    @Override
    public Usuario buscarUsuarioPorId(String id) {
        for (Usuario u : listaUsuarios) {
            if (u.getId().equalsIgnoreCase(id)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public boolean actualizarUsuario(String id, String nuevoNombre, String nuevoEmail, String nuevoRol) {
        Usuario u = buscarUsuarioPorId(id);
        if (u != null) {
            u.setNombre(nuevoNombre);
            u.setEmail(nuevoEmail);
            u.setRol(nuevoRol);
            return true;
        }
        return false;
    }

    @Override
    public boolean eliminarUsuario(String id) {
        Usuario u = buscarUsuarioPorId(id);
        if (u != null) {
            return listaUsuarios.remove(u);
        }
        return false;
    }
}