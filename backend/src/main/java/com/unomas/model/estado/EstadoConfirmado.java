package com.unomas.model.estado;

import com.unomas.model.Partido;

public class EstadoConfirmado implements IEstadoPartido {

    @Override
    public void avanzar(Partido partido) {
        partido.setEstado(new EstadoEnJuego());
    }

    @Override
    public String getNombre() {
        return "CONFIRMADO";
    }
}
