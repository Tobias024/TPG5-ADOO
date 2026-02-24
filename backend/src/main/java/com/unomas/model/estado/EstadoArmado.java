package com.unomas.model.estado;

import com.unomas.model.Partido;

public class EstadoArmado implements IEstadoPartido {

    @Override
    public void avanzar(Partido partido) {
        partido.setEstado(new EstadoConfirmado());
    }

    @Override
    public String getNombre() {
        return "ARMADO";
    }
}
