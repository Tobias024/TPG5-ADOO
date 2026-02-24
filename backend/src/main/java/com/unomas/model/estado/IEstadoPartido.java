package com.unomas.model.estado;

import com.unomas.model.Partido;

public interface IEstadoPartido {
    void avanzar(Partido partido);
    String getNombre();
}
