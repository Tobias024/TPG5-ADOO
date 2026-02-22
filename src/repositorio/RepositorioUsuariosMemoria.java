package repositorio;

import modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

// Repositorio en memoria, sin base de datos
public class RepositorioUsuariosMemoria implements IRepositorioUsuarios {
    private List<Usuario> usuarios;

    public RepositorioUsuariosMemoria() {
        this.usuarios = new ArrayList<>();
    }

    @Override
    public void agregar(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public Usuario buscarPorMail(String mail) {
        for (Usuario u : usuarios) {
            if (u.getMail().equalsIgnoreCase(mail)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public boolean existeMail(String mail) {
        return buscarPorMail(mail) != null;
    }
}
