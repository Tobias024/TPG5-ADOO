package com.unomas.model.nivel;

public class NivelAvanzado extends NivelBase {

    public NivelAvanzado() {
        super("AVANZADO", 3);
    }

    @Override
    public INivel avanzar(Usuario usuario) {
        return this;
    }
}
