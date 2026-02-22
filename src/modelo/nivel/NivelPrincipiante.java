package modelo.nivel;

import modelo.Usuario;

public class NivelPrincipiante extends NivelBase {

    public NivelPrincipiante(INivel siguiente) {
        super("Principiante", 1, 5, siguiente);
    }

    @Override
    public void avanzar(Usuario usuario) {
        // Si ya jugo suficientes partidos, sube a intermedio
        if (getSiguiente() != null) {
            usuario.setNivel(getSiguiente());
        }
    }
}
