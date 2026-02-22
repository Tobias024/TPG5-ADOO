package modelo.nivel;

import modelo.Usuario;

public class NivelPrincipiante extends NivelBase {

    public NivelPrincipiante(INivel siguiente) {
        super("Principiante", 1, 5, siguiente);
    }

    // avanzar() se hereda de NivelBase: cuenta partidos y sube si llega a 5
}
