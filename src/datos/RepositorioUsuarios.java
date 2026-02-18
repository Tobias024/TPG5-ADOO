package datos;

import modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuarios {
    private List<Usuario> listaUsuarios;

    public RepositorioUsuarios() {
        this.listaUsuarios = new ArrayList<>();
    }

    public void agregar(Usuario usuario) {
        listaUsuarios.add(usuario);
    }

    public Usuario buscar(String mail) {
        for (Usuario u : listaUsuarios) {
            if (u.getMail().equalsIgnoreCase(mail)) {
                return u;
            }
        }
        return null;
    }

    public boolean existe(Usuario usuario) {
        return listaUsuarios.stream()
                .anyMatch(u -> u.getMail().equalsIgnoreCase(usuario.getMail()));
    }

    public List<Usuario> obtenerTodos() {
        return new ArrayList<>(listaUsuarios);
    }
}
