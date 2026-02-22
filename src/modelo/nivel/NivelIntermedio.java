package modelo.nivel;

import modelo.Usuario;

public class NivelIntermedio extends NivelBase {

    public NivelIntermedio(INivel siguiente) {
        super("Intermedio", 2, 10, siguiente);
    }

    // avanzar() se hereda de NivelBase: cuenta partidos y sube si llega a 10
}
