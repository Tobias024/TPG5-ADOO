package com.unomas.model.estado;

import com.unomas.model.Partido;

public class EstadoEnJuego implements IEstadoPartido {

    @Override
    public void avanzar(Partido partido) {
        partido.setEstado(new EstadoFinalizado());
    }

    @Override
    public String getNombre() {
        return "EN_JUEGO";
    }
}
