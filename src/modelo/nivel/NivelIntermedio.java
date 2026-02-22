package modelo.nivel;

import modelo.Usuario;

public class NivelIntermedio extends NivelBase {

    public NivelIntermedio(INivel siguiente) {
        super("Intermedio", 2, 10, siguiente);
    }

    @Override
    public void avanzar(Usuario usuario) {
        if (getSiguiente() != null) {
            usuario.setNivel(getSiguiente());
        }
    }
}
