package com.unomas.model.nivel;

public class NivelPrincipiante extends NivelBase {

    public NivelPrincipiante() {
        super("PRINCIPIANTE", 1);
    }

    @Override
    public INivel avanzar(Usuario usuario) {
        if (usuario.getVictorias() >= 5) {
            return new NivelIntermedio();
        }
        return this;
    }
}
