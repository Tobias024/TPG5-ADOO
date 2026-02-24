package com.unomas.model.estado;

import com.unomas.model.Partido;

public class EstadoFaltanJugadores implements IEstadoPartido {

    @Override
    public void avanzar(Partido partido) {
        if (partido.cupoCompleto()) {
            partido.setEstado(new EstadoArmado());
        }
    }

    @Override
    public String getNombre() {
        return "FALTAN_JUGADORES";
    }
}
