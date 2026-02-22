package modelo.nivel;

import modelo.Usuario;

public interface INivel {
    String getNombre();
    int getValor();
    void avanzar(Usuario usuario);
}
