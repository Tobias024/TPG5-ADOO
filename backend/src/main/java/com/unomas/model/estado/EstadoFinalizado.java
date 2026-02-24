package com.unomas.model.estado;

import com.unomas.model.Partido;

public class EstadoFinalizado implements IEstadoPartido {

    @Override
    public void avanzar(Partido partido) {
        // Terminal state — no further transitions
    }

    @Override
    public String getNombre() {
        return "FINALIZADO";
    }
}
