package repositorio;

import modelo.Usuario;

public interface IRepositorioUsuarios {
    void agregar(Usuario usuario);
    Usuario buscarPorMail(String mail);
    boolean existeMail(String mail);
}
