package servicio;

import modelo.Usuario;
import repositorio.IRepositorioUsuarios;

// Servicio de usuarios: registro, login y busqueda
public class ServicioUsuarios {
    private IRepositorioUsuarios repositorioUsuarios;

    public ServicioUsuarios(IRepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }

    public Usuario registrar(String nombre, String mail, String password) {
        if (repositorioUsuarios.existeMail(mail)) {
            return null;
        }
        Usuario nuevo = new Usuario(nombre, mail, password);
        repositorioUsuarios.agregar(nuevo);
        return nuevo;
    }

    public Usuario login(String mail, String password) {
        Usuario usuario = repositorioUsuarios.buscarPorMail(mail);
        if (usuario != null && usuario.passwordCorrecta(password)) {
            return usuario;
        }
        return null;
    }

    public Usuario buscarPorMail(String mail) {
        return repositorioUsuarios.buscarPorMail(mail);
    }
}
