package com.unomas.model.nivel;

public class NivelIntermedio extends NivelBase {

    public NivelIntermedio() {
        super("INTERMEDIO", 2);
    }

    @Override
    public INivel avanzar(Usuario usuario) {
        if (usuario.getVictorias() >= 15) {
            return new NivelAvanzado();
        }
        return this;
    }
}
